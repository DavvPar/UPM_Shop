package es.upm.etsisi.poo;
enum ProductType{
    ProductPersonalized,
    Product,
    Meeting,
    Food,
    Service
}
public abstract class Product {
    private final String ID;
    private ProductType type;
    public Product(String ID,ProductType type){
        this.ID =ID;
        this.type = type;
    }
    public abstract Product CloneProduct();
    public abstract String toString();
    public String getID(){return ID;}
    public ProductType getProductType(){return type;}
}
