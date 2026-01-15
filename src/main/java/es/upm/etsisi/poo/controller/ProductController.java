package es.upm.etsisi.poo.controller;

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
import java.time.temporal.ChronoUnit;

public class ProductController {

    private final ProductList productList;
    private final EventProductValidator eventValidator;
    private final CustomProductValidator customValidator;
    private final ServiceValidator serviceValidator;

    public ProductController(ProductList productList) {
        this.productList = productList;
        this.eventValidator = new EventProductValidator();
        this.customValidator = new CustomProductValidator();
        this.serviceValidator = new ServiceValidator();
    }

    // java
    public boolean addProduct(String args) {
        try {
            String[] message = args.trim().split(" ");
            Product product = null;

            // Servicio (ej: "2025-12-20 STYPE")
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

                // Construir parámetros para el validador
                String[] params;
                if (rightParts.length == 3){
                    params = new String[]{id, categoryStr, priceStr, rightParts[2]};
                } else {
                    params = new String[]{id, categoryStr, priceStr};
                }

                // Validar con CustomProductValidator antes de parsear
                if (!customValidator.validate(params)) {
                    System.out.println("prod add: error");
                    return false;
                }

                // Parseos seguros tras validación
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
            System.out.println(ok ? "prod add: ok" : "prod add: error");
            return ok;

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Usage: `prod add <id> \"<name>\" <category> <price> [maxPers] || \"<name>\" <category>`");
            return false;
        }
    }

    public boolean listProducts(){
        boolean ok = productList.listProducts();
        System.out.println(ok ? "prod list: ok" : "prod list: error");
        return ok;
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

            if (rightParts.length != 3) {
                System.out.println(
                        "Usage: prod addmeeting <id> \"<name>\" <price> <expiration: yyyy-MM-dd> <max_people>"
                );
                return false;
            }

            String line = args;
            String name = Utils.getNameScanner(line);
            String id = message[0];

            double price = Double.parseDouble(rightParts[0]);
            String expirationStrg = rightParts[1];
            int maxPeople = Integer.parseInt(rightParts[2]);

            if (!Utils.validDate(expirationStrg)) return false;

            if (maxPeople > 100) {
                System.out.println("have exceeded the maximum number of people allowed(100)");
                return false;
            }

            if (!validatePlanningTime(ProductType.Meeting, expirationStrg)) {
                return false;
            }

            EventProduct eventProduct =
                    new EventProduct(id, name, price, expirationStrg, maxPeople, ProductType.Meeting);
            //TODO eventValidator.validate(eventProduct);
            boolean ok = productList.addProduct(eventProduct);
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

            if (rightParts.length != 3) {
                System.out.println(
                        "Usage: prod addfood <id> \"<name>\" <price> <expiration: yyyy-MM-dd> <max_people>"
                );
                return false;
            }

            String line = args;
            String name = Utils.getNameScanner(line);
            String id = message[0];

            double price = Double.parseDouble(rightParts[0]);
            String expirationStrg = rightParts[1];
            int maxPeople = Integer.parseInt(rightParts[2]);

            if (maxPeople > 100) {
                System.out.println("have exceeded the maximum number of people allowed(100)");
                return false;
            }

            if (!validatePlanningTime(ProductType.Food, expirationStrg)) {
                return false;
            }

            EventProduct eventProduct =
                    new EventProduct(id, name, price, expirationStrg, maxPeople, ProductType.Food);
            //TODO eventValidator.validate(eventProduct);
            boolean ok = productList.addProduct(eventProduct);
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

    //TODO CAMBIAR ESTO DE SITIO, PQ ES PUBLICO Y LO NECESITA TICKET
    //Si hacemos un PlanningTimeValidator??
    public boolean validatePlanningTime(ProductType typeProduct, String expirationDate) {
        //YYYY-MM-DD-HH:MM
        String[] currentTimeString = Utils.getTime().trim().split("[:-]");
        String[] time = expirationDate.trim().split("[:-]");
        LocalDateTime now =  LocalDateTime.of(Integer.parseInt(currentTimeString[0]),Integer.parseInt(currentTimeString[1]),
                Integer.parseInt(currentTimeString[2]),Integer.parseInt(currentTimeString[3]),Integer.parseInt(currentTimeString[4]));
        LocalDateTime Date = LocalDateTime.of(Integer.parseInt(time[0]),Integer.parseInt(time[1]),Integer.parseInt(time[2]),
                0,0);
        long HourD = ChronoUnit.HOURS.between(now,Date);
        boolean isValid = true;
        if (typeProduct == ProductType.Food) {
            if (HourD < 72) {
                System.out.println("Error adding product");
                isValid = false;
            }
        }else{
            if (HourD< 12){
                System.out.println("Error adding meeting");
                isValid=false;
            }
        }
        return isValid;
    }
}
