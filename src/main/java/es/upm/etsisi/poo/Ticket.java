package es.upm.etsisi.poo;

import java.util.Arrays;

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
    private int[] discount;
    private int[] num;
    private Productlist product;

    /**
     * Total price of all the products in the ticket.
     */
    private int totalPrice;
    /**
     * Number of products currently in the ticket.
     */
    private int NumProductInTicket;

    /**
     * ticket class constructor
     * @param MaxNumProduct Max number of products on the ticket.
     */
    public Ticket(int MaxNumProduct) {
        NumProductInTicket = 0;
        this.MaxNumProduct = MaxNumProduct;
        productList = new Product[MaxNumProduct];
        num = new int[MaxNumProduct];
        this.totalPrice = 0;
    }
    /**
     * Method for adding products to a ticket
     * @param Id   Product id to be added
     * @param quantity to be added
     * @return true or false (successful or failed)
     */
    public boolean addProductToTicket(int Id, int quantity){
        boolean exists = false,add = false;
        if (NumProductInTicket <= 100){
            for (int i= 0; i < NumProductInTicket;i++){
                if ( product.getProduct(Id)== productList[i]){
                    num[i] += quantity;
                    exists = true;
                    add = true;
                }
            }
            if (!exists){
                productList[NumProductInTicket] = product.getProduct(Id);
                num[NumProductInTicket] =quantity;
                NumProductInTicket++;
                add = true;
            }
        }
        else {
            System.out.println("No further products can be added.");
        }
        return add;
    }

    /**
     * Remove ticket product
     * @param Id product id will remove
     */
    public void removeProduct(int Id) {
        for(int i =0;i<NumProductInTicket;i++) {
            if (productList[i].getID() == Id){
                for (int j = i+1; j<NumProductInTicket;j++){
                    productList[j-1] = productList[j];
                }
                NumProductInTicket--;
                productList[NumProductInTicket] = null;

            }

        }
    }

    /**
     * getter TotalPrice
     * @return TotalPrice
     */

    public double getTotalPrice(){
        for (int i =0; i < NumProductInTicket; i++){
            totalPrice += num[i]* productList[i].getPrice();
        }
        return totalPrice;
    }
    public double getTotaldiscunt(){
        return 1;
    }
    public double getFinalPrice(){
        return 1;
    }
    /**
     * toString
     * @return Ticket
     */
    @Override
    public String toString() {
        return "Ticket{" +
                "productList=" + Arrays.toString(productList) +"\n" +
                ", totalPrice=" + getTotalPrice() +
                '}';
    }
}