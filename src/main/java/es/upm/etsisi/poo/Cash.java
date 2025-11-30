package es.upm.etsisi.poo;

/**
 * Cash is an extension of Users that is necessary for declaring
 * new Clients, they have unique identification and tickets are
 * tied to a Cashier
 */
public class Cash extends User{
    /**
     * Cashier id, consisting in letters "UW" and 7 random numbers
     */
    private String cashId;
    /**
     * Constructor of the class Cash
     * @param name name of the Cash
     * @param email email of the Cash
     * @param cashId unique identification of the Cash
     */
    public Cash(String name, String email, String cashId){
        super(name, email);
        this.cashId = cashId;
    }

    /**
     * Constructor of the class Cash
     * @param name name of the Cash
     * @param email email of the Cash
     */
    public Cash(String name, String email){
        super(name, email);
        this.cashId = null;
    }

    /**
     * Getter fot cashId
     * @return cashId
     */
    public String getIdentifier() {
        return cashId;
    }

    /**
     * Setter for cashId
     * @param cashId cash
     */
    public void setCashId(String cashId){
        this.cashId = cashId;
    }

    /**
     * toString of the object Cash, showing its name, email and identification.
     * @return String containing cash info
     */
    @Override
    public String toString() {
        return "Cash{" +
                "identifier='" + getIdentifier() +
                "', name='" + getName() +
                "', email='" + getEmail() +
                "'}";
    }
}
