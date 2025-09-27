package es.upm.etsisi.poo;

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
    public Ticket(Product[] productList, int totalPrice) {
        this.productList = productList;
        this.totalPrice = totalPrice;
    }
}