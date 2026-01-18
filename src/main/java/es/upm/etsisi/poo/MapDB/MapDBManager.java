package es.upm.etsisi.poo.MapDB;

import es.upm.etsisi.poo.Utils;
import es.upm.etsisi.poo.products.*;
import es.upm.etsisi.poo.ticket.*;
import es.upm.etsisi.poo.user.*;
import org.mapdb.*;
import java.io.*;
import java.util.*;

public class MapDBManager {
    private DB db;
    private UserList usercache;
    private ProductList productcache;
    private TicketList<Ticket<Product>> ticketscache;
    private HTreeMap<String, Product> productMap;
    private HTreeMap<String, HashMap<String,Object>> ticketMap;
    private HTreeMap<String, User> userMap;
    public MapDBManager() {
        initDB();
        Load();
    }

    private void initDB() {
        if (db == null || db.isClosed()) {
            db = DBMaker
                    .fileDB("UPM_SHOP.db")
                    .closeOnJvmShutdown()
                    .transactionEnable()
                    .make();
        }
    }
    private void Load() {
        LoadUserList();
        LoadProductList();
        LoadTicketList();
    }

    private void LoadUserList() {
        userMap = db.hashMap("user")
                .keySerializer(Serializer.STRING)
                .valueSerializer(Serializer.JAVA)
                .createOrOpen();
        usercache = new UserList();
        List<Cash> cashUsers = new ArrayList<>();
        List<Client> clientUsers = new ArrayList<>();
        for (User user : userMap.values()) {
            if (user instanceof Cash) {
                cashUsers.add((Cash) user);
            } else if (user instanceof Client) {
                clientUsers.add((Client) user);
            }
        }
        for (Cash cash : cashUsers) {
            usercache.addCash(cash);
        }
        for (Client client : clientUsers) {
            usercache.addClient(client);
        }
    }
    private void LoadProductList() {
        productMap = db.hashMap("product")
                .keySerializer(Serializer.STRING)
                .valueSerializer(Serializer.JAVA)
                .createOrOpen();

            productcache = new ProductList(200);
            if (!productMap.isEmpty()){
                for (Product product : productMap.values()){
                    productcache.addProduct(product);
                }
            }
    }

    private void LoadTicketList() {
        ticketMap = db.hashMap("ticket")
                .keySerializer(Serializer.STRING)
                .valueSerializer(Serializer.JAVA)
                .createOrOpen();
        ticketscache = new TicketList<>();
        if (!ticketMap.isEmpty()){
            for (HashMap<String,Object> ticket : ticketMap.values()){
                ticketscache.addTicket(ticket);
            }
        }
    }

    public void addTicket(HashMap<String, Object> ticket) {
            String ticketId = ((Ticket)ticket.get("ticket")).getTicketId();
            ticketMap.put(ticketId,ticket);
            db.commit();
    }

    public void addUser(User user) {
        if (userMap.get(user.getId()) == null) {
            userMap.put(user.getId(), user);
            db.commit();
        }
    }

    public void addProduct(Product product) {
        productMap.put(product.getID(),product);
        db.commit();
    }

    public void removeUser(String id) {
        userMap.remove(id);
        db.commit();
    }

    public void removeProduct(Product product) {
        productMap.remove(product.getID());
        db.commit();
    }

    public void removeTicket(Ticket ticket) {
        ticketMap.remove(Utils.getShortId(ticket.getTicketId()));
        db.commit();
    }

    public ProductList getProductoList() {
        return productcache;
    }

    public TicketList<Ticket<Product>> getTicketList() {
        return ticketscache;
    }

    public UserList getUserList() {
        return usercache;
    }

    public void close() {
        if (db != null && !db.isClosed()) {
            db.close();
        }
    }
}