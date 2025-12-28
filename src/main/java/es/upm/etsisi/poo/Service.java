package es.upm.etsisi.poo;
enum ServiceType{
    INSURANCE,
    TRANSPORT,
    ENTERTAINMENT
}

public class Service extends Product{
    private ServiceType types;
    private String expirationDate;

    public Service(String ID,ServiceType typeS,String expitationDate,ProductType type){
        super(ID,type);
        this.types = typeS;
        this.expirationDate = expitationDate;
    }

    private String getIDNumber(String ID) {
        if (ID == null || ID.isEmpty()) return "";
        return ID.replaceAll("[^0-9]", "");
    }

    @Override
    public Product CloneProduct() {
        return new Service(getID(),types,expirationDate,ProductType.Service);
    }

    @Override
    public String toString() {
        //{class:ProductService, id:1, category:INSURANCE, expiration:Sun Dec 21 00:00:00 CET 2025}
        return "{class:ProductService, id:"+getIDNumber(getID())+
                ", category:"+types+
                ", expiration:"+
                expirationDate+"}";
    }
}

