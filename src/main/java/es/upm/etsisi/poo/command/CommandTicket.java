package es.upm.etsisi.poo.command;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CommandTicket implements Command{
    private final Map<String, Function<String,Boolean>> commandRegistry = new HashMap<>();

    public CommandTicket(){
        LoadComand();
    }
    private void LoadComand(){
        commandRegistry.put("add",this::add);
        commandRegistry.put("remove",this::remove);
        commandRegistry.put("list",this::list);
        commandRegistry.put("new",this::newTicket);
        commandRegistry.put("print",this::print);
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
    private boolean add(String args){
        return false;
    }
    private boolean print(String args){return false;}
    private boolean remove(String args){
        return false;}
    private boolean list(String args){
        return false;
    }
    private boolean newTicket(String args){
        return false;
    }
}
