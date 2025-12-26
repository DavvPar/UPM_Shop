package es.upm.etsisi.poo;

/**
 * Item is a class that encapsules all the attributes
 * that define a unique Item, it is used to process
 * them and show its data.
 */


public abstract class  Item extends Product{
    /**
     * ID for unique identification of the product
     */
    private final String ID;
    /**
    /**
     * Name of the Item
     */
    private String name;
    /**
     * Price of the Item with no discounts
     */
    private double price;



    /**
     * Constructor of the Class Item
     * Checks if the entering values are valid for a Product
     * @param ID product ID
     * @param name product name
     * @param price product price
     */

    public Item(String ID, String name, double price,ProductType types) {
        super(ID,types);
        if(Integer.parseInt(ID) <= 0)
            throw new IllegalArgumentException("ID must be positive.");
        if(name == null || name.length() >= 100 || name.trim().isEmpty()) //name.trim().isEmpty() -> para que no acepte que entre comillas este vacio
            throw new IllegalArgumentException("Invalid name.");
        if(price <= 0)
            throw new IllegalArgumentException("Price must be positive.");
        this.ID = ID;
        this.name = name;
        this.price = price;

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

