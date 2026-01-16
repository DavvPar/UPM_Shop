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
    private HTreeMap<String, byte[]> productMap;
    private HTreeMap<String, byte[]> ticketMap;
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

        if (userMap.isEmpty()) {
            usercache = new UserList();
        } else {
            usercache = new UserList();
            for (User user : userMap.values()) {
                if (user instanceof Cash) {
                    usercache.addCash((Cash) user);
                } else {
                    usercache.addClient((Client) user);
                }
            }
        }
    }

    private void LoadProductList() {
        productMap = db.hashMap("product")
                .keySerializer(Serializer.STRING)
                .valueSerializer(Serializer.BYTE_ARRAY)
                .createOrOpen();

        if (productMap.isEmpty()) {
            productcache = new ProductList(200);
        } else {
            productcache = new ProductList(200);
            int success = 0;
            int failed = 0;

            for (Map.Entry<String, byte[]> entry : productMap.entrySet()) {
                try {
                    Product product = JacksonSerializer.deserialize(entry.getValue(), Product.class);
                    if (product != null) {
                        productcache.addProduct(product);
                        success++;
                        System.out.println("Cargado: " + entry.getKey());
                    } else {
                        failed++;
                    }
                } catch (Exception e) {
                    failed++;
                    System.err.println("Error producto " + entry.getKey() + ": " + e.getMessage());

                    byte[] data = entry.getValue();
                    if (data != null && data.length > 0) {
                        String preview = new String(data, 0, Math.min(50, data.length));
                        System.err.println("Datos: " + preview);
                    }
                }
            }

            System.out.println("Productos: " + success + " ok, " + failed + " fallados");
        }
    }

    private void LoadTicketList() {
        ticketMap = db.hashMap("ticket")
                .keySerializer(Serializer.STRING)
                .valueSerializer(Serializer.BYTE_ARRAY)
                .createOrOpen();

        if (ticketMap.isEmpty()) {
            ticketscache = new TicketList<>();
        } else {
            ticketscache = new TicketList<>();
            int success = 0;
            int failed = 0;

            for (Map.Entry<String, byte[]> entry : ticketMap.entrySet()) {
                try {
                    HashMap<String, Object> ticketData = JacksonSerializer.deserialize(entry.getValue(), HashMap.class);
                    if (ticketData != null) {
                        ticketscache.addTicket(ticketData);
                        success++;
                    } else {
                        failed++;
                    }
                } catch (Exception e) {
                    failed++;
                    System.err.println("Error ticket " + entry.getKey() + ": " + e.getMessage());
                }
            }

            System.out.println("Tickets: " + success + " ok, " + failed + " fallados");
        }
    }

    public void addUser(User user) {
        if (userMap.get(user.getId()) == null) {
            userMap.put(user.getId(), user);
            db.commit();
        }
    }

    public void addProduct(Product product) {
        byte[] serialized = JacksonSerializer.serialize(product);
        productMap.put(product.getID(), serialized);
        productcache.addProduct(product);
        db.commit();
    }

    public void addTicket(HashMap<String, Object> ticket) {
        String id = ((Ticket)ticket.get("ticket")).getTicketId();
        byte[] serialized = JacksonSerializer.serialize(ticket);
        ticketMap.put(id, serialized);
        ticketscache.addTicket(ticket);
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