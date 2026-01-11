package es.upm.etsisi.poo.command;

import java.util.HashMap;
import java.util.Map;

public class ComandManager implements Command{
    private final Map<String, Command> commandRegistry = new HashMap<>();

    public ComandManager(){

    }
    private void LoadComand(){

    }
    public boolean execute(String args) {
            String[] parts = args.split(" ");
            String commandName = parts[0].toLowerCase();
            Command command = commandRegistry.get(commandName);
            if (command != null) {
                String message = String.join(" ",java.util.Arrays.copyOfRange( parts,1, parts.length));
                return command.execute(message);
            }
            return false;
    }
}
