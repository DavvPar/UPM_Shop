package es.upm.etsisi.poo;

public class Productlist {
private Product[] products;
private int MaxNumProduct;
private int NumProduct;

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
}
