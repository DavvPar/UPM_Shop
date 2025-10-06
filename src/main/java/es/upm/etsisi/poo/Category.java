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
     * Declares all the possible categories on
     * the program with their discount included.
     */
    public Category MERCH = new Category(Category.CategoryType.MERCH, 0.00);
    public Category STATIONERY = new Category(Category.CategoryType.STATIONERY, 0.05);
    public Category CLOTHES = new Category(Category.CategoryType.CLOTHES, 0.07);
    public Category BOOK = new Category(Category.CategoryType.BOOK, 0.10);
    public Category ELECTRONICS = new Category(Category.CategoryType.ELECTRONICS, 0.03);

    /**
     * Checks if the discounts will be applied, which
     * happens if there is 2 or more products of the same
     * Category.
     *
     * @param ticket
     * @return
     */
    public boolean[] applydiscount(Ticket ticket){
        boolean[] apply = new boolean[5];
        return apply;
    }

    /**
     * Determines the valid values that the variable
     * type can take.
     */
    public enum CategoryType {
        MERCH,
        STATIONERY,
        CLOTHES,
        BOOK,
        ELECTRONICS;
    }

}