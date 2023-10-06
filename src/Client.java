public class Client {
    private String name;
    private boolean isGenuinePart;
    private boolean isRegistered;
    private boolean isOnAccount;

    public Client(String name, boolean isGenuinePart, boolean isRegistered, boolean isOnAccount) {
        this.name = name;
        this.isGenuinePart = isGenuinePart;
        this.isRegistered = isRegistered;
        this.isOnAccount = isOnAccount;
    }
    public String getName() {
        return name;
    }
    public boolean isGenuinePart() {
        return isGenuinePart;
    }
    public boolean isRegistered() {
        return isRegistered;
    }
    public boolean isOnAccount() {
        return isOnAccount;
    }
}
