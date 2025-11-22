package es.upm.etsisi.poo;

public class Client extends User {
    /**
     * Identification of the client, consisting on 8 numbers and a letter
     */
    private final String DNI;
    /**
     * Identification of the cashier that registered the client
     */
    private String cashId;

    /**
     *
     * Constructor of the class Client
     * @param name name of the Client
     * @param DNI unique identification of the Client
     * @param email email of the Client
     * @param cashId Cash that created the Client
     */
    public Client(String name, String DNI, String email, String cashId){
        super(name, email);
        this.DNI = DNI;
        this.cashId = cashId;
    }

    /**
     * Getter for DNI
     * @return DNI
     */
    public String getIdentifier(){return DNI;}

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
        return "{class:Client" +
                ", name:" + getName() +
                ", email:" + getEmail() +
                ", DNI:" + getIdentifier() +
                "}";
    }
}
