package es.upm.etsisi.poo.command;

import es.upm.etsisi.poo.MapDB.MapDBManager;
import es.upm.etsisi.poo.Utils;
import es.upm.etsisi.poo.controller.*;
import es.upm.etsisi.poo.products.ProductList;
import es.upm.etsisi.poo.ticket.TicketList;
import es.upm.etsisi.poo.user.UserList;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandManager implements Command{

    private final Map<String, Command> commandRegistry = new HashMap<>();

    private final ProductController productController;

    private final TicketController ticketController;

    private final ClientController clientController;

    private final CashController cashController;

    private final ExitController exitController;

    private final HelpController helpController;

    private final MapDBManager MapDb;
    public CommandManager(ExitController exitController){
        Scanner scanner = new Scanner(System.in);
        String filename  = Utils.leerCadena(scanner,"Enter the name of the db file to use if you do not know it, press Enter:");
        MapDb = new MapDBManager(filename);
        productController = new ProductController(MapDb);
        ticketController = new TicketController(MapDb);
        clientController = new ClientController(MapDb);
        cashController = new CashController(MapDb);
        this.exitController = exitController;
        helpController = new HelpController();
        loadComand();
    }

    private void loadComand(){
        commandRegistry.put("prod",new CommandProduct(productController));
        commandRegistry.put("ticket",new CommandTicket(ticketController));
        commandRegistry.put("client",new CommandClient(clientController));
        commandRegistry.put("cash",new CommandCash(cashController));
    }
    public boolean execute(String args) {
            String normalizedArgs = args.trim().replaceAll("\\s+", " ");
            String[] parts = normalizedArgs.split(" ");
            String commandName = parts[0].toLowerCase();
            Command command = commandRegistry.get(commandName);
            if(!Utils.ValidInput(commandName)){
                System.out.println("Command not recognized, please go to help for more assistance.");
                return false;
            }
        if ("exit".equalsIgnoreCase(commandName)) {
            exitController.requestExit();
            return true;
        }
        if ("help".equalsIgnoreCase(commandName)) {
            helpController.help();
            return true;
        }
        if ("echo".equalsIgnoreCase(commandName)) {
            System.out.println(args);
            return true;
        }
            if (command != null) {
                String message = String.join(" ",java.util.Arrays.copyOfRange( parts,1, parts.length));
                return command.execute(message);
            }
            return false;
    }
}
