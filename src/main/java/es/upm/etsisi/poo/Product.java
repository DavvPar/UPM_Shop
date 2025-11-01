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
     * Checks if the entering values are valid for a Product.
     *
     * @param ID product ID
     * @param name product name
     * @param price product price
     */
    public Product(int ID, String name, Category category, double price) {
        if(ID <= 0)
            throw new IllegalArgumentException("ID must be positive.");
        if(name == null || name.length() >= 100 || name.trim().isEmpty()) //name.trim().isEmpty() -> para que no acepte que entre comillas este vacio
            throw new IllegalArgumentException("Invalid name.");
        if(category == null || category.getType() == null) //TODO
            throw new IllegalArgumentException("Category must be: MERCH, STATIONERY, CLOTHES, BOOK, ELECTRONICS");
        if(price <= 0)
            throw new IllegalArgumentException("Price must be positive.");

        this.ID = ID;
        this.name = name;
        this.category = category;
        this.price = price;
    }
    /**
     * Getter for the ID.
     *
     * @return ID of the product
     */
    public int getID() {
        return ID;
    }
    /**
     * Getter for category.
     *
     * @return Category of the product
     */
    public Category getCategory() {
        return this.category;
    }
    /**
     * Setter for category.
     */
    public void setCategory(Category category) {
        if (category == null)
            throw new IllegalArgumentException("Category cannot be null.");
        this.category = category;
    }
    /**
     * Getter for the name.
     *
     * @return Name of the product
     */
    public String getName() {
        return this.name;
    }
    /**
     * Setter for the name.
     */
    public void setName(String name) {
        if (name == null || name.length() >= 100)
            throw new IllegalArgumentException("Invalid name.");
        this.name = name;
    }
    /**
     * Getter for the price.
     *
     * @return Price of the product
     */
    public double getPrice() {
        return this.price;
    }
    /**
     * Setter for the price.
     */
    public void setPrice(double price) {
        if (price <= 0)
            throw new IllegalArgumentException("Price must be positive.");
        this.price = price;
    }

    /**
     * toString for the object Product, showing its ID, name,
     * category and price, also tied to Category class toString method.
     *
     * @return String with product data
     */
    @Override
    public String toString() {
        return "{class:Product" +
                ", id:" + ID +
                ", name:'" + name + '\'' +
                ", " + category.toString() +
                ", price:" + String.format("%.2f", price) +
                '}';
    }
}
