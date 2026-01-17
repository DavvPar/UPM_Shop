package es.upm.etsisi.poo.controller;

import es.upm.etsisi.poo.MapDB.MapDBManager;
import es.upm.etsisi.poo.Utils;
import es.upm.etsisi.poo.enums.Category;
import es.upm.etsisi.poo.enums.CategoryType;
import es.upm.etsisi.poo.enums.ProductType;
import es.upm.etsisi.poo.enums.ServiceType;
import es.upm.etsisi.poo.products.*;
import es.upm.etsisi.poo.validation.EventProductValidator;
import es.upm.etsisi.poo.validation.CustomProductValidator;
import es.upm.etsisi.poo.validation.ServiceValidator;

import java.time.LocalDateTime;
//import java.time.temporal.ChronoUnit;

public class ProductController extends Controller{

    private final ProductList productList;
    private final EventProductValidator eventValidator;
    private final CustomProductValidator customValidator;
    private final ServiceValidator serviceValidator;

    public ProductController(MapDBManager mapDBManager) {
        super(mapDBManager);
        this.productList = mapDBManager.getProductoList();
        this.eventValidator = new EventProductValidator();
        this.customValidator = new CustomProductValidator();
        this.serviceValidator = new ServiceValidator();
    }

    public boolean addProduct(String args) {
        try {
            String[] message = args.trim().split(" ");
            Product product = null;

            //Servicio
            if (message.length == 2) {
                if (!serviceValidator.validate(message)) {
                    System.out.println("prod add: error");
                    return false;
                }
                String numService = ((productList.getNumservice() + 1) + "S");
                ServiceType serviceType = ServiceType.valueOf(message[1].toUpperCase());
                String expireDate = message[0];
                product = new Service(numService, serviceType, expireDate, ProductType.Service);
            }
            // Producto (con o sin maxPers)
            else {
                String name = Utils.getNameScanner(args);
                String[] rightParts = Utils.secondPartArray(args);

                if (rightParts.length < 2) {
                    System.out.println("prod add: error");
                    return false;
                }

                String id = message[0];
                String categoryStr = rightParts[0];
                String priceStr = rightParts[1];

                String[] params;
                if (rightParts.length == 3){
                    params = new String[]{id, categoryStr, priceStr, rightParts[2], name};
                } else {
                    params = new String[]{id, categoryStr, priceStr, name};
                }

                if (!customValidator.validate(params)) {
                    System.out.println("prod add: error");
                    return false;
                }

                double price = Double.parseDouble(priceStr);
                Category category = new Category(CategoryType.valueOf(categoryStr.toUpperCase()));

                if (rightParts.length == 3) {
                    int maxPers = Integer.parseInt(rightParts[2]);
                    product = new CustomProduct(id, name, category, price, maxPers, ProductType.ProductPersonalized);
                } else {
                    product = new CustomProduct(id, name, category, price, 0, ProductType.Product);
                }
            }

            boolean ok = productList.addProduct(product);
            if (ok) mapDBManager.addProduct(product);
            System.out.println(ok ? "prod add: ok" : "prod add: error");
            return ok;

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Usage: prod add <id> \"<name>\" <category> <price> [maxPers] || \"<name>\" <category>");
            return false;
        }
    }

    public boolean listProducts(){
        if(productList.listProducts()){
            System.out.println("prod list: ok");
            return true;
        } else {
            System.out.println("prod list: error");
            return false;
        }
    }

    public boolean updateProduct(String args){
        try {
            String[] message = args.split(" ");
            if (message.length < 3) {
                System.out.println("Usage: prod update <id> <field> <value>");
                return false;
            }

            String idToUpdate = message[0];
            String field = message[1].toLowerCase();
            String value;

            if (field.equalsIgnoreCase("name")) {
                value = Utils.getNameScanner(args);
            } else {
                value = message[message.length - 1];
            }

            if (!validField(field)) {
                System.out.println("Invalid field");
                return false;
            }

            boolean ok = productList.updateProduct(idToUpdate, field, value);

            if (ok) {
                Product updated = productList.getProduct(idToUpdate);
                System.out.println(updated);
                System.out.println("prod update: ok");
            } else {
                System.out.println("prod update: error");
            }

            return ok;

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    //TODO METERLO EN VALIDATEPRODUCT
    private boolean validField(String field) {
        String[] allowedFields = {"name", "category", "price"};
        boolean validField = false;
        for (String f : allowedFields) {
            if (f.equals(field)) {
                validField = true;
                break;
            }
        }

        if (!validField) {
            System.out.println("Error: invalid field. The allowed fields are: name, category, price");
        }
        return validField;
    }

    public boolean removeProduct(String args) {
        try {
            String idRemove = args.trim();
            Product productRemove = productList.getProduct(idRemove);

            if (productRemove == null) {
                System.out.println("prod remove: error");
                return false;
            }

            System.out.println(productRemove);

            boolean ok = productList.removeProduct(productRemove);
            if (ok) mapDBManager.removeProduct(productRemove);
            System.out.println(ok ? "prod remove: ok" : "prod remove: error");
            return ok;

        } catch (Exception e) {
            System.out.println("prod remove: error");
            return false;
        }
    }

    public boolean addMeeting(String args) {
        try {
            String[] message = args.split(" ");
            String[] rightParts = Utils.secondPartArray(args);

            /*if (rightParts.length != 3) {
                System.out.println(
                        "Usage: prod addmeeting <id> \"<name>\" <price> <expiration: yyyy-MM-dd> <max_people>"
                );
                return false;
            }*/

            String line = args;
            String name = Utils.getNameScanner(line);
            String id = message[0];

            String priceStr = rightParts[0];
            String expirationStrg = rightParts[1];
            String maxPeopleStr = rightParts[2];

            String[] params = new String[]{message[0], name, priceStr, expirationStrg, maxPeopleStr};

            if(!eventValidator.validate(params)){
                System.out.println("prod add : error");
                return false;
            }

            double price = Double.parseDouble(rightParts[0]);
            int maxPeople = Integer.parseInt(rightParts[2]);


            EventProduct eventProduct =
                    new EventProduct(id, name, price, expirationStrg, maxPeople, ProductType.Meeting);

            boolean ok = productList.addProduct(eventProduct);
            if (ok) mapDBManager.addProduct(eventProduct);
            System.out.println(ok ? eventProduct : "prod add: error");
            if (ok) System.out.println("prod add: ok");

            return ok;

        } catch (Exception e) {
            System.out.println(
                    "Error: wrong format. Use prod addmeeting <id> \"<name>\" <price> <expiration: yyyy-MM-dd> <max_people>"
            );
            return false;
        }
    }

    public boolean addFood(String args) {
        try {
            String[] message = args.split(" ");
            String[] rightParts = Utils.secondPartArray(args);

            /*if (rightParts.length != 3) {
                System.out.println(
                        "Usage: prod addfood <id> \"<name>\" <price> <expiration: yyyy-MM-dd> <max_people>"
                );
                return false;
            }*/
            String line = args;
            String name = Utils.getNameScanner(line);
            String id = message[0];

            String priceStr = rightParts[0];
            String expirationStrg = rightParts[1];
            String maxPeopleStr = rightParts[2];

            String[] params = new String[]{message[0], name, priceStr, expirationStrg, maxPeopleStr};

            if(!eventValidator.validate(params)){
                System.out.println("prod add : error");
                return false;
            }

            double price = Double.parseDouble(rightParts[0]);
            int maxPeople = Integer.parseInt(rightParts[2]);

            EventProduct eventProduct =
                    new EventProduct(id, name, price, expirationStrg, maxPeople, ProductType.Food);
            boolean ok = productList.addProduct(eventProduct);
            if (ok) mapDBManager.addProduct(eventProduct);
            System.out.println(ok ? eventProduct : "prod add: error");
            if (ok) System.out.println("prod add: ok");

            return ok;

        } catch (Exception e) {
            System.out.println(
                    "Error: wrong format. Use prod addfood <id> \"<name>\" <price> <expiration: yyyy-MM-dd> <max_people>"
            );
            return false;
        }
    }
}
