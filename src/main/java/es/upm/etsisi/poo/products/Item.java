package es.upm.etsisi.poo.products;
import es.upm.etsisi.poo.enums.ProductType;

import java.io.Serializable;

public abstract class Item extends Product {

    private String name;

    private double price;
    public Item() {
        super();
        this.name = "";
        this.price = 0.0;
    }
    public Item(String ID, String name, double price,ProductType types) {
        super(ID,types);
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public abstract Product CloneProduct();

    @Override
    public abstract String toString();
}

