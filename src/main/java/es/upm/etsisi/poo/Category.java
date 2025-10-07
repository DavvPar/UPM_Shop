package es.upm.etsisi.poo;

/**
 * Category is a class that is defined by a type and
 * a discount, it is used to give a operate with products
 * and calculate the discount for each type in a ticket.
 */
enum CategoryType {
    MERCH,
    STATIONERY,
    CLOTHES,
    BOOK,
    ELECTRONICS
}
public class Category {
    /**
     * Defines the concrete type of Category.
     */
    private CategoryType type;
    /**
     * Percentage of discount for a specific category.
     */
    private double discountMerch = 0;
    private double discountStationery= 0.05;
    private double discountClothes=0.07;
    private double discountBook = 0.10;
    private double discountElectronic=0.03;
    /**
     * Constructor of the Class
     *
     * @param type;
     */
    public Category(CategoryType type) {
        this.type = type;
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
        double discount = 0;
        switch (getType()){
            case ELECTRONICS -> discount =discountElectronic;
            case STATIONERY -> discount = discountStationery;
            case CLOTHES -> discount =discountClothes;
            case BOOK -> discount = discountBook;
            case MERCH -> discount =discountMerch;
        }
        return discount;
    }
    /**
     * Setter for the discount.
     */
    public void setDiscount(double discount) {
        switch (getType()){
            case ELECTRONICS -> discountElectronic = discount;
            case STATIONERY -> discountStationery = discount;
            case CLOTHES -> discountClothes = discount;
            case BOOK ->  discountBook = discount;
            case MERCH -> discountMerch = discount;
        }
    }



    /**
     * Checks if the discounts will be applied, which
     * happens if there is 2 or more products of the same
     * Category.
     *
     * @param ticket
     * @return
     */

    /**
     * Determines the valid values that the variable
     * type can take.
     */

    @Override
    public String toString() {
        return "category: " + type;
    }
}