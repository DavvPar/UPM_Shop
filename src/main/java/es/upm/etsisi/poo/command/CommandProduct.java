package es.upm.etsisi.poo.command;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
public class CommandProduct implements Command{
    private final Map<String,Function<String,Boolean>> commandRegistry = new HashMap<>();

    public CommandProduct(){
        LoadComand();
    }
    private void LoadComand(){
        commandRegistry.put("add",this::add);
        commandRegistry.put("remove",this::remove);
        commandRegistry.put("list",this::list);
        commandRegistry.put("update",this::update);
        commandRegistry.put("addmeeting",this::addmeeting);
        commandRegistry.put("addfood",this::addfood);
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
    private boolean addfood(String args){return false;}
    private boolean addmeeting(String args){return false;}
    private boolean remove(String args){
        return false;}
    private boolean list(String args){
        return false;
    }
    private boolean update(String args){
        return false;
    }
}
