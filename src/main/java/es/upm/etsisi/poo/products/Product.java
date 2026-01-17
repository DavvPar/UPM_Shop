package es.upm.etsisi.poo.products;
import es.upm.etsisi.poo.enums.ProductType;

import java.io.Serializable;

public abstract class Product implements Serializable {

    private final String ID;

    private ProductType type;
    public Product() {
        this.ID = "";
        this.type = null;
    }
    public Product(String ID,ProductType type){
        this.ID =ID;
        this.type = type;
    }
    public abstract Product CloneProduct();

    public abstract String toString();

    public String getID(){return ID;}

    public ProductType getProductType(){return type;}
}
