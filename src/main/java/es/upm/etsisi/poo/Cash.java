package es.upm.etsisi.poo;

public class Cash extends User{


    /**
     * Cashier id, consisting in letters "UW" and 7 random numbers
     */
    private String cashId;
    /**
     * Constructor of the class Cash
     * @param nombre
     * @param email
     * @param cashId
     */
    public Cash(String nombre, String email, String cashId){
        super(email, nombre);
        this.cashId = cashId;
        //AL CREARLO ASI HAY QUE AÑADIR CASHID AL SET DE CASHIDS
    }

    public Cash(String nombre, String email){
        super(email, nombre);
        this.cashId = CashList.generateUniqueId();
    }

    /**
     * Getter fot cashId
     * @return cashId
     */
    public String getCashId() {
        return cashId;
    }

    public void setCashId(){
        this.cashId = cashId;
    }

    //TODO toString
}
