public class CarPart {
    private String name;
    private boolean isGenuine;

    public CarPart(String name, boolean isGenuine) {
        this.name = name;
        this.isGenuine = isGenuine;
    }

    public String getName() {
        return name;
    }

    public boolean isGenuine() {
        return isGenuine;
    }
}