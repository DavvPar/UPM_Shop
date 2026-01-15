package es.upm.etsisi.poo.MapDB;
import es.upm.etsisi.poo.Utils;
import es.upm.etsisi.poo.products.*;
import es.upm.etsisi.poo.ticket.*;
import es.upm.etsisi.poo.user.*;
import org.mapdb.*;
import java.util.*;

public class MapDBManager {
    private DB db;
    private UserList usercache ;
    private ProductList productcache;
    private TicketList<Ticket<Product>> ticketscache;
    private HTreeMap productMap;
    private HTreeMap userMap;
    private HTreeMap ticketMap;
    private static final String COL_USER = "userList";
    private static final String COL_PRODUCT = "productList";
    private static final String COL_TICKET = "ticketList";
    private static final String COL_COMMAND = "commandMap";
    public MapDBManager(){
        initDB();
        Load();
    }
    private void Load(){
        userMap = db
                .hashMap("user")
                .keySerializer(Serializer.STRING)
                .valueSerializer(Serializer.JAVA)
                .createOrOpen();
        LoadUserList(userMap);
        productMap = db
                .hashMap("product")
                .keySerializer(Serializer.STRING)
                .valueSerializer(Serializer.JAVA)
                .createOrOpen();
        LoadProductList(productMap);
        ticketMap = db
                .hashMap("ticket")
                .keySerializer(Serializer.STRING)
                .valueSerializer(Serializer.JAVA)
                .createOrOpen();
        LoadTicketList(ticketMap);
    }
    public void addUser(User user){
        userMap.put(user.getId(),user);
        db.commit();
    }
    public void addProduct(Product product){
        productMap.put(product.getID(),product);
        db.commit();
    }
    public void addTicket(Ticket ticket){
        String id =Utils.getShortId(ticket.getTicketId());
        ticketMap.put(id,ticket);
        db.commit();
    }
    public void removeUser(String id){
        userMap.remove(id);
        db.commit();
    }
    public void removeProduct(Product product){
        productMap.remove(product.getID());
        db.commit();
    }
    public void removeTicket(Ticket ticket){
        ticketMap.remove(Utils.getShortId(ticket.getTicketId()));
        db.commit();
    }
    private void LoadUserList(HTreeMap<String,User> Map){
        if (Map.isEmpty()) {
            usercache= new UserList();
        }
        else{
            for (User user : Map.values()) {
                if (user instanceof Cash){
                    usercache.addCash((Cash) user);
                }else usercache.addClient((Client) user);
            }
        }
    }
    private void LoadProductList(HTreeMap<String,Product> Map){
        if (Map.isEmpty()) {
            productcache = new ProductList(200);
        }
        else{
            for (Product product : Map.values()) {
             productcache.addProduct(product);
            }
        }
    }
    private void LoadTicketList(HTreeMap<String,Ticket> Map) {
        if (Map.isEmpty()) {
            ticketscache = new TicketList<>();
        }
        else{
            for (Ticket ticket : Map.values()) {
                ticketscache.addTicket(ticket);
            }
        }
    }
    public ProductList getProductoList() {
        return productcache;
    }
    public TicketList<Ticket<Product>> getTicketList() {
        return ticketscache;
    }
    public UserList getUserList(){
        return usercache;
    }
    private void initDB(){
        if (db == null || db.isClosed()) {
            db = DBMaker
                    .fileDB("System_date.db")
                    .closeOnJvmShutdown()
                    .transactionEnable()
                    .make();
        }
        ticketscache = new TicketList<>();
        productcache = new ProductList(200);
        usercache = new UserList();

    }
}
