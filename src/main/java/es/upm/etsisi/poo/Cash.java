package es.upm.etsisi.poo;

public class Cash extends User{


    /**
     * Cashier id, consisting in letters "UW" and 7 random numbers
     */
    private String cashId;
    /**
     * Constructor of the class Cash
     * @param nombre name of the Cash
     * @param email email of the Cash
     * @param cashId unique identification of the Cash
     */
    public Cash(String nombre, String email, String cashId){
        super(email, nombre);
        this.cashId = cashId;
    }

    public Cash(String nombre, String email){
        super(email, nombre);
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
        return "{class:Cash" +
                ", name:" + getName() +
                ", email:" + getEmail() +
                ", cashId:" + getIdentifier() +
                "}";
    }
}
