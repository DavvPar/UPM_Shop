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
    private int MaxNumProduct;
    private Product[] productList;
    private int[] num;
    /**
     * Total price of all the products in the ticket.
     */
    private int totalPrice;
    private int numProductInTicket;

    /**
     * Constructor of the class
     * @param numProduct number of products on the ticket.
     */
    public Ticket(int numProduct) {
        numProductInTicket = 0;
        MaxNumProduct = numProduct;
        productList = new Product[MaxNumProduct];
        num = new int[MaxNumProduct];
        this.totalPrice = 0;
    }

    /**
     * Metodo agregar productos a ticket
     * @param product a añadir
     * @param cantidad a añadir
     * @return true o false (successful o false)
     */
    public boolean addProductToTicket(Product product, int cantidad){
        boolean existe = false,add = false;
        if (numProductInTicket<= 100){
            for (int i= 0;i<numProductInTicket;i++){
                if (product == productList[i]){
                    num[i]+=cantidad;
                    existe = true;
                    add = true;
                }
            }
            if (!existe){
                productList[numProductInTicket] = product;
                num[numProductInTicket] =cantidad;
                numProductInTicket++;
                add = true;
            }
        }
        return add;
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