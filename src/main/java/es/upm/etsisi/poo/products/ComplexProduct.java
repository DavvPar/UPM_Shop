package es.upm.etsisi.poo.products;
import es.upm.etsisi.poo.enums.ProductType;
/**
 * ComplexProduct is a class for instancing products with more complex
 * requirements such as foods and meetings
 * They have new attributes, such as time limits, people quantity and type
 */
public class ComplexProduct extends Item{
    /**
     * Date of expiration (no longer valid)
     */
    private String expirationDate;
    private Double priceP;
    /**
     * Number of people permitted in the product
     */
    private int people;
    /**
     * Maximum possible number of people
     */
    private int MAX_PEOPLE;
    /**
     * Type of product (Food/Meeting)
     */

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
    public ComplexProduct(String ID, String name, double price, String expirationDate, int people, ProductType type) {
        super(ID, name, price, type);
        MAX_PEOPLE = people;
        this.expirationDate = expirationDate;
        this.people =0;
        this.priceP = price;
        if(people < 0 || people > MAX_PEOPLE){
            throw new IllegalArgumentException("The number of participants must be between 1 and " + MAX_PEOPLE);
        }
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
        super.setPrice((people*priceP*100)/100);

    }
    /**
     * Getter for the maximum number of people
     * @return max_people
     */
    public int getMAX_PEOPLE() {
        return MAX_PEOPLE;
    }

    @Override
    public Product CloneProduct() {
        ComplexProduct c =new ComplexProduct(getID(),getName(),priceP,expirationDate,MAX_PEOPLE,getProductType());
        c.setPeople(people);
     return c;
    }

    /**
     * toString of the object ComplexProduct, showing its id,
     * name, price and date, maximum people and actual people.
     * @return String containing product info
     */
    @Override
    public String toString(){
        return "{class:" + getProductType() +
                ", id:" + getID() +
                ", name:'" + getName() + '\'' +
                ", price:" + String.format("%.2f", getPrice()) +
                ", date of Event:" + expirationDate +
                ", max of people allowed:" + MAX_PEOPLE +
                ", actual people in event:"+people+
                '}';
    }
}
