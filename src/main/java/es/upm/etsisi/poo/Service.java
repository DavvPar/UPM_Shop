package es.upm.etsisi.poo;
enum ServiceType{

}

public class Service extends Product{
    public Service(String ID,ProductType type){
        super(ID,type);
    }

    @Override
    public Product CloneProduct() {
        return null;
    }
}

