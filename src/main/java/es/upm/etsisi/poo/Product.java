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
     * Indicates the type of product.
     */
    private Category category;
    /**
     * Name of the product.
     */
    private String name;
    /**
     * Price of the product with no discounts.
     */
    private double price;

    /**
     * Constructor of the Class
     *
     * @param ID product ID.
     * @param name product name.
     * @param price product price.
     */
    public Product(int ID, Category category, String name, double price) {
        this.ID = ID;
        this.category = category;
        this.name = name;
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
    /**
     * Getter for category
     *
     * @return Category of the product.
     */
    public Category getCategory() {
        return this.category;
    }
    /**
     * Setter for category
     */
    public void setCategory(Category category) {
        this.category = category;
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
    public double getPrice() {
        return this.price;
    }
    /**
     * Setter for the price.
     */
    public void setPrice(double price) {
        this.price = price;
    }

}