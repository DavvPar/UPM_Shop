package es.upm.etsisi.poo;

import java.util.List;

/**
 * Tickets is a class for managing the ticket creation
 * and deletion. It also works with the product list,
 * the prices, the dates and bills.
 */
public class Ticket {
    /**
     * Maximum number of products in a list
     */
    private int MaxNumProduct;
    /**
     * The list of products on the ticket.
     */
    private Product[] productList;
    /**
     * Discount for each individual product in the ticket.
     */
    private double[] discount ;

    /**
     * Total price of all the products in the ticket.
     */
    private double totalPrice;
    /**
     * Total discount applied to all the products in the ticket.
     */
    private double totalDiscount;
    /**
     * Number of products currently in the ticket.
     */
    private int NumProductInTicket;

    /**
     * Constructor of the class.
     * Creates a new empty ticket with a maximum number of products.
     *
     * @param MaxNumProduct Max number of products on the ticket
     */
    public Ticket(int MaxNumProduct) {
        this.NumProductInTicket = 0;
        this.MaxNumProduct = MaxNumProduct;
        this.productList = new Product[MaxNumProduct];
        this.totalPrice = 0;
        discount = new double[MaxNumProduct];
    }

    /**
     * Method for adding products to a ticket.
     *
     * @param Id ID of the new product added
     * @param quantity to be added
     * @return true or false (successful or failed)
     */
    public boolean addProductToTicket(ProductList lista,int Id, int quantity){
        boolean add = false;
        for (int i = 0; i < quantity; i++){
            if (NumProductInTicket <= 100){
                    productList[NumProductInTicket] = lista.getProduct(Id);
                    NumProductInTicket++;
                    add = true;
            }
            else {
                System.out.println("No further products can be added.");
            }
        }
        applyDiscunt();
        return add;
    }
    /**
     * Removes the product with the passed ID from the ticket.
     *
     * @param Id product ID from product that will be removed
     */
    public void removeProduct(int Id) {
        for(int i = 0; i < NumProductInTicket; i++) {
            if (productList[i].getID() == Id){
                for (int j = i+1; j<NumProductInTicket;j++){
                    productList[j-1] = productList[j];
                }
                NumProductInTicket--;
                productList[NumProductInTicket] = null;

            }

        }
        applyDiscunt();
    }

    /**
     * Checks if the discount applies for each category of product,
     * if it does, it applies it accordingly.
     *
     */
    public void applyDiscunt(){
        int[] categorytype = new int[5];
        for (int i = 0; i<NumProductInTicket; i++){
            switch (productList[i].getCategory().getType()){
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
        for (int i = 0; i<NumProductInTicket; i++){
            switch (productList[i].getCategory().getType()){
                case MERCH:
                    if (categorytype[0]>=2){
                        discount[i] = Math.floor(productList[i].getPrice() * productList[i].getCategory().getDiscount()*100)/100;
                    }
                    break;
                case BOOK:
                    if (categorytype[1]>=2){
                        discount[i] = Math.floor(productList[i].getPrice() * productList[i].getCategory().getDiscount()*100)/100;
                    }
                    break;
                case CLOTHES:
                    if (categorytype[2]>=2){
                        discount[i] = Math.floor(productList[i].getPrice() * productList[i].getCategory().getDiscount()*100)/100;
                    }
                    break;
                case STATIONERY:
                    if (categorytype[3]>=2){
                        discount[i] = Math.floor(productList[i].getPrice() * productList[i].getCategory().getDiscount()*100)/100;
                    }
                    break;
                case ELECTRONICS:
                    if (categorytype[4]>=2){
                        discount[i] = Math.floor(productList[i].getPrice() * productList[i].getCategory().getDiscount()*100)/100;
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Returns the total price of the whole product list.
     * Getter TotalPrice
     *
     * @return TotalPrice
     */
    public double getTotalPrice(){
        totalPrice = 0;
        for (int i = 0; i<NumProductInTicket; i++){
            totalPrice += productList[i].getPrice();
        }
        totalPrice = Math.floor(totalPrice * 100) / 100;
        return totalPrice;
    }

    /**
     * Returns the total amount discounted from all the products in the ticket.
     * Getter TotalDiscount
     *
     * @return total discount.
     */
    public double getTotalDiscount(){
        totalDiscount = 0 ;
        for (int i =0; i<NumProductInTicket; i++){
            totalDiscount += discount[i];
        }
        totalDiscount = Math.floor(totalDiscount * 100) / 100;
        return totalDiscount;
    }
    public double getFinalPrice(){
        return getTotalPrice() - getTotalDiscount();
    }

    /**
     * Ticket toString, showing all the products in the list, the
     * total price, total discount and final price with discount applied.
     *
     * @return Ticket
     */
    @Override
    public String toString() {
        String message = "";
        for (int i = 0; i < NumProductInTicket; i++){
            if (discount[i]>0) {
                message += productList[i].toString() + "**discount -" + discount[i] + "\n";
            }
            else{
                message += productList[i].toString() + "\n";
            }
        }
        return message +"Total price: "+getTotalPrice() +"\n"
                + "Total discount: "+ getTotalDiscount() +"\n"
                + "Final Price: " +getFinalPrice() ;

    }
}