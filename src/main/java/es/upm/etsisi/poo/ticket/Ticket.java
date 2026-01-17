package es.upm.etsisi.poo.ticket;
import com.fasterxml.jackson.annotation.JsonIgnore;
import es.upm.etsisi.poo.MapDB.MapDBManager;
import es.upm.etsisi.poo.products.*;
import es.upm.etsisi.poo.enums.stateTicket;
import es.upm.etsisi.poo.enums.TicketType;
import es.upm.etsisi.poo.enums.ProductType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public abstract class Ticket <P extends Product>implements Serializable {

    public final int MaxNumProduct;

    private ArrayList<P> productList;

    private ArrayList<Double> discount;

    private double totalPrice;

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

    private stateTicket state;
    private TicketType type;
    public Ticket() {
        this.MaxNumProduct = 100;
        this.productList = new ArrayList<>();
        this.ticketId = "";
        this.totalPrice = 0.0;
        this.state = stateTicket.empty;
        this.type = null;
        this.discount = new ArrayList<>();
        this.categorytype = new int[6];
    }
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
    public abstract boolean addProductToTicket(ProductList lista, String Id, int quantity, String message);
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

    public abstract double getTotalDiscount();

    public Product getProduct(int index){return productList.get(index);}

    public int getNumProductInTicket(){return productList.size();}

    public double getFinalPrice(){
        return getTotalPrice() - getTotalDiscount();
    }

    public String getTicketId(){return ticketId;}

    public void setTicketId(String id){ticketId = id;}

    public stateTicket getState (){return state;}

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
                            Product::getID,
                            String.CASE_INSENSITIVE_ORDER
                    ));
        }
        if (servicesCount < (productList.size() - 1)) {
            productList.subList(servicesCount, productList.size())
                    .sort(Comparator.comparing(
                            p ->Integer.parseInt(((Item)p).getName()),
                            Integer::compare
                    ));
        }
    }

    @Override
    public abstract String toString() ;
}