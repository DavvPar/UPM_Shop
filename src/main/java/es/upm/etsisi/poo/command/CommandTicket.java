package es.upm.etsisi.poo.command;

import es.upm.etsisi.poo.controller.TicketController;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CommandTicket implements Command{

    private final Map<String, Function<String,Boolean>> commandRegistry = new HashMap<>();

    private final TicketController controller;

    public CommandTicket(TicketController controller){
        this.controller = controller;
        loadComand();
    }
    private void loadComand(){
        commandRegistry.put("add",controller::addTicket);
        commandRegistry.put("remove",controller::removeTicket);
        commandRegistry.put("list",args -> controller.listTicket());
        commandRegistry.put("new",controller::newTicket);
        commandRegistry.put("print",controller::printTicket);
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
