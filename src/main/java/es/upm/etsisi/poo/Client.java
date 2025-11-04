package es.upm.etsisi.poo;

public class Client extends User {
    /**
     * Identification of the client, consisting on 8 numbers and a letter
     */
    private String DNI;
    /**
     * Identification of the cashier that registered the client
     */
    private final String cashId;

    /**
     *
     * Constructor of the class Client
     * @param name
     * @param DNI
     * @param email
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
    public String getDNI(){return DNI;}
    /**
     * Setter for DNI
     * @param DNI
     */
    public void setDNI(String DNI){this.DNI = DNI;}
    /**
     * Getter for cashId
     * @return cashId
     */
    public String getCashId(){return cashId;}
}
