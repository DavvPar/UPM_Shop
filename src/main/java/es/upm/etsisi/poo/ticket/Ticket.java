package es.upm.etsisi.poo.ticket;
import es.upm.etsisi.poo.products.*;
import es.upm.etsisi.poo.enums.stateTicket;
import es.upm.etsisi.poo.enums.TicketType;
import es.upm.etsisi.poo.enums.ProductType;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Tickets is a class for managing the ticket creation and deletion. It also works with
 * the product list, the prices, the dates and bills.
 */


public abstract class Ticket <P extends Product>{
    /**
     * Maximum number of products in a list
     */
    public final int MaxNumProduct;
    /**
     * The list of products on the ticket.
     */
    private ArrayList<P> productList;
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
    private  String ticketId;
    /**
     *number of product types
     * [0] MERCH,
     * [1]STATIONERY,
     * [2]CLOTHES,
     * [3]BOOK,
     * [4]ELECTRONICS
     * [5]Service
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
        this.productList = new ArrayList<>();
        this.ticketId = idTicket;
        this.MaxNumProduct = 100;
        this.totalPrice = 0;
        this.state = state;
        this.type =type;
        discount = new ArrayList<>();
        categorytype= new int[6];
    }

    public TicketType getType(){return type;}

    /**
     * * Method for adding products to a ticket
     * * @return true or false (successful or failed)
     *
     */
    public abstract boolean addProductToTicket(ProductList lista,String Id, int quantity, String message);
    public boolean add(Product p,int quantity,String message){
        boolean add = false;
        if (p.getProductType() == ProductType.ProductPersonalized && !message.isEmpty()){
            ((CustomProduct)p).addPersonalized(message);
        }
        for (int i =0;i<quantity;i++){
            if (getNumProductInTicket() < 100){
                    productList.add((P)p);
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
    /**
     * private method to perform the necessary counting
     *number of types/product categories affecting the discount
     * [0] MERCH,
     * [1]STATIONERY,
     * [2]CLOTHES,
     * [3]BOOK,
     * [4]ELECTRONICS
     * [5]Service
     */
    private void setupDiscount(){
        discount = new ArrayList<>();
        categorytype = new int[6];
        for (int i =0;i<productList.size();i++){
            Product p =productList.get(i);
            if (p.getProductType() == ProductType.Service){
                categorytype[5]++;
            }
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
        setupDiscount();
        for(int i =0; i<productList.size();i++){
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
                        if (categorytype[3]>=2){
                            discount.set(i,Math.floor(((Item)p).getPrice()*c.getCategory().getDiscount()*100)/100);
                        }
                        break;
                    case CLOTHES:
                        if (categorytype[2]>=2){
                            discount.set(i,Math.floor(((Item)p).getPrice()*c.getCategory().getDiscount()*100)/100);
                        }
                        break;
                    case STATIONERY:
                        if (categorytype[1]>=2){
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
    public int NumService(){
        return categorytype[5];
    }


    /**
     * Remove ticket product
     * @param Id product id will remove
     */
    public void removeProduct(String Id) {
        int i = 0;
        while (i < productList.size()) {
            if (productList.get(i).getID().equals(Id)) {
                productList.remove(productList.get(i));
            } else {
                i++;
            }
        }
        if (productList.size() == 0){
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
        for (int i =0; i < productList.size(); i++){
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
    public void Sort(){
        int servicesCount = 0;
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getProductType() == ProductType.Service) {
                Product temp = productList.get(i);
                productList.set(i, productList.get(servicesCount));
                productList.set(servicesCount, (P)temp);
                servicesCount++;
            }
        }
        if (servicesCount > 1) {
            productList.subList(0, servicesCount)
                    .sort(Comparator.comparing(
                            p -> p.getID(),
                            String.CASE_INSENSITIVE_ORDER
                    ));
        }
        if (servicesCount < (productList.size() - 1)) {
            productList.subList(servicesCount, productList.size())
                    .sort(Comparator.comparing(
                            p ->((Item)p).getName(),
                            String.CASE_INSENSITIVE_ORDER
                    ));
        }
    }
    /**
     * Ticket toString, showing all the
     * @return Ticket
     */
    @Override
    public abstract String toString() ;
}