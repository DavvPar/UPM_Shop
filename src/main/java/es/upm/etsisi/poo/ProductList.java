package es.upm.etsisi.poo;

public class ProductList {
    private Product[] products;
    private int maxNumProduct;
    private int numProduct;

    /**
     * empty product list builder
     *
     * @param MaxNumProduct maximum number of products that can be added
     */
    public ProductList(int MaxNumProduct) {
        numProduct = 0;
        this.maxNumProduct = MaxNumProduct;
        products = new Product[MaxNumProduct];
    }

    /**
     * constructor of the productList class to load from saved files
     *
     * @param MaxNumProduct maximum number of products that can be added
     * @param filename      name of the productList
     */
    public ProductList(int MaxNumProduct, String filename) {

    }

    /**
     * add list product
     *
     * @param product product will add
     * @return
     */
    public boolean addProduct(Product product) {
        boolean added = false, exists = false;
        if (numProduct < maxNumProduct) {
            for (int i = 0; i < numProduct; i++) {
                if (product.getID() == products[i].getID()) {
                    exists = true;
                }
            }
            if (!exists) {
                products[numProduct] = product;
                numProduct++;
                added = true;
            } else
                System.out.println("The product already exists");
        } else
            System.out.println("No further products can be added");
        return added;
    }

    public boolean listProducts() {
        boolean listed = false;
        if (numProduct == 0) {
            System.out.println("Empty list");
        } else {
            System.out.println("Catalog:");

            for (int i = 0; i < numProduct; i++) {
                Product p = products[i];
                System.out.printf(" {class:Product, id:%d, name:'%s', category:%s, price:%.2f}%n",
                        p.getID(),
                        p.getName(),
                        p.getCategory().getType(),
                        p.getPrice());
            }
            listed = true;
        }
        return listed;
    }


    public boolean updateProduct(int idToUpdate, String field, String value) {
        boolean updated = false;
        if (numProduct == 0)
            System.out.println("Empty List");
        else {
            try {
                Product productToUpdate = getProduct(idToUpdate);
                if (productToUpdate != null) {
                    if (field.equalsIgnoreCase("NAME")) { //TODO IMPRIME LAS COMILLAS Y NO FUNCIONA CON ESPACIOS
                        productToUpdate.setName(value);
                        updated = true;
                    } else if (field.equalsIgnoreCase("CATEGORY")) {
                        CategoryType type = CategoryType.valueOf(value.toUpperCase());
                        productToUpdate.setCategory(new Category(type));
                        updated = true;
                    } else if (field.equalsIgnoreCase("PRICE")) {
                        double newPrice = Double.parseDouble(value);
                        productToUpdate.setPrice(newPrice);
                        updated = true;
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
     * remove list product
     *
     * @param selected product will remove
     */
    public boolean removeProduct(Product selected) {
        boolean removed = false;
        for (int i = 0; i < numProduct; i++) {
            if (products[i].getID() == selected.getID()) {
                for (int j = i + 1; j < numProduct; j++) {
                    products[j - 1] = products[j];
                }
                numProduct--;
                products[numProduct] = null;
                removed = true;
            }
        }
        return removed;
    }

    /**
     * obtain product by id
     *
     * @param Id product id
     * @return
     */
    public Product getProduct(int Id) {
        Product find = null;
        for (int i = 0; i < numProduct; i++) {
            if (products[i].getID() == Id) {
                find = products[i];
            }
        }
        return find;
    }

    /**
     * toString
     *
     * @return ProductList
     */
    @Override
    public String toString() {
        String text = "Catalog:\n";
        for (int i = 0; i < numProduct; i++) {
            Product p = products[i];
            text += "  {class:Product, id:" + p.getID()
                    + ", name:'" + p.getName()
                    + "', category:" + p.getCategory().getType()
                    + ", price:" + String.format("%.1f", (double) p.getPrice())
                    + "}\n";
        }
        text += "prod list: ok";
        return text;
    }
}
