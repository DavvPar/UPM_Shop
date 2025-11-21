package es.upm.etsisi.poo;

import java.time.LocalDate;

public class ComplexProduct extends Product{
    private String expirationDate;
    private int people;
    private final int MAX_PEOPLE = 100;
    private ProductType type;

    /**
     * Constructor of the Class Product.
     * Checks if the entering values are valid for a Product.
     *
     * @param ID       product ID
     * @param name     product name
     * @param price    product price
     */
    public ComplexProduct(int ID, String name, double price, String expirationDate, int people,ProductType type) {
        super(ID, name, price);
        this.type = type;
        if(people <= 0 || people > MAX_PEOPLE){
            throw new IllegalArgumentException("The number of participants must be between 1 and " + MAX_PEOPLE);
           // throw new IllegalArgumentException("The expiration date cannot be earlier than today.");
        }
        this.expirationDate = expirationDate;
        this.people = people;
    }


    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        if(people <= 0 || people > MAX_PEOPLE)
            throw new IllegalArgumentException("The number of participants must be between 1 and " + MAX_PEOPLE);
        this.people = people;
    }

    public int getMAX_PEOPLE() {
        return MAX_PEOPLE;
    }


    @Override
    public ProductType getProductType() {
        return type;
    }

    @Override
    public String toString(){
        return "{Product" +
                ", id:" + getID() +
                ", name:'" + getName() + '\'' +
                ", price:" + String.format("%.2f", getPrice()) +
                ", date of Event:" + expirationDate +
                ", max of people allowed:" + MAX_PEOPLE +
                ", actual people in event:"+people+
                '}';
    }
}
