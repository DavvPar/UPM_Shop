package es.upm.etsisi.poo;

/**
 * Cash is an extension of Users that needs a Cashier to be created
 * Has unique identification and creates tickets to purchase products
 */
public class Client extends User {
    /**
     * Identification of the cashier that registered the client
     */
    private String cashId;

    /**
     * Constructor of the class Client
     * @param name name of the Client
     * @param id unique identification of the Client
     * @param email email of the Client
     * @param cashId Cash that created the Client
     */
    public Client(String name, String id, String email, String cashId){
        super(name, email, id);
        this.cashId = cashId;
    }

    /**
     * Getter for cashId
     * @return the Cash that created the user
     */
    public String getCashId() {
        return cashId;
    }

    /**
     * Setter for cashId
     * @param cashId the cashId of the Cash that created the user
     */
    public void setCashId(String cashId) {
        this.cashId = cashId;
    }

    /**
     * toString of the object Client, showing its name, email and identification.
     * @return String containing client info
     */
    @Override
    public String toString() {
        return "Client{" +
                "identifier='" + getId() +
                "', name='" + getName() +
                "', email'=" + getEmail() +
                "', cash='" + getCashId() +
        "'}";
    }
}
