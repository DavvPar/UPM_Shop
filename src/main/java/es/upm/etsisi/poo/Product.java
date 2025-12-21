package es.upm.etsisi.poo;

/**
 * Product is a class that encapsules all the attributes
 * that define a unique product, it is used to process
 * them and show its data.
 */

enum ProductType{
    ProductPersonalized,
    Product,
    Meeting,
    Food,
}

public abstract class  Product {
    /**
     * ID for unique identification of the product
     */
    private final int ID;
    /**
    /**
     * Name of the product
     */
    private String name;
    /**
     * Price of the product with no discounts
     */
    private double price;
    private ProductType type;


    /**
     * Constructor of the Class Product
     * Checks if the entering values are valid for a Product
     * @param ID product ID
     * @param name product name
     * @param price product price
     */

    public Product(int ID, String name, double price,ProductType types) {
        if(ID <= 0)
            throw new IllegalArgumentException("ID must be positive.");
        if(name == null || name.length() >= 100 || name.trim().isEmpty()) //name.trim().isEmpty() -> para que no acepte que entre comillas este vacio
            throw new IllegalArgumentException("Invalid name.");
        if(price <= 0)
            throw new IllegalArgumentException("Price must be positive.");
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.type = types;

    }

    /**
     * Getter for the productType
     * @return productType
     */
    public  ProductType getProductType(){return type;}
    /**
     * Getter for the ID
     * @return ID of the product
     */
    public int getID() {
        return ID;
    }
    /**
     * Getter for the name
     * @return Name of the product
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter for the name
     * @param name name
     */
    public void setName(String name) {
        if (name == null || name.length() >= 100)
            throw new IllegalArgumentException("Invalid name.");
        this.name = name;
    }

    /**
     * Getter for the price
     * @return Price of the product
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setter for the price
     * @param price quantity
     */
    public void setPrice(double price) {
        if (price <= 0)
            throw new IllegalArgumentException("Price must be positive.");
        this.price = price;
    }
    /**
     * @return
     */
    public abstract Product CloneProduct();

    /**
     * toString for the object Product, showing its ID, name,
     * category and price, also tied to Category class toString method
     * @return String with product data
     */
    @Override
    public abstract String toString();
}

