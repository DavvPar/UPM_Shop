package es.upm.etsisi.poo;

import java.time.LocalDate;

public class ComplexProduct extends Product{
    private LocalDate expirationDate;
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
    public ComplexProduct(int ID, String name, double price, LocalDate expirationDate, int people) {
        super(ID, name, price);
        if(people <= 0 || people > MAX_PEOPLE)
            throw new IllegalArgumentException("The number of participants must be between 1 and " + MAX_PEOPLE);
        LocalDate today = LocalDate.now();
        if(expirationDate.isBefore(today))
            throw new IllegalArgumentException("The expiration date cannot be earlier than today.");
        this.expirationDate = expirationDate;
        this.people = people;
    }


    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
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
    public String toString(){
        return "{Product" +
                ", id:" + getID() +
                ", name:'" + getName() + '\'' +
                ", price:" + String.format("%.2f", getPrice()) +
                ", date of Event:" + expirationDate +
                ", people:" + people +
                '}';
    }
}
