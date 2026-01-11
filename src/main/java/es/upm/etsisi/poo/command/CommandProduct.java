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
}
