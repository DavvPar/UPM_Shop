package es.upm.etsisi.poo.products;
import es.upm.etsisi.poo.enums.ProductType;
import es.upm.etsisi.poo.enums.CategoryType;
import es.upm.etsisi.poo.enums.Category;
import java.util.ArrayList;
import java.util.Comparator;

public class ProductList {

    protected ArrayList<Product> products;

    private int maxNumProduct;

    private int numProduct;
    private int Numservice;

    public ProductList(int MaxNumProduct) {
        numProduct = 0;
        this.maxNumProduct = MaxNumProduct;
        products = new ArrayList<>();
        Numservice = 0;
    }

    public boolean addProduct(Product product) {
        boolean added = false, exists = false;
        if (numProduct < maxNumProduct) {
            for (int i = 0; i < numProduct; i++) {
                if (product.getID() == products.get(i).getID()) {
                    exists = true;
                }
            }
            if (!exists) {
                products.add(product);
                if (product.getProductType()==ProductType.Service){
                    Numservice++;
                }
                numProduct++;
                added = true;
                products.sort(Comparator.nullsLast(
                        Comparator.comparing(Product::getID)
                        ));
            } else
                System.out.println("The product already exists");
        } else
            System.out.println("No further products can be added");
        return added;
    }

    public boolean listProducts() {
        if (numProduct == 0) {
            System.out.println("Empty list");
            return false;
        }
        System.out.println("Catalog:");
        for (int i = 0; i < numProduct; i++) {
            System.out.println("  " + products.get(i).toString());
        }
        return true;
    }

    public boolean updateProduct(String idToUpdate, String field, String value) {
        boolean updated = false;
        if (numProduct == 0)
            System.out.println("Empty List");
        else {
            try {
                Product productToUpdate = getProduct(idToUpdate);
                if (productToUpdate != null && productToUpdate.getProductType() != ProductType.Service) {
                    if (field.equalsIgnoreCase("NAME")) {
                        ((Item)productToUpdate).setName(value);
                        updated = true;
                    } else if (field.equalsIgnoreCase("CATEGORY")) {
                        CategoryType type = CategoryType.valueOf(value.toUpperCase());
                        CustomProduct customProduct = (CustomProduct) productToUpdate;
                        customProduct.setCategory(new Category(type));
                        updated = true;
                    } else if (field.equalsIgnoreCase("PRICE")) {
                        double newPrice = Double.parseDouble(value);
                        ((Item)productToUpdate).setPrice(newPrice);
                        updated = true;
                    }

                } else {
                    System.out.println("The product doesn't exist or is service");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        return updated;
    }

    public boolean removeProduct(Product selected) {
        boolean removed = false;
        for (int i = 0; i < numProduct; i++) {
            if (products.get(i).getID() == selected.getID()) {
                products.remove(selected);
                numProduct--;
                removed = true;
            }
        }
        return removed;
    }

    public Product getProduct(String Id) {
        Product find = null;
        for (int i = 0; i < numProduct; i++) {
            if (products.get(i).getID().equals(Id)) {
                find = products.get(i);
            }
        }
        return find;
    }

    public int getNumProduct(){
        return numProduct;
    }

    public int getNumservice(){return Numservice;}

    @Override
    public String toString() {
        String text = "Catalog:\n";
        for (int i = 0; i < numProduct; i++) {
            Product p = products.get(i);
            text += p.toString() + "\n";
        }
        text += "prod list: ok";
        return text;
    }
}
