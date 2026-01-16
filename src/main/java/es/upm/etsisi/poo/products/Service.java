package es.upm.etsisi.poo.products;
import es.upm.etsisi.poo.Utils;
import es.upm.etsisi.poo.enums.ProductType;
import es.upm.etsisi.poo.enums.ServiceType;

import java.io.Serializable;

public class Service extends Product {

    private ServiceType type;

    private String expirationDate;
    public Service() {
        super();
        this.type = null;
        this.expirationDate = "";
    }
    public Service(String ID,ServiceType typeS,String expitationDate,ProductType type){
        super(ID,type);
        this.type = typeS;
        this.expirationDate = expitationDate;
    }

    private String getIDNumber(String ID) {
        if (ID == null || ID.isEmpty()) return "";
        return ID.replaceAll("[^0-9]", "");
    }

    public ServiceType getType() {
        return type;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    @Override
    public Product CloneProduct() {
        return new Service(getID(), type,expirationDate,ProductType.Service);
    }

    @Override
    public String toString() {
        //{class:ProductService, id:1, category:INSURANCE, expiration:Sun Dec 21 00:00:00 CET 2025}
        return "{class:ProductService, id:"+getIDNumber(getID())+
                ", category:"+ type +
                ", expiration:"+
                Utils.convertDate(expirationDate)+"}";
    }
}

