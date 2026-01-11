package es.upm.etsisi.poo.command;

import es.upm.etsisi.poo.controller.ProductController;
import es.upm.etsisi.poo.controller.TicketController;
import es.upm.etsisi.poo.products.ProductList;
import es.upm.etsisi.poo.ticket.TicketList;

import java.util.HashMap;
import java.util.Map;

public class CommandManager implements Command{
    private final Map<String, Command> commandRegistry = new HashMap<>();

    private final ProductList productList;
    private final TicketList ticketList;
    //private final ClientList clientList;

    private final ProductController productController;
    private final TicketController ticketController;
    //private final ClientController clientController;

    public CommandManager(){
        productList = new ProductList(100);
        ticketList = new TicketList();
        //clientList = new ClientList();

        productController = new ProductController(productList);
        ticketController = new TicketController(ticketList, productList);
        //clientController = new ClientController(clientList);

        LoadComand();
    }
    private void LoadComand(){
        commandRegistry.put("prod",new CommandProduct(productController));
        commandRegistry.put("ticket",new CommandTicket(ticketController));
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
