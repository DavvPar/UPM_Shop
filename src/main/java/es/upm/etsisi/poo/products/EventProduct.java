package es.upm.etsisi.poo.products;
import es.upm.etsisi.poo.enums.ProductType;

public class EventProduct extends Item{

    private String expirationDate;

    private Double priceP;

    private int people;

    private int MAX_PEOPLE;

    public EventProduct(String ID, String name, double price, String expirationDate, int people, ProductType type) {
        super(ID, name, price, type);
        MAX_PEOPLE = people;
        this.expirationDate = expirationDate;
        this.people =0;
        this.priceP = price;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        if(people <= 0 || people > MAX_PEOPLE)
            throw new IllegalArgumentException("The number of participants must be between 1 and " + MAX_PEOPLE);
        this.people = people;
        super.setPrice((people*priceP*100)/100);

    }

    public int getMAX_PEOPLE() {
        return MAX_PEOPLE;
    }

    @Override
    public Product CloneProduct() {
        EventProduct c =new EventProduct(getID(),getName(),priceP,expirationDate,MAX_PEOPLE,getProductType());
        c.setPeople(people);
        return c;
    }

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