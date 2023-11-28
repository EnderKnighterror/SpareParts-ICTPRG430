import org.junit.Before;
import org.junit.Test;
import java.util.Scanner;
import static org.junit.Assert.*;

public class RandomNumberGeneratorTest {

    private RandomNumberGenerator rng;

    @Before
    public void setUp() {
        rng = new RandomNumberGenerator();
    }

    @Test
    public void testGenerateRandomNumber() {
        RandomNumberGenerator rng = new RandomNumberGenerator();
        int number = rng.generateRandomNumber();
        boolean isInRange = number >= 100000 && number <= 999999;
        System.out.println("Generated number: " + number + " - Is in range: " + isInRange);
        assertTrue("Number should be between 100000 and 999999", isInRange);
    }
    @Test
    public void testStartsWithOne() {
        assertTrue("Should return true for numbers starting with 1", rng.startsWithOne(100000));
        assertFalse("Should return false for numbers not starting with 1", rng.startsWithOne(200000));
    }

    @Test
    public void testRegisterNewClient() {
        // Simulate user input
        String input = "John\nyes\nyes\nno\n";
        Scanner scanner = new Scanner(input);
        rng.registerNewClient(scanner);

        assertTrue("Client should be registered", rng.clients.containsKey("John"));
        Client client = rng.clients.get("John");
        assertNotNull("Client should not be null", client);
        assertTrue("Client should prefer genuine parts", client.isGenuinePart());
        assertTrue("Client should be registered", client.isRegistered());
        assertFalse("Client should not be on account", client.isOnAccount());
    }
}