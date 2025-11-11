package es.upm.etsisi.poo;

import java.util.Date;

public class ComplexProduct extends Product{
    private Date expirationDate;
    private int people;
    private final int MAX_PEOPLE = 100;

    /**
     * Constructor of the Class Product.
     * Checks if the entering values are valid for a Product.
     *
     * @param ID       product ID
     * @param name     product name
     * @param price    product price
     */
    public ComplexProduct(int ID, String name, double price, Date expirationDate, int people) {
        super(ID, name, price);
        this.expirationDate = expirationDate;
        this.people = people;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public int getMAX_PEOPLE() {
        return MAX_PEOPLE;
    }


    //TODO SIN ACABAR
    @Override
    public String toString() {
        return super.toString().replace("}","") +
                "expirationDate=" + expirationDate +
                ", people=" + people +
                '}';
    }

    /*public String toString(){
        return "{Product" +
                ", id:" + getID() +
                ", name:'" + getName() + '\'' +
                ", price:" + String.format("%.2f", getPrice()) +
                ", expirationDate:" + expirationDate +
                ", people:" + people +
                '}';
    }*/
}
