package es.upm.etsisi.poo;


import java.util.ArrayList;
import java.util.List;

public class CustomProduct extends Product{
    private List<String> personalization;
    private int maxPers;

    public CustomProduct(int ID, String name, Category category, double price, int maxPers) {
        super(ID, name, category, price);
        this.maxPers = maxPers;
        this.personalization = new ArrayList<>();
    }

    public boolean allowedText(String text){
        if(personalization.size() >= maxPers) {
            System.out.println("No more custom text can be added.");
            return false;
        }
        personalization.add(text);
        return true;

    }

    @Override
    public double getPrice() {
        double complexPrice = 0.10 * getPrice() * maxPers;
        return super.getPrice() + complexPrice;
    }

    @Override
    public String toString() {
        return super.toString().replace("}", "") +
                ", max People allowed: " + maxPers +
                '}';
    }
}
