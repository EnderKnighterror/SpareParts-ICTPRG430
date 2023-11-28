/**
 * Represents a client in a system that deals with car parts.
 * This class encapsulates details about the client, including their name and various status flags
 * related to car parts and client's account.
 */
public class Client {
    // Name of the client.
    private String name;

    // Flag indicating whether the client prefers genuine car parts.
    private boolean isGenuinePart;

    // Flag indicating whether the client is registered in the system.
    private boolean isRegistered;

    // Flag indicating whether the client has an account with the system.
    private boolean isOnAccount;

    /**
     * Constructs a new Client with specified details.
     *
     * @param name           the name of the client
     * @param isGenuinePart  a boolean indicating if the client prefers genuine parts
     * @param isRegistered   a boolean indicating if the client is registered
     * @param isOnAccount    a boolean indicating if the client has an account
     */
    public Client(String name, boolean isGenuinePart, boolean isRegistered, boolean isOnAccount) {
        this.name = name;
        this.isGenuinePart = isGenuinePart;
        this.isRegistered = isRegistered;
        this.isOnAccount = isOnAccount;
    }

    /**
     * Returns the name of the client.
     *
     * @return the name of the client
     */
    public String getName() {
        return name;
    }

    /**
     * Checks if the client prefers genuine car parts.
     *
     * @return true if the client prefers genuine parts, false otherwise
     */
    public boolean isGenuinePart() {
        return isGenuinePart;
    }

    /**
     * Checks if the client is registered in the system.
     *
     * @return true if the client is registered, false otherwise
     */
    public boolean isRegistered() {
        return isRegistered;
    }

    /**
     * Checks if the client has an account with the system.
     *
     * @return true if the client has an account, false otherwise
     */
    public boolean isOnAccount() {
        return isOnAccount;
    }
}
