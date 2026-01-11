package es.upm.etsisi.poo.command;
import es.upm.etsisi.poo.controller.ProductController;


import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CommandProduct implements Command{
    private final Map<String,Function<String,Boolean>> commandRegistry = new HashMap<>();
    private final ProductController controller;

    public CommandProduct(ProductController controller){
        this.controller = controller;
        loadComand();
    }
    private void loadComand(){
        commandRegistry.put("add",controller::addProduct);
        commandRegistry.put("remove",controller::removeProduct);
        commandRegistry.put("list",args -> controller.listProducts()); //lambda para que no necesite recibir un string
        commandRegistry.put("update",controller::updateProduct);
        commandRegistry.put("addmeeting",controller::addMeeting);
        commandRegistry.put("addfood",controller::addFood);
    }
    public boolean execute(String args) {
        String[] parts = args.split(" ");
        String commandName = parts[0].toLowerCase();
        Function<String,Boolean> function = commandRegistry.get(commandName);
        if (function != null) {
            String message = String.join(" ",java.util.Arrays.copyOfRange( parts,1, parts.length));
            return function.apply(message);
        }
        return false;
    }

    /*private boolean addProduct(String args){
        try {
            String[] message = args.split(" ");

            Product product;

            // Servicio
            if (message.length == 2) {
                String expireDate = message[0];
                ServiceType sType = ServiceType.valueOf(message[1].toUpperCase());
                product = new Service(
                        (productList.getNumservice() + 1) + "S",
                        sType,
                        expireDate,
                        ProductType.Service
                );
            }
            // Producto normal
            else {
                String line = args;
                String name = Utils.getNameScanner(line);
                String id = message[0];

                String[] rightParts = secondPartArray(line);
                CategoryType type = CategoryType.valueOf(rightParts[0].toUpperCase());
                Category category = new Category(type);
                double price = Double.parseDouble(rightParts[1]);

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
            System.out.println("Usage: prod add <id> \"<name>\" <category> <price> [maxPers]");
            return false;
        }
    }
    private boolean addfood(String args){
        try {
            String[] parts = args.split(" ");
            if (parts.length < 4) return false;

            String id = parts[0];
            String name = parts[1];
            CategoryType type = CategoryType.valueOf(parts[2].toUpperCase());
            double price = Double.parseDouble(parts[3]);

            CustomProduct food = new CustomProduct(id, name, price, new Category(type));
            return productList.addProduct(food);

        } catch (Exception e) {
            return false;
        }
    }

    private boolean addmeeting(String args){
        try {
            String[] parts = args.split(" ");
            if (parts.length < 1) return false;

            String id = parts[0];

            Product service = new Service(id);
            return productList.addProduct(service);

        } catch (Exception e) {
            return false;
        }
    }

    private boolean removeProduct(String args){
        Product p = productList.getProduct(args.trim());
        if (p == null) return false;
        return productList.removeProduct(p);
    }

    private boolean listProducts(String args){
        return productList.listProducts();
    }
    private boolean updateProduct(String args){
        try {
            String[] parts = args.split(" ");
            if (parts.length < 3) return false;

            String id = parts[0];
            String field = parts[1];
            String value = parts[2];

            return productList.updateProduct(id, field, value);

        } catch (Exception e) {
            return false;
        }
    }*/
}
