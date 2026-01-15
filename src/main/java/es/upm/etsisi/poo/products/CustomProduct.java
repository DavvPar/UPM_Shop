package es.upm.etsisi.poo.products;
import es.upm.etsisi.poo.enums.ProductType;
import es.upm.etsisi.poo.enums.Category;
import java.util.ArrayList;
import java.util.List;

public class CustomProduct extends Item{

    private ArrayList<String> personalization;

    private final int maxPers;

    private Category category;

    private int N_Pers;

    public CustomProduct(String ID, String name, Category category, double price, int maxPers,ProductType type) {
        super(ID, name, price, type);
        this.category = category;
        this.maxPers = maxPers;
        this.personalization = new ArrayList<>();
        N_Pers = 0;
    }

    public boolean allowedText(String[] text){
        if(text.length > maxPers ) {
            System.out.println("Too many customizations, the customization limit is"+maxPers);
            return false;
        }
        return true;
    }

    public boolean addPersonalized(String personalized){
        boolean add = false;
        // eliminar el primer --p
        // porque si no al hacer split deja r[0]="" haciendo que haya 4 elementos en r
        String message = personalized.substring(3);
        String[] r = message.trim().split("--p");
            if (allowedText(r)){
                for (int i=0;i<r.length;i++){
                    if (!r[i].isEmpty()){
                personalization.add(r[i]);
                N_Pers++;
                    }
                }
                    super.setPrice(super.getPrice()*(1+(0.1*N_Pers)));
            }
        return add;
    }

    public Category getCategory(){return category;}

    public void setCategory(Category category){this.category = category;}

    @Override
    public double getPrice() {
        return super.getPrice();
    }
    public int getMaxPers(){return maxPers;}
    public String getPersonalization(){
        String resul ="";
        for (int i=0;i<personalization.size();i++){
                resul += "--p"+personalization.get(i);
        }
        return resul;
    }

    @Override
    public Product CloneProduct() {
        CustomProduct New;
        New = new CustomProduct(getID(),getName(),getCategory(),getPrice(),getMaxPers(),getProductType());
        if (!personalization.isEmpty()){
        String message = getPersonalization();
            New.addPersonalized(message);
        }
        return New;
    }

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
                ", id:"+getID() +
                ", name:'"+getName() +
                "', "+ getCategory().toString() +
                ", price:"+ String.format("%.2f", getPrice()) +
                r +
                "}";
    }
}
