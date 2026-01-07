package es.upm.etsisi.poo.user;
import es.upm.etsisi.poo.enums.ClientType;
/**
 * Cash is an extension of Users that needs a Cashier to be created
 * Has unique identification and creates tickets to purchase products
 */


public class Client extends User {

    private String cashId;

    private ClientType type;

    public Client(String name, String id, String email, String cashId,ClientType type){
        super(name, email, id);
        this.cashId = cashId;
        this.type = type;
    }

    public String getCashId() {
        return cashId;
    }

    public void setCashId(String cashId) {
        this.cashId = cashId;
    }

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
