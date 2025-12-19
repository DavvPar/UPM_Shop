package es.upm.etsisi.poo;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Tickets is a class for managing the ticket creation and deletion. It also works with
 * the product list, the prices, the dates and bills.
 */
enum stateTicket{
    empty,
    open,
    closed
}

public class Ticket {
    /**
     * Maximum number of products in a list
     */
    private final int MaxNumProduct;
    /**
     * The list of products on the ticket.
     */
    private Product[] productList;
    /**
     * Stores the discount of each product in the ticket.
     */
    private double[] discount;
    /**
     * Total price of all the products in the ticket.
     */
    private double totalPrice;
    /**
     * Total discount of all the products in the ticket.
     */
    private double totaldiscount;
    /**
     * Number of products currently in the ticket.
     */
    private int NumProductInTicket;
    private String ticketId;
    /**
     * cashId of the cashier that is selling
     */
    /**
     * State of the ticket, empty, open or closed
     */
    private stateTicket state;
    private Utils utils;
    /**
     * Constructor of the Class Ticket.
     @param idTicket TicketId
     */
    public Ticket(String idTicket,stateTicket state) {
        this.ticketId = idTicket;
        this.NumProductInTicket = 0;
        this.MaxNumProduct = 100;
        this.productList = new Product[MaxNumProduct];
        this.totalPrice = 0;
        this.state = state;
        discount = new double[MaxNumProduct];
    }
    /**
     * Method for adding products to a ticket
     * @param Id   Product id to be added
     * @param quantity to be added
     * @return true or false (successful or failed)
     */
    public boolean addProductToTicket(ProductList lista,int Id, int quantity, String message){
        boolean add = false;
        Product p =lista.getProduct(Id).CloneProduct();
        if (p.getProductType() == ProductType.ProductPersonalized && !message.isEmpty()){
            ((CustomProduct)p).addPersonalized(message);
        }
        for (int i =0;i<quantity;i++){
            if (NumProductInTicket < 100){
                    productList[NumProductInTicket] = p;
                    NumProductInTicket++;
                    add = true;
                    if (state == stateTicket.empty){
                        state = stateTicket.open;
                    }
            }
            else {
                System.out.println("No further products can be added.");
            }
        }
        return add;
    }
    /**
     * Remove ticket product
     * @param Id product id will remove
     */
    public void removeProduct(int Id) {
        int i = 0;
        while (i < NumProductInTicket) {
            if (productList[i].getID() == Id) {
                for (int j = i + 1; j < NumProductInTicket; j++) {
                    productList[j - 1] = productList[j];
                }
                NumProductInTicket--;
                productList[NumProductInTicket] = null;
            } else {
                i++;
            }
        }
        if (NumProductInTicket == 0){
            state = stateTicket.empty;
        }
    }

    /**
     * If there are at least 2 products of the same category in the list, it applies
     * the according discount to it.
     */
    public void applyDiscunt(){
        int[] categorytype = new int[5];
        discount = new double[MaxNumProduct];
        for (int i =0;i<NumProductInTicket;i++){
            if (productList[i].getProductType()== ProductType.Product||productList[i].getProductType()== ProductType.ProductPersonalized){
            CustomProduct product = (CustomProduct)productList[i];
            switch (product.getCategory().getType()){
                case MERCH:
                    categorytype[0]++;
                    break;
                case BOOK:
                    categorytype[1]++;
                    break;
                case CLOTHES:
                    categorytype[2]++;
                    break;
                case STATIONERY:
                    categorytype[3]++;
                    break;
                case ELECTRONICS:
                    categorytype[4]++;
                    break;
                default:
                    break;
            }
            }
        }
        for (int i =0;i<NumProductInTicket;i++){
            if (productList[i].getProductType()== ProductType.Product||productList[i].getProductType()== ProductType.ProductPersonalized){
            CustomProduct product = (CustomProduct)productList[i];
            switch (product.getCategory().getType()){
                case MERCH:
                    if (categorytype[0]>=2){
                        discount[i] = Math.floor(productList[i].getPrice()*product.getCategory().getDiscount()*100)/100;
                    }
                    break;
                case BOOK:
                    if (categorytype[1]>=2){
                        discount[i] = Math.floor(productList[i].getPrice()*product.getCategory().getDiscount()*100)/100;
                    }
                    break;
                case CLOTHES:
                    if (categorytype[2]>=2){
                        discount[i] = Math.floor(productList[i].getPrice()*product.getCategory().getDiscount()*100)/100;
                    }
                    break;
                case STATIONERY:
                    if (categorytype[3]>=2){
                        discount[i] = Math.floor(productList[i].getPrice()*product.getCategory().getDiscount()*100)/100;
                    }
                    break;
                case ELECTRONICS:
                    if (categorytype[4]>=2){
                        discount[i] = Math.floor(productList[i].getPrice()*product.getCategory().getDiscount()*100)/100;
                    }
                    break;
                default:
                    break;
            }
            }
        }
    }
    
    /**
     * Returns the total price of the whole product list.
     * Getter TotalPrice
     * @return TotalPrice
     */
    public double getTotalPrice(){
        totalPrice =0;
        for (int i =0; i < NumProductInTicket; i++){
            totalPrice += productList[i].getPrice();
        }
        totalPrice = Math.floor(totalPrice * 100) / 100;
        return totalPrice;
    }

    /**
     * Returns the total discount made in the whole product list
     * Getter TotalDiscount
     * @return TotalDiscount
     */
    public double getTotalDiscount(){
        totaldiscount =0 ;
        for (int i =0; i < NumProductInTicket; i++){
            totaldiscount += discount[i];
        }
        totaldiscount = Math.floor(totaldiscount * 100) / 100;
        return totaldiscount;
    }

    /**
     * Get the short unique part of the id
     * @return shortId
     */
    String getShortId(){
        String shortId;
        if(!(this.getState().equals(stateTicket.closed))){
            shortId = this.getTicketId().substring(this.getTicketId().length()-5);
        }else{
            shortId = this.getTicketId().substring(this.getTicketId().length()-21, this.getTicketId().length()-16);
        }
        return shortId;
    }

    /**
     * Getter for a product by its id
     * @param index product id
     * @return product with said id
     */
    public Product getProduct(int index){return productList[index];}
    /**
     * Getter for number of products in ticket
     * @return number of products
     */
    public int getNumProductInTicket(){return NumProductInTicket;}
    /**
     * Getter for price, taking into account the discount
     * @return final price with applied discount
     */
    public double getFinalPrice(){
        return getTotalPrice() - getTotalDiscount();
    }
    /**
     * Getter for ticket id
     * @return ticket identification
     */
    public String getTicketId(){return ticketId;}
    public void setTicketId(String id){ticketId = id;}
    /**
     * Getter for ticket state
     * @return current ticket state
     */
    public stateTicket getState (){return state;}

    /**
     * Setter for state
     * @param state new ticket state
     */
    public void setState(stateTicket state) {this.state= state;}

    /**
     * Ticket toString, showing all the
     * @return Ticket
     */
    @Override
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