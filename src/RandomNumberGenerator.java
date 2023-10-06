import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RandomNumberGenerator {
    private final Set<Integer> usedNumbers;
    private final List<Integer> currentNumbers;
    private final Map<String, Client> clients;

    public RandomNumberGenerator() {
        usedNumbers = new HashSet<>();
        currentNumbers = new ArrayList<>();
        clients = new HashMap<>();
    }

    // Method to generate 6 digit number
    private int generateRandomNumber() {
        Random random = new Random();
        int num;
        do {
            num = 100000 + random.nextInt(900000); // generates a random number between 100000 and 999999
        } while (usedNumbers.contains(num));
        usedNumbers.add(num);
        return num;
    }

    //method to check if a number starts with 1
    private boolean startsWithOne(int num) {
        return String.valueOf(num).startsWith("1");
    }

    // method to generate a random number and add it to the currentNumbers list
    private void generateAndAddNumber(Client client) {
        int num = generateRandomNumber();
        String paymentType = client.isOnAccount() ? "Invoice" : "Card at pickup";
        if (client.isGenuinePart()) {
            while (!startsWithOne(num)) {
                num = generateRandomNumber();
            }
        } else {
            while (startsWithOne(num)) {
                num = generateRandomNumber();
            }
        }
        currentNumbers.add(num);
        String partType = client.isGenuinePart() ? "Genuine Part" : "Third-Party Part";
        String output = String.format("Client: %s | Number: %06d | Part Type: %s | Payment Type: %s", client.getName(), num, partType, paymentType);
        System.out.println(output);
    }

    // method to print all the current numbers
    private void printCurrentNumbers() {
        System.out.println("\nCurrent Numbers:");
        for (int num : currentNumbers) {
            System.out.println(String.format("%06d", num));
        }
    }

    // method to remove a number from the currentNumbers list when picked up
    private void removeNumber(int number) {
        if (currentNumbers.contains(number)) {
            currentNumbers.remove(Integer.valueOf(number));
        }
    }
    //method to reister a new client
    private void registerNewClient(Scanner scanner) {
        System.out.println("Enter client name: ");
        String name = scanner.next();

        System.out.println("Is the Part Genuine? (yes/no): ");
        String genuineInput = scanner.next().toLowerCase();
        boolean isGenuinePart = genuineInput.equals("yes");

        System.out.println("Is the client registered? (yes/no):");
        String registeredInput = scanner.next().toLowerCase();
        boolean isRegistered = registeredInput.equals("yes");

        System.out.println("Is the client paying on account? (yes/no):");
        String onAccountInput = scanner.next().toLowerCase();
        boolean isOnAccount = onAccountInput.equals("yes");

        Client client = new Client(name, isGenuinePart, isRegistered, isOnAccount);
        clients.put(name, client);
        System.out.println("Client registered successfully!");
    }
    // current write to file on option number 6 after client is registered doesn't save clients. a client needs to be registered each time
    private void writeCurrentNumbersToFile() {
        try (FileWriter writer = new FileWriter("current_numbers.txt", true)) {
            for (int num : currentNumbers) {
                String clientName = getClientNameByNumber(num);
                String partType = clients.get(clientName).isGenuinePart() ? "Genuine Part" : "Third-Party Part";
                String paymentType = clients.get(clientName).isOnAccount() ? "Invoice" : "Card at Pickup";

                String output = String.format("Client: %s | Number: %06d | Part Type: %s | Payment Type: %s%n", clientName, num, partType, paymentType);
                writer.write(output);
            }
            System.out.println("Current numbers with client names have been written to the file.");
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }
    private void viewCurrentClientsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("current_numbers.txt"))){
            String line;
            System.out.println("\nCurrent Clients Saved in File:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
    private List<String> loadExistingClientDataFromFile() {
        List<String> existingClientData = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("current_numbers.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                existingClientData.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        return existingClientData;
    }

    // helper to get client info
    private String getClientNameByNumber(int number) {
        for (Map.Entry<String, Client> entry : clients.entrySet()) {
            if (currentNumbers.contains(number) && entry.getValue().getName().equals(entry.getKey())) {
                return entry.getKey();
            }
        }
        return "Unknown Client";
    }
    //menu display. menu switch below
    private int displayMenuAndGetChoice(Scanner scanner) {
        System.out.println("\nMenu:");
        System.out.println("1. Generate Random Number for Existing Client");
        System.out.println("2. Generate Random Number for Predetermined Part");
        System.out.println("3. Print Current Numbers");
        System.out.println("4. Remove Number (Order Picked Up)");
        System.out.println("5. Register New Client");
        System.out.println("6. Write Current Numbers to File");
        System.out.println("7. Print current saved clients ");
        System.out.println("8. Exit");
        System.out.print("Enter your choice: ");
        return scanner.nextInt();
    }
    public void run() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            choice = displayMenuAndGetChoice(scanner);
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter client name: ");
                    String existingClientName = scanner.next();
                    List<String> existingClientData = loadExistingClientDataFromFile();

                    boolean clientExists = false;
                    for (String clientInfo : existingClientData) {
                        if (clientInfo.contains(existingClientName)) {
                            System.out.println("Using existing client data:");
                            System.out.println(clientInfo);
                            clientExists = true;
                            break;
                        }
                    }

                    if (!clientExists) {
                        Client existingClient = clients.get(existingClientName);
                        if (existingClient == null) {
                            System.out.println("Client not Found. Please register the client first.");
                        } else {
                            generateAndAddNumber(existingClient);
                        }
                    }
                }

                case 2 -> {
                    System.out.print("Enter part type (true for genuine, false for third-party): ");
                    boolean isGenuinePart = scanner.nextBoolean();
                    Client predeterminedClient = new Client("Predetermined Client", isGenuinePart, true, true);
                    generateAndAddNumber(predeterminedClient);
                }
                case 3 -> printCurrentNumbers();

                case 4 -> {
                    System.out.print("Enter the number to remove (6 digits): ");
                    int numberToRemove = scanner.nextInt();
                    removeNumber(numberToRemove);
                }

                case 5 -> registerNewClient(scanner);

                case 6 -> writeCurrentNumbersToFile();

                case 7 -> viewCurrentClientsFromFile();

                case 8 -> System.out.println("Exiting...");

                default -> {
                    System.out.println("Invalid choice. Please try again.");
                }
            }
        } while (choice != 8);
    }
    public static void main(String[] args) {
        RandomNumberGenerator generator = new RandomNumberGenerator();
        generator.run();
    }

}

