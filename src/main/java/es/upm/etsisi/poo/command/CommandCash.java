package es.upm.etsisi.poo.command;

import es.upm.etsisi.poo.controller.CashController;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CommandCash implements Command{
    private final Map<String, Function<String,Boolean>> commandRegistry = new HashMap<>();
    private final CashController controller;

    public CommandCash(CashController controller){
        this.controller = controller;
        loadComand();
    }
    private void loadComand(){
        commandRegistry.put("add",controller::addCash);
        commandRegistry.put("remove",controller::removeCash);
        commandRegistry.put("list",args -> controller.listCash());
        commandRegistry.put("tickets",controller::ticketsCash);
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
