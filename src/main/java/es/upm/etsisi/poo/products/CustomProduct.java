package es.upm.etsisi.poo.products;
import es.upm.etsisi.poo.enums.ProductType;
import es.upm.etsisi.poo.enums.Category;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

public class CustomProduct extends Item {
    private ArrayList<String> personalization;
    private int maxPers;
    private Category category;
    private int N_Pers;
    public CustomProduct() {
        super();
        this.personalization = new ArrayList<>();
        this.maxPers = 0;
        this.category = null;
        this.N_Pers = 0;
    }
    public CustomProduct(String ID, String name, Category category, double price, int maxPers, ProductType type) {
        super(ID, name, price, type);
        this.category = category;
        this.maxPers = maxPers;
        this.personalization = new ArrayList<>();
        N_Pers = 0;
    }

    public String getPersonalization() {
        if (personalization == null || personalization.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        for (String item : personalization) {
            if (!item.isEmpty()) {
                result.append("--p").append(item);
            }
        }
        return result.toString();
    }

    @JsonSetter("personalization")
    public void setPersonalization(Object personalizationValue) {
        this.personalization = new ArrayList<>();
        this.N_Pers = 0;
        if (personalizationValue == null) {
            return;
        }
        if (personalizationValue instanceof String) {
            String personalizationString = (String) personalizationValue;

            if (personalizationString == null || personalizationString.trim().isEmpty() || personalizationString.equals("\"\"")) {
                return;
            }
            String toProcess = personalizationString;
            if (!toProcess.startsWith("--p")) {
                toProcess = "--p" + toProcess;
            }

            addPersonalized(toProcess);
        }
        else if (personalizationValue instanceof ArrayList) {
            ArrayList<String> list = (ArrayList<String>) personalizationValue;
            for (String item : list) {
                if (item != null && !item.trim().isEmpty()) {
                    this.personalization.add(item.trim());
                    this.N_Pers++;
                }
            }
            if (this.N_Pers > 0) {
                super.setPrice(super.getPrice() * (1 + (0.1 * this.N_Pers)));
            }
        }
    }

    public boolean addPersonalized(String personalized) {
        boolean add = false;
        String message = personalized.substring(3);
        String[] r = message.trim().split("--p");
        if (allowedText(r)) {
            for (int i = 0; i < r.length; i++) {
                if (!r[i].isEmpty()) {
                    personalization.add(r[i]);
                    N_Pers++;
                    add = true;
                }
            }
            if (add) {
                super.setPrice(super.getPrice() * (1 + (0.1 * N_Pers)));
            }
        }
        return add;
    }
    @JsonIgnore
    public ArrayList<String> getPersonalizationList() {
        return personalization;
    }

    public boolean allowedText(String[] text) {
        if (text.length > maxPers) {
            System.out.println("Too many customizations, the customization limit is " + maxPers);
            return false;
        }
        return true;
    }
    // no borres son setter/getter necesario para queJson funcione
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public int getMaxPers() { return maxPers; }
    public void setMaxPers(int maxPers) { this.maxPers = maxPers; }
    public int getN_Pers() { return N_Pers; }
    public void setN_Pers(int n_Pers) { N_Pers = n_Pers; }

    @Override
    public Product CloneProduct() {
        CustomProduct newProduct = new CustomProduct(
                getID(),
                getName(),
                getCategory(),
                getPrice(),
                getMaxPers(),
                getProductType()
        );
        if (!personalization.isEmpty()) {
            String message = getPersonalization();
            newProduct.addPersonalized(message);
        }
        return newProduct;
    }

    @Override
    public String toString() {
        String r = "";
        if (super.getProductType() == ProductType.ProductPersonalized) {
            r = ", maxPersonal:" + maxPers;
            if (!personalization.isEmpty()) {
                r += ", personalizationList:[";
                for (int i = 0; i < personalization.size(); i++) {
                    if (i > 0) r += ", ";
                    r += personalization.get(i);
                }
                r += "]";
            }
        }
        return "{class:" + getProductType() +
                ", id:" + getID() +
                ", name:'" + getName() + '\'' +
                ", " + (category != null ? category.toString() : "category: null") +
                ", price:" + String.format("%.2f", getPrice()) +
                r +
                "}";
    }
}