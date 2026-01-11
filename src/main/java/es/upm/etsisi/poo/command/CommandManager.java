package es.upm.etsisi.poo.command;

import es.upm.etsisi.poo.controller.*;
import es.upm.etsisi.poo.products.ProductList;
import es.upm.etsisi.poo.ticket.TicketList;
import es.upm.etsisi.poo.user.UserList;

import java.util.HashMap;
import java.util.Map;

public class CommandManager implements Command{
    private final Map<String, Command> commandRegistry = new HashMap<>();

    private final ProductList productList;
    private final TicketList ticketList;
    private final UserList userList;
    private final ProductController productController;
    private final TicketController ticketController;
    private final ClientController clientController;
    private final CashController cashController;
    private final ExitController exitController;
    private final HelpController helpController;
    public CommandManager(ExitController exitController){
        productList = new ProductList(100);
        ticketList = new TicketList();
        userList = new UserList();

        productController = new ProductController(productList);
        ticketController = new TicketController(ticketList, productList, userList, productController);
        clientController = new ClientController(userList);
        cashController = new CashController(ticketList, userList);
        this.exitController = exitController;
        helpController = new HelpController();
        LoadComand();
    }
    private void LoadComand(){
        commandRegistry.put("prod",new CommandProduct(productController));
        commandRegistry.put("ticket",new CommandTicket(ticketController));
        commandRegistry.put("client",new CommandClient(clientController));
        commandRegistry.put("cash",new CommandCash(cashController));
    }
    public boolean execute(String args) {
            String[] parts = args.split(" ");
            String commandName = parts[0].toLowerCase();
            Command command = commandRegistry.get(commandName);
        if ("exit".equalsIgnoreCase(commandName)) {
            exitController.requestExit();
            return true;
        }
        if ("help".equalsIgnoreCase(commandName)) {
            helpController.help();
            return true;
        }
            if (command != null) {
                String message = String.join(" ",java.util.Arrays.copyOfRange( parts,1, parts.length));
                return command.execute(message);
            }
            return false;
    }
}
