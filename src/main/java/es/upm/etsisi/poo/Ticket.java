package es.upm.etsisi.poo;

import java.util.Arrays;

/**
 * Tickets is a class for managing the ticket creation
 * and deletion. It also works with the product list,
 * the prices, the dates and bills.
 */
public class Ticket {
    /**
     * The list of products on the ticket.
     */
    private Product[] productList;
    /**
     * Total price of all the products in the ticket.
     */
    private int totalPrice;

    /**
     * Constructor of the class
     * @param productList List of products on the ticket.
     */
    public Ticket(Product[] productList) {
        this.productList = productList;
        this.totalPrice = 0;
    }

    /**
     * toString
     * @return Ticket
     */
    @Override
    public String toString() {
        return "Ticket{" +
                "productList=" + Arrays.toString(productList) +"\n" +
                ", totalPrice=" + totalPrice +
                '}';
    }
}