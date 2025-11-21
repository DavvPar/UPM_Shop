package es.upm.etsisi.poo;


import java.util.ArrayList;
import java.util.List;

public class CustomProduct extends Product{
    private List<String> personalization;
    private int maxPers;
    private ProductType type;
    private Category category;

    /**
     * class constructor
     * @param ID product id
     * @param name product name
     * @param category product category
     * @param price product price
     * @param maxPers maximum customisation
     */
    public CustomProduct(int ID, String name, Category category, double price, int maxPers) {
        super(ID, name, price);
        if (maxPers == -1){
            type = ProductType.Product;
        }
        else {
            type = ProductType.ProductPersonalized;
        }
        this.category = category;
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
    public boolean addPersonalized(String personalized){
        boolean add = false;
        String[] r = personalized.trim().split("--p");
        if (r.length>0){
            for (int i=0;i<r.length;i++){
            personalization.add(r[i]);
            }
        }
        return add;
    }

    @Override
    public ProductType getProductType() {
        return type;
    }
    public Category getCategory(){return category;}

    @Override
    public double getPrice() {
        double complexPrice = 0.10 * getPrice() * maxPers;
        return super.getPrice() + complexPrice;
    }

    @Override
    public String toString() {
        String r = "";
        if (type == ProductType.ProductPersonalized){
            r = ", maxPersonal:"+maxPers;
            if (personalization.size()!=0){
                r+= "\n, personalizationList:[";
                for (int i =0;i<personalization.size();i++){
                    if(i<personalization.size()-1){
                        r+= personalization.get(i)+", ";
                    }
                    else {
                        r+= personalization.get(i)+"]";
                    }
                    }
                }
        }
        return "{class:"+getProductType()+", id:"+super.getID()+", name:'"+super.getName()+"', category:"+
                getCategory()+", price:"+super.getPrice()+r+"}";
    }
}
