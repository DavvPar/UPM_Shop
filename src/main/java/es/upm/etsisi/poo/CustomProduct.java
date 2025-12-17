package es.upm.etsisi.poo;


import java.util.ArrayList;
import java.util.List;
/**
 * CustomProduct is a class for instancing simple products that can be customized
 * They have basic attributes and sometimes can be personalized by the user, adding
 * a new layer to them
 */
public class CustomProduct extends Product{
    /**
     * List of personalizations
     */
    private ArrayList<String> personalization;
    /**
     * Maximum number of personalizations
     */
    private int maxPers;
    /**
     * Category of product
     */
    private Category category;
    /**
     * Current personalizations
     */
    private int N_Pers;

    /**
     * class constructor
     * @param ID product id
     * @param name product name
     * @param category product category
     * @param price product price
     * @param maxPers maximum personalizations for product
     */
    public CustomProduct(int ID, String name, Category category, double price, int maxPers,ProductType type) {
        super(ID, name, price, type);
        this.category = category;
        this.maxPers = maxPers;
        this.personalization = new ArrayList<>();
        N_Pers = 0;

    }

    /**
     * Boolean to check if the text on the personalization is valid
     * @param text text to check
     * @return true if valid, false if invalid
     */
    public boolean allowedText(ArrayList<String> text){
        if(text.size() > maxPers ) {
            System.out.println("No more custom text can be added.");
            return false;
        }
        return true;

    }

    /**
     * Tries to add String personalized to the personalizations
     * of the product, checking if there are personalizations left to do
     * on it and if the text is valid
     * @param personalized text to add if valid
     * @return true if added, false if not
     */
    public boolean addPersonalized(String personalized){
        boolean add = false;
        String[] r = personalized.trim().split("--p");
                for (int i=0;i<r.length;i++){
                    if (!r[i].isEmpty() || (allowedText(personalization))){
                personalization.add(r[i]);
                N_Pers++;
                    }
                }
                    super.setPrice(super.getPrice()*(1+(0.1*N_Pers)));
        return add;
    }

    /**
     * Getter for category
     * @return category
     */
    public Category getCategory(){return category;}
    /**
     * Setter for category
     * @param category category
     */
    public void setCategory(Category category){this.category = category;}
    /**
     * Getter for the price
     * @return price
     */
    @Override
    public double getPrice() {
        return super.getPrice();
    }
    public int getMaxPers(){return maxPers;}
    /**
     * toString of the object CustomProduct, showing its id,
     * name, category, price and personalizations
     * @return String containing product info
     */
    @Override
    public String toString() {
        String r = "";
        if (super.getProductType() == ProductType.ProductPersonalized){
            r = ", maxPersonal:"+maxPers;
            if (!personalization.isEmpty()) {
                r += ", personalizationList:[";
                if (personalization.size() > 1) {
                    for (int i = 0; i < personalization.size(); i++) {
                        if (i < personalization.size()-1) {
                            r += personalization.get(i) + ", ";
                        } else {
                            r += personalization.get(i) + "]";
                        }
                    }
                }
            }
        }
        return "{class:"+getProductType() +
                ", id:"+super.getID() +
                ", name:'"+super.getName() +
                "', "+ getCategory().toString() +
                ", price:"+super.getPrice() +
                r +
                "}";
    }
}
