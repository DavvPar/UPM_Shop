package es.upm.etsisi.poo;

/**
 * Category is a class that is defined by a type and
 * a discount, it is used to give a operate with products
 * and calculate the discount for each type in a ticket.
 */

public class Category {
    /**
     * Defines the concrete type of Category.
     */
    private CategoryType type;
    /**
     * Percentage of discount for a specific category.
     */
    private double discount;

    /**
     * Constructor of the Class
     *
     * @param type;
     * @param discount;
     */
    public Category(CategoryType type, double discount) {
        this.type = type;
        this.discount = discount;
    }

    /**
     * Getter for the type.
     *
     * @return Type of the category.
     */
    public CategoryType getType() {
        return this.type;
    }
    /**
     * Setter for the type
     */
    public void setType(CategoryType type) {
        this.type = type;
    }
    /**
     * Getter for the discount.
     *
     * @return Discount of the category.
     */
    public double getDiscount() {
        return this.discount;
    }
    /**
     * Setter for the discount.
     */
    public void setDiscount(double discount) {
        this.discount = discount;
    }

    /**
     * Determines the values that the variable
     * type can take.
     */
    public boolean applydiscount(boolean apply){
        apply = false;

        return apply;
    }
    public enum CategoryType {
        MERCH,
        STATIONERY,
        CLOTHES,
        BOOK,
        ELECTRONICS;
    }



}