package es.upm.etsisi.poo;

/**
 * Cash is an extension of Users that is necessary for declaring
 * new Clients, they have unique identification and tickets are
 * tied to a Cashier
 */
public class Cash extends User{

    /**
     * Constructor of the class Cash
     * @param name name of the Cash
     * @param email email of the Cash
     * @param id unique identification of the Cash
     */
    public Cash(String name, String email, String id){
        super(name, email, id);
    }

    /**
     * toString of the object Cash, showing its name, email and identification.
     * @return String containing cash info
     */
    @Override
    public String toString() {
        return "Cash{" +
                "identifier='" + getId() +
                "', name='" + getName() +
                "', email='" + getEmail() +
                "'}";
    }
}
