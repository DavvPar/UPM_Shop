package es.upm.etsisi.poo;

import java.util.Arrays;
import java.util.Comparator;

public class TicketClient extends Ticket{
    /**
     * Constructor of the Class Ticket.
     *
     * @param idTicket TicketId
     * @param state
     * @param type
     */
    public TicketClient(String idTicket, stateTicket state, TicketType type) {
        super(idTicket, state, type);
    }

    @Override
    public boolean addProductToTicket(ProductList lista, String Id, int quantity, String message) {
        Product p = lista.getProduct(Id);
        boolean add = false;
        if (p.getProductType() != ProductType.Service){
            p = p.CloneProduct();
            add =add(p,quantity,message);
        }
        return add;
    }
    @Override
    public double getTotalDiscount(){
        double totaldiscount =0 ;
        for (int i =0; i < getNumProductInTicket(); i++){
            totaldiscount += getDiscount(i);
        }
        totaldiscount = Math.floor((totaldiscount) * 100) / 100;
        return totaldiscount;
    }


    public String toString() {
        String message = "";
        if (getNumProductInTicket()>0){
        Sort();
        applyDiscunt();
        for (int i =0;i<getNumProductInTicket();i++){
            if (getDiscount(i)>0) {
                message += "  "  + getProduct(i).toString() + "**discount -" + getDiscount(i) + "\n";
            }
            else{
                message += getProduct(i).toString() + "\n";
            }
        }
        }
        return "Ticket : "+ getTicketId() + "\n" +message +"  Total price: "+getTotalPrice() +"\n"
                + "  Total discount: "+ getTotalDiscount() +"\n"
                + "  Final Price: " + getFinalPrice();

    }
}
