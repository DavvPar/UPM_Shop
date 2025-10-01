package es.upm.etsisi.poo;
public class Productlist {
private Product[] products;
private int MaxNumProduct;
private int NumProduct;
//falta Tostring
    /**
     * empty product list builder
     * @param MaxNumProduct maximum number of products that can be added
     */
    public Productlist( int MaxNumProduct){
    NumProduct = 0;
    this.MaxNumProduct = MaxNumProduct;
    products = new Product[MaxNumProduct];
}

    /**
     * constructor of the productList class to load from saved files
     * @param MaxNumProduct maximum number of products that can be added
     * @param filename name of the productList
     */
    public Productlist(int MaxNumProduct, String filename){

}

    /**
     * add list product
     * @param product product will add
     * @return
     */
    public boolean addProduct(Product product) {
        boolean added = false, exists =false;
        if (NumProduct<=MaxNumProduct){
            for (int i= 0;i<NumProduct;i++){
                if (product == products[i]){
                    exists = true;
                }
            }
            if (!exists){
                products[NumProduct] = product;
                NumProduct++;
                added = true;
            }
        } else {
            System.out.println("No further products can be added, or they already exist in the system.");
        }
        return added;
    }

    /**
     * remove list product
     * @param selected product will remove
     */
    public void removeProduct(Product selected) {
        for(int i =0;i<NumProduct;i++) {
            if (products[i].getID() == selected.getID()){
                for (int j = i+1; j<NumProduct;j++){
                    products[j-1] = products[j];
                }
                NumProduct--;
                products[NumProduct] = null;

            }

        }
    }

    /**
     * obtain product by id
     * @param Id product id
     * @return
     */
    public Product getProduct(int Id){
        Product find = null;
        for (int i =0;i<NumProduct;i++){
            if (products[i].getID() == Id){
                find = products[i];
            }
        }
        return find;
    }
}
