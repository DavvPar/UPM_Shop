package es.upm.etsisi.poo;

public class Cash extends User{

    public Cash(String name, String email, String id){
        super(name, email, id);
    }

    @Override
    public String toString() {
        return "Cash{" +
                "identifier='" + getId() +
                "', name='" + getName() +
                "', email='" + getEmail() +
                "'}";
    }
}
