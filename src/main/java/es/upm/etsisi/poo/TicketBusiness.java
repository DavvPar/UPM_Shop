package es.upm.etsisi.poo;

import java.util.ArrayList;

public class TicketBusiness extends Ticket{

    /**
     * Constructor of the Class Ticket.
     *
     * @param idTicket TicketId
     * @param state
     * @param type
     */
    public TicketBusiness(String idTicket, stateTicket state, TicketType type) {
        super(idTicket, state, type);
    }

    public boolean addProductToTicket(ProductList lista,String Id, int quantity, String message){
        boolean add = false;
        boolean allow = false;
        Product p =lista.getProduct(Id).CloneProduct();
        if (p.getProductType() == ProductType.ProductPersonalized && !message.isEmpty()){
            ((CustomProduct)p).addPersonalized(message);
        }
        for (int i =0;i<quantity;i++){
            if (getNumProductInTicket() < 100){
                switch (getType()){
                    case businessC -> allow = true;
                    case businessP -> {
                        if (p.getProductType() != ProductType.Service) allow = true;
                    }
                    case businessS -> {
                        if (p.getProductType() == ProductType.Service)allow = true;
                    }
                }
                if (allow){
                    add(p);
                    add = true;
                }
                if (getState() == stateTicket.empty){
                    setState(stateTicket.open);
                }
            }
            else {
                System.out.println("No further products can be added.");
            }
        }
        return add;
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
        return Math.min(getTotalPrice()*(0.15*NumService()),getTotalPrice()-getDiscountProduct());
    }

    @Override
    public String toString() {
        boolean exitS=false,exitP =false;
        String Mservice="Services Included: \n",Mproduct="Product Included: \n";
        String DiscountM ="";
        Sort();
        applyDiscunt();
        for (int i = 0; i < getNumProductInTicket(); i++){
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
