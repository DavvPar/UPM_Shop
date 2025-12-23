package es.upm.etsisi.poo;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * ProductList is a class created to manage all types of products in a list,
 * adding them, removing them and basically operating with products
 */
public class ProductList {
    /**
     * Array of products to create the list of products
     */
    private ArrayList<Product> products;
    /**
     * Maximum number of products in the list
     */
    private int maxNumProduct;
    /**
     * Current number of products in the list
     */
    private int numProduct;

    /**
     * Constructor of the class
     * Creates an empty product list
     *
     * @param MaxNumProduct maximum number of products that can be added
     */
    public ProductList(int MaxNumProduct) {
        numProduct = 0;
        this.maxNumProduct = MaxNumProduct;
        products = new ArrayList<>();
    }
    /**
     * Adds a passed product to the product list
     * @param product product will add
     * @return true if added, false if failed
     */
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


    /**
     * Lists all the products from the list
     * @return true if successful, false if not
     */
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

    /**
     * Updates the NAME, CATEGORY or PRICE of the product with
     * passed ID
     * @param idToUpdate ID of the product to update
     * @param field The field that will be changed on the product
     * @param value New value to set the field to
     * @return true if updated, false if not
     */
    public boolean updateProduct(String idToUpdate, String field, String value) {
        boolean updated = false;
        if (numProduct == 0)
            System.out.println("Empty List");
        else {
            try {
                Product productToUpdate = getProduct(idToUpdate);
                if (productToUpdate != null) {
                    if (productToUpdate.getProductType() != ProductType.Service){
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
                    }
                } else {
                    System.out.println("The product doesn't exist");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        return updated;
    }

    /**
     * Removes the selected product from the list, checking
     * if it is in the list and removing all its instances
     * @param selected product will remove
     */
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

    /**
     * Getter for a Product using its ID to identify
     * @param Id product ID
     * @return Product with said ID
     */
    public Product getProduct(String Id) {
        Product find = null;
        for (int i = 0; i < numProduct; i++) {
            if (products.get(i).getID().equals(Id)) {
                find = products.get(i);
            }
        }
        return find;
    }

    /**
     * Getter of number of products
     * @return numProduct
     */
    public int getNumProduct(){
        return numProduct;
    }

    /**
     * toString for the list of products, showing all
     * the products in the list
     * @return ProductList string
     */
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
