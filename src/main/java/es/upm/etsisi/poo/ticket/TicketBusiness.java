package es.upm.etsisi.poo.ticket;
import java.io.Serializable;
import java.util.ArrayList;
import es.upm.etsisi.poo.products.*;
import es.upm.etsisi.poo.enums.stateTicket;
import es.upm.etsisi.poo.enums.TicketType;
import es.upm.etsisi.poo.enums.ProductType;

public class TicketBusiness<P extends Product> extends Ticket<P>implements Serializable {
    private double serviceDiscount;

    public TicketBusiness(String idTicket, stateTicket state, TicketType type) {
        super(idTicket, state, type);
        serviceDiscount = 0.15;
    }

    public void setServiceDiscount(Double discount){
        this.serviceDiscount = discount;
    }
    @Override
    public boolean addProductToTicket(ProductList lista, String Id, int quantity, String message) {
        Product p = lista.getProduct(Id);
        boolean add = false;
        if (allowedB(p.getProductType())){
            p = p.CloneProduct();
            add =add(p,quantity,message);
        }
        return add;
    }

    private boolean allowedB(ProductType productType){
        boolean allow = false;
        switch (getType()){
            case businessC -> allow = true;
            case businessP -> {
                if (productType != ProductType.Service) allow = true;
            }
            case businessS -> {
                if (productType == ProductType.Service)allow = true;
            }
        }
        return allow;
    }

    private double getDiscountProduct(){
        double totaldiscount =0 ;
        for (int i =0; i < getNumProductInTicket(); i++){
            totaldiscount += getDiscount(i);

        }
        return totaldiscount;
    }
    @Override
    public double getTotalDiscount(){
        double totaldiscount =getDiscountProduct() ;
        if (NumService() ==0){
        totaldiscount = Math.floor((totaldiscount) * 100) / 100;
        }
        else {
            totaldiscount = Math.floor((Math.min(totaldiscount+getDiscountServece(),getTotalPrice()))* 100) / 100;
        }
        return totaldiscount;
    }
    private double getDiscountServece(){
        return Math.min(getTotalPrice()*(serviceDiscount*NumService()),getTotalPrice()-getDiscountProduct());
    }

    @Override
    public String toString() {
        boolean exitS=false,exitP =false;
        String Mservice="Services Included: \n",Mproduct="Product Included: \n";
        String DiscountM ="";
        Sort();
        applyDiscunt();
        for (int i=0;i<getNumProductInTicket();i++){
            Product p = getProduct(i);
            if (p.getProductType() == ProductType.Service){
                Mservice += p +"\n";
                exitS = true;
            }else {
                if (getDiscount(i)>0) {
                    Mproduct += "  "  + p + "**discount -" + String.format("%.2f",getDiscount(i)) + "\n";
                }
                else{
                    Mproduct += p+ "\n";
                }
                exitP = true;
            }
        }
        if (!exitS) Mservice ="";
        else{
            DiscountM +="Extra Discount from services:"+String.format("%.2f",getDiscountServece())+"**discount -"+String.format("%.2f",getDiscountServece());
        }
        if (!exitP) Mproduct ="";
        else {

            return "Ticket : "+ getTicketId() + "\n" +Mservice+Mproduct +
                    "Total price: "+ String.format("%.2f",getTotalPrice()) +"\n"
                    + "Total discount: "+ String.format("%.2f",getTotalDiscount()) +"\n"
                    +DiscountM
                    + "\nFinal Price: " + String.format("%.2f",getFinalPrice());
        }

        return "Ticket : "+ getTicketId() + "\n" +Mservice;
    }
}
