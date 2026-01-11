package es.upm.etsisi.poo.command;

import java.util.HashMap;
import java.util.Map;

public class CommandClient implements Command{
    private final Map<String, Command> commandRegistry = new HashMap<>();

    public CommandClient(){
        LoadComand();
    }
    private void LoadComand(){
        commandRegistry.put("prod",new CommandProduct());
        commandRegistry.put("ticket",new CommandTicket());
        commandRegistry.put("client",new CommandClient());
        commandRegistry.put("cash",new CommandCash());
        commandRegistry.put("help",new CommandHelp());
        commandRegistry.put("exit",new CommandExit());
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
