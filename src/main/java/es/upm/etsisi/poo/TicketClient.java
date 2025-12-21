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

    public boolean addProductToTicket(ProductList lista, int Id, int quantity, String message){
        boolean add = false;
        Product p =lista.getProduct(Id).CloneProduct();
        if (p.getProductType() == ProductType.ProductPersonalized && !message.isEmpty()){
            ((CustomProduct)p).addPersonalized(message);
        }
        for (int i =0;i<quantity;i++){
            if (NumProductInTicket < 100){
                super.productList[NumProductInTicket] = p;
                NumProductInTicket++;
                add = true;
                if (super.state == stateTicket.empty){
                    super.state = stateTicket.open;
                }
            }
            else {
                System.out.println("No further products can be added.");
            }
        }
        return add;
    }
    public String toString() {
        String message = "";
        Arrays.sort(productList, Comparator.nullsLast(
                Comparator.comparing(
                        lp -> lp.getName(),
                        String.CASE_INSENSITIVE_ORDER
                )
        ));
        applyDiscunt();
        for (int i =0;i<NumProductInTicket;i++){
            if (discount[i]>0) {
                message += "  "  + productList[i].toString() + "**discount -" + discount[i] + "\n";
            }
            else{
                message += productList[i].toString() + "\n";
            }
        }
        return "Ticket : "+ ticketId + "\n" +message +"  Total price: "+getTotalPrice() +"\n"
                + "  Total discount: "+ getTotalDiscount() +"\n"
                + "  Final Price: " + getFinalPrice();

    }
}
