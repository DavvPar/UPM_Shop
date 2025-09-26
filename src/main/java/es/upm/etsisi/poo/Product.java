package es.upm.etsisi.poo;

/**
 * Product is a class that encapsules all the attributes
 * that define a unique product, it is used to process
 * them and show its data.
 */
public class Product {
    /**
     * ID for unique identification of the product.
     */
    private final int ID;
    /**
     * Name of the product.
     */
    private String name;
    /**
     * Price of the product with no discounts.
     */
    private int price;

    /**
     * Constructor of the Class
     *
     * @param ID product ID.
     * @param name product name.
     * @param price product price.
     */
    public Product(int ID, String name, int price) {
        this.ID = ID;
        this.name = name;
        this.price = price;
    }
    /**
     * Getter for the name.
     *
     * @return Name of the product.
     */
    public String getName() {
        return this.name;
    }
    /**
     * Setter for the name.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Getter for the price.
     *
     * @return Price of the product.
     */
    public int getPrice() {
        return this.price;
    }
    /**
     * Setter for the price.
     */
    public void setPrice(int price) {
        this.price = price;
    }
    /**
     * Getter for the ID.
     *
     * @return ID of the product.
     */
    public int getID() {
        return ID;
    }
}