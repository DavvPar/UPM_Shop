package es.upm.etsisi.poo.command;

import es.upm.etsisi.poo.controller.ClientController;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CommandClient implements Command{

    private final Map<String, Function<String,Boolean>> commandRegistry = new HashMap<>();

    private final ClientController controller;

    public CommandClient(ClientController controller){
        this.controller = controller;
        loadComand();
    }
    private void loadComand(){
        commandRegistry.put("add",controller::addClient);
        commandRegistry.put("remove",controller::removeClient);
        commandRegistry.put("list",args -> controller.listClient());
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
