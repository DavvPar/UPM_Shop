package es.upm.etsisi.poo;

/**
 * ComplexProduct is a class for instancing products with more complex
 * requirements such as foods and meetings
 * They have new attributes, such as time limits, people quantity and type
 */
public class ComplexProduct extends Product{
    /**
     * Date of expiration (no longer valid)
     */
    private String expirationDate;
    /**
     * Number of people permitted in the product
     */
    private int people;
    /**
     * Maximum possible number of people is 100
     */
    private final int MAX_PEOPLE = 100;
    /**
     * Type of product (Food/Meeting)
     */
    private ProductType type;

    /**
     * Constructor of the Class ComplexProduct.
     * Checks if the entering values are valid for a ComplexProduct.
     *
     * @param ID product ID
     * @param name product name
     * @param price  product price per person
     * @param expirationDate date of validness
     * @param people permitted people
     * @param type food/meeting type
     */
    public ComplexProduct(int ID, String name, double price, String expirationDate, int people,ProductType type) {
        super(ID, name, price);
        this.type = type;
        if(people <= 0 || people > MAX_PEOPLE){
            throw new IllegalArgumentException("The number of participants must be between 1 and " + MAX_PEOPLE);
           // throw new IllegalArgumentException("The expiration date cannot be earlier than today.");
        }
        this.expirationDate = expirationDate;
        this.people = people;
    }

    /**
     * Getter for expirationDate
     * @return expiration date
     */
    public String getExpirationDate() {
        return expirationDate;
    }
    /**
     * Setter of expiration date
     * @param expirationDate expiration date
     */
    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
    /**
     * Getter for number of people
     * @return number of people
     */
    public int getPeople() {
        return people;
    }
    /**
     * Setter for number of people
     * @param people max quantity
     */
    public void setPeople(int people) {
        if(people <= 0 || people > MAX_PEOPLE)
            throw new IllegalArgumentException("The number of participants must be between 1 and " + MAX_PEOPLE);
        this.people = people;
    }
    /**
     * Getter for the maximum number of people
     * @return max_people
     */
    public int getMAX_PEOPLE() {
        return MAX_PEOPLE;
    }
    /**
     * Getter for product type
     * @return product type
     */
    @Override
    public ProductType getProductType() {
        return type;
    }

    /**
     * toString of the object ComplexProduct, showing its id,
     * name, price and date, maximum people and actual people.
     * @return String containing product info
     */
    @Override
    public String toString(){
        return "{Product" +
                ", id:" + getID() +
                ", name:'" + getName() + '\'' +
                ", price:" + String.format("%.2f", getPrice()) +
                ", date of Event:" + expirationDate +
                ", max of people allowed:" + MAX_PEOPLE +
                ", actual people in event:"+people+
                '}';
    }
}
