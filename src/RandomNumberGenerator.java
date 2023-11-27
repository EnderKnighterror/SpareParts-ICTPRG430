import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RandomNumberGenerator {
    // Sets for storing used numbers and lists for current numbers
    private final Set<Integer> usedNumbers;
    private final List<Integer> currentNumbers;
    private final Map<String, Client> clients;

    public RandomNumberGenerator() {
        usedNumbers = new HashSet<>();
        currentNumbers = new ArrayList<>();
        clients = new HashMap<>();
    }

    /**
     * Generates a unique 6-digit random number.
     * Ensures that the generated number hasn't been used before.
     * @return A unique 6-digit number.
     */
    int generateRandomNumber() {
        Random random = new Random();
        int num;
        do {
            // Generates a number between 100000 and 999999
            num = 100000 + random.nextInt(900000);
        } while (usedNumbers.contains(num));
        usedNumbers.add(num);
        return num;
    }

    /**
     * Checks if a number starts with the digit 1.
     * @param num The number to check.
     * @return true if the number starts with 1, false otherwise.
     */
    boolean startsWithOne(int num) {
        return String.valueOf(num).startsWith("1");
    }

    /**
     * Generates a random number based on client requirements and adds it to the currentNumbers list.
     * @param client The client for whom the number is being generated.
     */
    private void generateAndAddNumber(Client client) {
        int num = generateRandomNumber();
        String paymentType = client.isOnAccount() ? "Invoice" : "Card at pickup";

        // Generate number based on whether the part is genuine or not
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

    /**
     * Prints all current numbers.
     */
    private void printCurrentNumbers() {
        System.out.println("\nCurrent Numbers:");
        for (int num : currentNumbers) {
            System.out.println(String.format("%06d", num));
        }
    }

    /**
     * Removes a number from the currentNumbers list when an order is picked up.
     * @param number The number to remove.
     */
    private void removeNumber(int number) {
        currentNumbers.remove(Integer.valueOf(number));
    }

    /**
     * Registers a new client by gathering their information through user input.
     * @param scanner The Scanner object for reading user input.
     */
    private void registerNewClient(Scanner scanner) {
        System.out.println("Enter client name: ");
        String name = scanner.next();

        // Collecting information about the part's genuineness
        System.out.println("Is the Part Genuine? (yes/no): ");
        String genuineInput = scanner.next().toLowerCase();
        boolean isGenuinePart = genuineInput.equals("yes");

        // Collecting information about the client's registration status
        System.out.println("Is the client registered? (yes/no):");
        String registeredInput = scanner.next().toLowerCase();
        boolean isRegistered = registeredInput.equals("yes");

        // Collecting information about the client's payment method
        System.out.println("Is the client paying on account? (yes/no):");
        String onAccountInput = scanner.next().toLowerCase();
        boolean isOnAccount = onAccountInput.equals("yes");

        // Creating and registering a new client
        Client client = new Client(name, isGenuinePart, isRegistered, isOnAccount);
        clients.put(name, client);
        System.out.println("Client registered successfully!");
    }

    /**
     * Writes the current numbers and associated client details to a file.
     */
    private void writeCurrentNumbersToFile() {
        try (FileWriter writer = new FileWriter("current_numbers.txt", true)) {
            for (int num : currentNumbers) {
                String clientName = getClientNameByNumber(num);
                String partType = clients.get(clientName).isGenuinePart() ? "Genuine Part" : "Third-Party Part";
                String paymentType = clients.get(clientName).isOnAccount() ? "Invoice" : "Card at Pickup";

                String output = String.format("Client: %s | Number: %06d | Part Type: %s | Payment Type: %s%n", clientName, num, partType, paymentType);

                // Check if the entry already exists in the file to avoid duplicates
                if (entryExistsInFile(output)) {
                    updateEntryInFile(output);
                } else {
                    writer.write(output);
                }
            }
            System.out.println("Current numbers with client names have been written to the file.");
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }

    /**
     * Checks if a specific entry already exists in the file.
     * @param entry The entry to check.
     * @return true if the entry exists, false otherwise.
     */
    private boolean entryExistsInFile(String entry) {
        try (BufferedReader reader = new BufferedReader(new FileReader("current_numbers.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(entry)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        return false;
    }

    /**
     * Updates an existing entry in the file.
     * @param entry The entry to update.
     */
    private void updateEntryInFile(String entry) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("current_numbers.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(entry)) {
                    lines.add(entry);
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        // Rewrite the file with the updated content
        try (FileWriter writer = new FileWriter("current_numbers.txt")) {
            for (String line : lines) {
                writer.write(line + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }

    /**
     * Displays the current clients saved in the file.
     */
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

    /**
     * Loads existing client data from the file.
     * @return A list of strings, each representing a line (client data) from the file.
     */
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

    /**
     * Retrieves the client's name associated with a given number.
     * @param number The number associated with the client.
     * @return The name of the client or "Unknown Client" if not found.
     */
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
        System.out.println("2. Print Current Numbers");
        System.out.println("3. Remove Number (Order Picked Up)");
        System.out.println("4. Register New Client");
        System.out.println("5. Write Current Numbers to File");
        System.out.println("6. Print current saved clients ");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
        return scanner.nextInt();
    }
    public void run() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            choice = displayMenuAndGetChoice(scanner);
            switch (choice) {
                case 1: {
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

                case 2: printCurrentNumbers();

                case 3: {
                    System.out.print("Enter the number to remove (6 digits): ");
                    int numberToRemove = scanner.nextInt();
                    removeNumber(numberToRemove);
                }

                case 4: registerNewClient(scanner);

                case 5: writeCurrentNumbersToFile();

                case 6: viewCurrentClientsFromFile();

                case 7: System.out.println("Exiting...");

                default: {
                    System.out.println("Invalid choice. Please try again.");
                }
            }
        } while (choice != 7);
    }
    /**
     * Main method to run the RandomNumberGenerator program.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        RandomNumberGenerator generator = new RandomNumberGenerator();
        generator.run();
    }
}

