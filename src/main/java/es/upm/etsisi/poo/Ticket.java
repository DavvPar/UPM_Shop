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
    private int[] num;
    /**
     * Total price of all the products in the ticket.
     */
    private int totalPrice;
    /**
     * Number of products currently in the ticket.
     */
    private int numProductInTicket;

    /**
     * ticket class constructor
     * @param MaxNumProduct Max number of products on the ticket.
     */
    public Ticket(int MaxNumProduct) {
        numProductInTicket = 0;
        this.MaxNumProduct = MaxNumProduct;
        productList = new Product[MaxNumProduct];
        num = new int[MaxNumProduct];
        this.totalPrice = 0;
    }
    /**
     * Method for adding products to a ticket
     * @param product  to be added
     * @param quantity to be added
     * @return true or false (successful or failed)
     */
    public boolean addProductToTicket(Product product, int quantity){
        boolean exists = false,add = false;
        if (numProductInTicket <= 100){
            for (int i= 0; i < numProductInTicket;i++){
                if (product == productList[i]){
                    num[i] += quantity;
                    exists = true;
                    add = true;
                }
            }
            if (!exists){
                productList[numProductInTicket] = product;
                num[numProductInTicket] =quantity;
                numProductInTicket++;
                add = true;
            }
        }
        else {
            System.out.println("No further products can be added.");
        }
        return add;
    }

    /**
     * getter TotalPrice
     * @return TotalPrice
     */

    public int getTotalPrice(){
        for (int i =0; i < numProductInTicket; i++){
            totalPrice += num[i]* productList[i].getPrice();
        }
        return totalPrice;
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