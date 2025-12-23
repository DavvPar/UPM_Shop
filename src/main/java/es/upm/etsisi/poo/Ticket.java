package es.upm.etsisi.poo;

import java.util.ArrayList;
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
enum TicketType{
    Client,
    business
}

public abstract class Ticket {
    /**
     * Maximum number of products in a list
     */
    public final int MaxNumProduct;
    /**
     * The list of products on the ticket.
     */
    private ArrayList<Product> productList;
    /**
     * Stores the discount of each product in the ticket.
     */
    private ArrayList<Double> discount;
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
    private  String ticketId;
    /**
     *number of product types
     * [0] MERCH,
     * [1]STATIONERY,
     * [2]CLOTHES,
     * [3]BOOK,
     * [4]ELECTRONICS
     */
    private int[] categorytype;
    /**
     * cashId of the cashier that is selling
     */
    /**
     * State of the ticket, empty, open or closed
     */
    private stateTicket state;
    private TicketType type;
    /**
     * Constructor of the Class Ticket.
     @param idTicket TicketId
     */
    public Ticket(String idTicket, stateTicket state, TicketType type) {
        this.ticketId = idTicket;
        this.NumProductInTicket = 0;
        this.MaxNumProduct = 100;
        this.productList = new ArrayList<>();
        this.totalPrice = 0;
        this.state = state;
        this.type =type;
        discount = new ArrayList<>();
        categorytype= new int[5];
    }
    /**
     * Method for adding products to a ticket
     * @return true or false (successful or failed)
     */
    public abstract boolean addProductToTicket(ProductList lista,String Id, int quantity, String message);
    private void SetupDiscount(){
        discount = new ArrayList<>();
        for (int i =0;i<NumProductInTicket;i++){
            Product p =productList.get(i);
            if (p.getProductType() == ProductType.Product || p.getProductType() == ProductType.ProductPersonalized){
                CustomProduct c = (CustomProduct)p;
                switch (c.getCategory().getType()){
                    case MERCH -> categorytype[0]++;
                    case STATIONERY -> categorytype[1]++;
                    case CLOTHES -> categorytype[2]++;
                    case BOOK -> categorytype[3]++;
                    case ELECTRONICS -> categorytype[4]++;
                }
            }
            discount.add(0.0);
        }
    }
    /**
     * If there are at least 2 products of the same category in the list, it applies
     * the according discount to it.
     */
    public void applyDiscunt(){
        SetupDiscount();
        for(int i =0; i<NumProductInTicket;i++){
            Product p = productList.get(i);
            if (p.getProductType() == ProductType.Product || p.getProductType() == ProductType.ProductPersonalized){
                CustomProduct c = (CustomProduct)p;
                switch (c.getCategory().getType()){
                    case MERCH:
                        if (categorytype[0]>=2){
                            discount.set(i,Math.floor(((Item)p).getPrice()*c.getCategory().getDiscount()*100)/100);
                        }
                        break;
                    case BOOK:
                        if (categorytype[1]>=2){
                            discount.set(i,Math.floor(((Item)p).getPrice()*c.getCategory().getDiscount()*100)/100);
                        }
                        break;
                    case CLOTHES:
                        if (categorytype[2]>=2){
                            discount.set(i,Math.floor(((Item)p).getPrice()*c.getCategory().getDiscount()*100)/100);
                        }
                        break;
                    case STATIONERY:
                        if (categorytype[3]>=2){
                            discount.set(i,Math.floor(((Item)p).getPrice()*c.getCategory().getDiscount()*100)/100);
                        }
                        break;
                    case ELECTRONICS:
                        if (categorytype[4]>=2){
                            discount.set(i,Math.floor(((Item)p).getPrice()*c.getCategory().getDiscount()*100)/100);
                        }
                        break;
                }
            }
        }
    }
    public double getDiscount(int index){
        return discount.get(index);
    }
    public void add(Product p){
        productList.add(p);
    }

    public void setNumProductInTicket(int numProductInTicket) {
        NumProductInTicket = numProductInTicket;
    }

    /**
     * Remove ticket product
     * @param Id product id will remove
     */
    public void removeProduct(String Id) {
        int i = 0;
        while (i < NumProductInTicket) {
            if (productList.get(i).getID().equals(Id)) {
                productList.remove(productList.get(i));
                NumProductInTicket--;
            } else {
                i++;
            }
        }
        if (NumProductInTicket == 0){
            state = stateTicket.empty;
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
            if (productList.get(i).getProductType() !=ProductType.Service){
            totalPrice += ((Item)productList.get(i)).getPrice();
            }
        }
        totalPrice = Math.floor(totalPrice * 100) / 100;
        return totalPrice;
    }
    /**
     * Returns the total discount made in the whole product list
     * Getter TotalDiscount
     * @return TotalDiscount
     */
    public abstract double getTotalDiscount();


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
    public Product getProduct(int index){return productList.get(index);}
    /**
     * Getter for number of products in ticket
     * @return number of products
     */
    public int getNumProductInTicket(){return productList.size();}
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
    public abstract String toString() ;
}