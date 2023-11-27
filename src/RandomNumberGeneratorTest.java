import org.junit.Test;
import static org.junit.Assert.*;

public class RandomNumberGeneratorTest {

    @Test
    public void testGenerateRandomNumber() {
        RandomNumberGenerator rng = new RandomNumberGenerator();
        int number = rng.generateRandomNumber();
        assertTrue("Number should be between 100000 and 999999", number >= 100000 && number <= 999999);
    }

    @Test
    public void testStartsWithOne() {
        RandomNumberGenerator rng = new RandomNumberGenerator();
        assertTrue("Should return true for numbers starting with 1", rng.startsWithOne(100000));
        assertFalse("Should return false for numbers not starting with 1", rng.startsWithOne(200000));
    }
}
