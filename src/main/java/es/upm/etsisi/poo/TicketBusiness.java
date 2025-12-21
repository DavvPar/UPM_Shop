package es.upm.etsisi.poo;

import java.util.ArrayList;

public class TicketBusiness extends Ticket{

    private ArrayList<Product> service;
    /**
     * Constructor of the Class Ticket.
     *
     * @param idTicket TicketId
     * @param state
     * @param type
     */
    public TicketBusiness(String idTicket, stateTicket state, TicketType type) {
        super(idTicket, state, type);
        service =new ArrayList<>();
    }
    public boolean addProductToTicket(ProductList lista,int Id, int quantity, String message){
        boolean add = false;
        Product p =lista.getProduct(Id).CloneProduct();
        if (p.getProductType() == ProductType.ProductPersonalized && !message.isEmpty()){
            ((CustomProduct)p).addPersonalized(message);
        }
        for (int i =0;i<quantity;i++){
            if (getNumProductInTicket() < 100){
                 add(p);
                setNumProductInTicket(getNumProductInTicket());
                add = true;
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

    @Override
    public double getTotalDiscount(){
        double totaldiscount =0 ;
        for (int i =0; i < getNumProductInTicket(); i++){
            totaldiscount += getDiscount(i);
        }if (service.isEmpty()){
        totaldiscount = Math.floor((totaldiscount) * 100) / 100;
        }
        else {
            totaldiscount = Math.floor((totaldiscount*(1-(service.size()*0.15)))* 100) / 100;
        }
        return totaldiscount;
    }

    @Override
    public String toString() {
        return "";
    }
}
