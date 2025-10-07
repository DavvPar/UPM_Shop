package es.upm.etsisi.poo;

import java.util.Arrays;
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
    private double[] discount ;

    public final List<Integer> DISCOUNT = List.of(0,5,7,10,3);
    private ProductList product;
    /**
     * Total price of all the products in the ticket.
     */
    private double totalPrice;
    private double totaldiscount;
    /**
     * Number of products currently in the ticket.
     */
    private int NumProductInTicket;

    /**
     * ticket class constructor
     * @param MaxNumProduct Max number of products on the ticket.
     */
    public Ticket(int MaxNumProduct) {
        this.NumProductInTicket = 0;
        this.MaxNumProduct = MaxNumProduct;
        this.product = new ProductList(MaxNumProduct);
        this.totalPrice = 0;
        discount = new double[MaxNumProduct];
    }

    /**
     * Method for adding products to a ticket
     * @param Id   Product id to be added
     * @param quantity to be added
     * @return true or false (successful or failed)
     */
    public boolean addProductToTicket(int Id, int quantity){
        boolean add = false;
        for (int i =0;i<quantity;i++){
            if (NumProductInTicket <= 100){
                    productList[NumProductInTicket] = product.getProduct(Id);
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
        applyDiscunt();
    }


    public void applyDiscunt(){
        int[] categorytype = new int[5];
        for (int i =0;i<productList.length;i++){
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
        for (int i =0;i<productList.length;i++){
            switch (productList[i].getCategory().getType()){
                case MERCH:
                    if (categorytype[0]>=2){
                        discount[i] = productList[i].getPrice()*productList[i].getCategory().getDiscount();
                    }
                    break;
                case BOOK:
                    if (categorytype[1]>=2){
                        discount[i] = productList[i].getPrice()*productList[i].getCategory().getDiscount();
                    }
                    break;
                case CLOTHES:
                    if (categorytype[2]>=2){
                        discount[i] = productList[i].getPrice()*productList[i].getCategory().getDiscount();
                    }
                    break;
                case STATIONERY:
                    if (categorytype[3]>=2){
                        discount[i] = productList[i].getPrice()*productList[i].getCategory().getDiscount();
                    }
                    break;
                case ELECTRONICS:
                    if (categorytype[4]>=2){
                        discount[i] = productList[i].getPrice()*productList[i].getCategory().getDiscount();
                    }
                    break;
                default:
                    break;
            }
        }
    }
    /**
     * Returns the total price of the whole product list.
     * getter TotalPrice
     * @return TotalPrice
     */
    public double getTotalPrice(){
        for (int i =0; i < NumProductInTicket; i++){
            totalPrice += productList[i].getPrice();
        }
        return totalPrice;
    }
    public double getTotaldiscunt(){
        for (int i =0; i < NumProductInTicket; i++){
            totaldiscount += discount[i];
        }
        return totaldiscount;
    }
    public double getFinalPrice(){
        return totalPrice - totaldiscount;
    }

    /**
     * Ticket toString, showing all the
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