/**
 * Represents a part of a car.
 * This class encapsulates the details of a car part, including its name and whether it is a genuine part.
 */
public class CarPart {
    // Name of the car part.
    private String name;

    // Flag indicating whether the part is genuine or not.
    private boolean isGenuine;

    /**
     * Constructs a new CarPart with the specified name and genuineness.
     *
     * @param name       the name of the car part
     * @param isGenuine  a boolean indicating if the part is genuine
     */
    public CarPart(String name, boolean isGenuine) {
        this.name = name;
        this.isGenuine = isGenuine;
    }

    /**
     * Returns the name of the car part.
     *
     * @return the name of the car part
     */
    public String getName() {
        return name;
    }

    /**
     * Checks if the car part is genuine.
     *
     * @return true if the part is genuine, false otherwise
     */
    public boolean isGenuine() {
        return isGenuine;
    }
}