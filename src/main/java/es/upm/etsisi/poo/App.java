package es.upm.etsisi.poo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

/**
 * App is the main class where the program is executed. It uses the methods from other classes
 * and executes a Command Line Interface for the user to interact with and introduce commands
 * to operate with the data
 */
public class App {
    /**
     * Declares all the possible categories on the program with
     * their discount included
     */
    static Scanner sc = new Scanner(System.in);
    /**
     * Maximum number of products that the product list can have
     */
    static int MaxNumProduct = 200;
    /**
     * Maximum number of products that the ticket can have
     */
    Utils utils = new Utils();
    /**
     * Current ticket being used
     */
    static Ticket currentTicket;
    /**
     * Current ist of products
     */
    static ProductList productlist = new ProductList(MaxNumProduct);
    /**
     * Current list of users
     */
    static UserList userList = new UserList();
    /**
     * Current list of tickets
     */
    static TicketList ticketList = new TicketList();

    /**
     * Main structure for executing the app
     * @param args arguments
     */
    public static void main(String[] args) {
        boolean readingFromFile = false;
        try {
            if (args.length >= 1) {
                readingFromFile = true;
                String file_name = args[0];
                sc = new Scanner(new FileReader(file_name));
            } else {
                sc = new Scanner(System.in);
            }
        } catch (FileNotFoundException e) {
            sc = new Scanner(System.in);
            throw new RuntimeException(e);
        }
        App application = new App();
        application.init();
        application.run(sc, readingFromFile);
    }

    /**
     * This method is the core of the main program and
     * determines how the application runs, it executes
     * the introduced commands
     */
    private void run(Scanner scanner, boolean readingFromFile) {
        boolean cont = true;
        while (cont) {
            System.out.print("\ntUPM> ");
            String line = scanner.nextLine();
            if (readingFromFile){
                System.out.println(line);
            }
            String[] lineSepSpace = line.split(" ");

            switch (lineSepSpace[0].toLowerCase()) {
                case "prod":
                    optionsOfProd(lineSepSpace);
                    break;
                case "client":
                    optionsClient(lineSepSpace);
                    break;
                case "cash":
                    optionsCash(lineSepSpace);
                    break;
                case "ticket":
                    optionsOfTicket(lineSepSpace);
                    break;
                case "echo":
                    String[] echoSepSpace = line.split("\"");
                    System.out.println("\"" + echoSepSpace[1] + "\"");

                    break;
                case "help":
                    helpCommand();
                    break;
                case "exit":
                    exitProgram();
                    cont = false;
                    break;
                default:
                    unknownCommand();
                    break;
            }
        }
        sc.close();
    }

    /**
     * Switch to operate all the possible commands for a product
     * and its list, including add, list, update and remove, calling
     * for methods in the Product and ProductList to implement the commands.
     *
     * @param message array of Strings with parameters of the command
     */
    private void optionsOfProd(String[] message) {
        if (message.length < 2) {
            System.out.println("Usage: prod with add, list, update or remove");
            return;
        }

        String command = message[1].toLowerCase();
        String input = String.join(" ", message);
        String[] rightParts = secondPartArray(input);
        switch (command) {
            case "add":
                if (!input.toLowerCase().startsWith("prod add")) {
                    System.out.println("Usage: prod add <id> \"<name>\" <category> <price> [maxPers]");
                    return;
                }
                try {
                    //line: prod add id \name con espacios\ category price
                    String line = String.join(" ", message);
                    String name = utils.getNameScanner(line); //cambio en get nombre de scanner
                    int id = Integer.parseInt(message[2]);

                    CategoryType type = CategoryType.valueOf(rightParts[0].toUpperCase());
                    Category category = new Category(type);
                    double price = Double.parseDouble(rightParts[1]);
                    Product product;
                    if (rightParts.length == 3) {
                        int maxPers = Integer.parseInt(message[message.length-1]);
                        product = new CustomProduct(id, name, category, price, maxPers);
                    } else {
                        product = new CustomProduct(id, name, category, price,-1);
                    }
                    if (productlist.addProduct(product)) {
                        String addProduct = product.toString();
                        System.out.println(addProduct);
                        System.out.println("prod add: ok");
                    } else {
                        System.out.println("prod add: error");
                    }
                } catch (Exception e) {
                    //System.out.println("Error: wrong format. Use prod add <id> \"<name>\" <category> <price>");
                    System.out.println("Error: " + e.getMessage());
                    System.out.println("Usage: prod add <id> \"<name>\" <category> <price> [<maxPers>]");
                }
                break;

            case "list":
                if (productlist.listProducts()) {
                    System.out.println("prod list: ok");
                } else {
                    System.out.println("prod list: error");
                }
                break;

            case "update":
                if (message.length < 5) {
                    System.out.println("Usage: prod update <id> <field> <value>");
                    return;
                }

                int idToUpdate = Integer.parseInt(message[2]);
                String field = message[3].toLowerCase();
                String value = "";
                if (field.equalsIgnoreCase("name")) {
                    value = Utils.getNameScanner(String.join(" ", message));
                }
                else{
                    value = message[message.length-1];
                }
                if (validField(field)) {
                    if (productlist.updateProduct(idToUpdate, field, value)) {
                        Product productUpdate = productlist.getProduct(idToUpdate);
                        String stringUpdate = productUpdate.toString();
                        System.out.println(stringUpdate);
                        System.out.println("prod update: ok");
                    } else {
                        System.out.println("prod update: error");
                    }
                }

                break;

            case "remove":
                int idRemove = Integer.parseInt(message[2]);
                Product productRemove = productlist.getProduct(idRemove);
                String stringProd = productRemove.toString();
                System.out.println(stringProd);
                if (productlist.removeProduct(productRemove)) {
                    System.out.println("prod remove: ok");
                } else {
                    System.out.println("prod remove: error");
                }
                break;

            case "addmeeting":
                if (rightParts.length != 3) {
                    System.out.println("Usage: prod "+command+" <id> <name> <price> <expiration: yyyy-MM-dd> <max_people>");
                    return;
                }
                try {
                    String line = String.join(" ", message);
                    String name = utils.getNameScanner(line);
                    int id = Integer.parseInt(message[2]);
                    double price = Double.parseDouble(rightParts[0]);
                    if (!Utils.ValidDate(rightParts[1])){return;}
                    String expirationStrg = rightParts[1];
                    int maxPeople = Integer.parseInt(rightParts[2]);
                    if (maxPeople>100){
                        System.out.println("have exceeded the maximum number of people allowed(100)");
                        break;
                    }
                    if(!validatePlanningTime(ProductType.Meeting, expirationStrg)){
                        return;
                    }
                    try {
                        ComplexProduct complexProduct = new ComplexProduct(id, name, price, expirationStrg, maxPeople,ProductType.Meeting);
                        if (productlist.addProduct(complexProduct)) {
                            System.out.println(complexProduct);
                            System.out.println("prod add: ok");
                        } else {
                            System.out.println("prod add: error");
                        }
                    } catch (Exception e) {
                        System.out.println("Error creating product: " + e.getMessage());

                    }
                } catch (Exception e) {
                    System.out.println("Error: wrong format. Use prod addMeeting " +
                            "<id> \"<name>\" <price> <expiration: yyyy-MM-dd> <max_people >");
                }
                break;
            case "addfood":
                String in = String.join(" ", message);
                if (rightParts.length != 3) {
                    System.out.println("Usage: prod "+command+" <id> <name> <price> <expiration: yyyy-MM-dd> <max_people>");
                    return;
                }
                try {
                    String line = String.join(" ", message);
                    String name = Utils.getNameScanner(line);
                    int id = Integer.parseInt(message[2]);
                    double price = Double.parseDouble(rightParts[0]);
                    String expirationStrg = rightParts[1];
                    int maxPeople = Integer.parseInt(rightParts[2]);
                    if (maxPeople>100){
                        System.out.println("have exceeded the maximum number of people allowed(100)");
                        break;
                    }
                    if(!validatePlanningTime(ProductType.Food, expirationStrg)){
                        return;
                    }

                    try {
                        ComplexProduct complexProduct = new ComplexProduct(id, name, price, expirationStrg, maxPeople,ProductType.Food);
                        if (productlist.addProduct(complexProduct)) {
                            System.out.println(complexProduct);
                            System.out.println("prod add: ok");
                        } else {
                            System.out.println("prod add: error");
                        }
                    } catch (Exception e) {
                        System.out.println("Error creating product: " + e.getMessage());

                    }
                } catch (Exception e) {
                    System.out.println("Error: wrong format. Use prod addMeeting " +
                            "<id> \"<name>\" <price> <expiration: yyyy-MM-dd> <max_people >");
                }
                break;
            default:
                unknownCommand();
                break;
        }
    }

    /**
     * Switch to operate all the possible commands for a ticket,
     * including new, add, remove and print, calling for
     * methods in the Ticket class that implement the commands
     *
     * @param message array of Strings with parameters of the command
     */
    private void optionsOfTicket(String[] message) {
        if (message.length < 2) {
            System.out.println("Usage: ticket with new, add, remove or print");
            return;
        }
        String command = message[1].toLowerCase();


        switch (command) {
            case "new":
                if (userList.containsId(message[message.length-1])&& userList.containsId(message[message.length-2])) {
                    if (message[2].matches("[0-9]+") && message[2].length() >= 5) {
                        currentTicket = ticketList.createTicket(message[2], message[3], message[4]);
                        currentTicket.setState(stateTicket.empty);
                        System.out.println(currentTicket.toString());
                        System.out.println("ticket new: ok");
                    } else {
                        currentTicket = ticketList.createTicket(null, message[2], message[3]);
                        currentTicket.setState(stateTicket.empty);
                        System.out.println(currentTicket.toString());
                        System.out.println("ticket new: ok");
                    }
                }
                else{
                    System.out.println("Incorrect format or IDs, use: \n"+
                            "ticket new <ticketId> <CashId> <ClientID> or \n" +
                            "ticket new <CashId> <ClientId>");
                }
                break;
            case "add":
                try {
                    int id, quantity;
                    currentTicket = ticketList.getTicket(message[2]);
                    String CashId = message[3];
                    id = Integer.parseInt(message[4]);
                    Product p = productlist.getProduct(id);
                    quantity = Integer.parseInt(message[5]);
                    if (userList.containsId(CashId)&& currentTicket !=null){
                        if (p.getProductType() == ProductType.ProductPersonalized || p.getProductType() == ProductType.Product){
                        String Custom = "";
                        for (int i =6;i<message.length;i++){
                            Custom += (message[i]);
                        }
                        if (!Custom.isEmpty()){
                            CustomProduct product = (CustomProduct) productlist.getProduct(id);
                            product.addPersonalized(Custom);
                        }
                        }else if(p.getProductType() == ProductType.Meeting || p.getProductType() == ProductType.Food){
                            ComplexProduct product =(ComplexProduct) p;
                            product.setPeople(quantity);
                            if(!(quantity <= product.getMAX_PEOPLE())){
                                System.out.println("have exceeded the maximum number of people allowed");
                                break;
                            }
                            quantity = 1;
                        }
                        try {
                            currentTicket.addProductToTicket(productlist, id, quantity);
                            System.out.println(currentTicket.toString());
                            System.out.println("ticket add: ok");
                        } catch (Exception e) {
                            System.out.println("Error adding product");
                        }
                    }else {
                        System.out.println("ticket or cashID not found");
                    }
                } catch (Exception e) {
                    System.out.println("inappropriate format");
                    System.out.println("ticket add <ticketID> <CashId> <id> <quantity>" +"\n"+
                            "ticket add <ticketID> <CashId> <id> <people>");
                }
                break;
            case "remove":
                if (message.length != 5) {
                    System.out.println("inappropriate format" + "\n" + "ticket remove <ticketId> <cashId> <prodId>");
                    return;
                }
                try {
                    currentTicket = ticketList.getTicket(message[2]);
                    String CashId = message[3];
                    if (userList.containsId(CashId)&& currentTicket !=null){
                        int id = Integer.parseInt(message[4]);
                        currentTicket.removeProduct(id);
                        System.out.println(currentTicket.toString());
                        System.out.println("ticket remove: ok");
                    }else {
                        System.out.println("ticket or cashID not found");
                    }
                } catch (Exception e) {
                    System.out.println("inappropriate format" + "\n" + "ticket remove <ticketId> <cashId> <prodId>");
                }
                break;
            case "print":
                try {currentTicket = ticketList.getTicket(message[2]);
                    String CashId = message[3];
                    String date ="";
                    if (userList.containsId(CashId)&& currentTicket !=null){
                        for (int i = 0; i<currentTicket.getNumProductInTicket();i++){
                            Product p = currentTicket.getProduct(i);
                            if (p.getProductType() == ProductType.Food || p.getProductType() == ProductType.Meeting){
                                ComplexProduct product= (ComplexProduct) p;
                                date = ((ComplexProduct) p).getExpirationDate();
                                if (!validatePlanningTime(p.getProductType(),date)){
                                    currentTicket.removeProduct(p.getID());
                                }
                            }

                        }
                        ticketList.CloseTicket(currentTicket,Utils.getTime("GMT+1"));
                        System.out.println(currentTicket.toString());
                        System.out.println("ticket print: ok");
                    }else {
                        System.out.println("ticket or cashID not found");
                    }
                } catch (Exception e) {
                    System.out.println("ticket print: fail");
                }
                break;
            case "list":
                System.out.print(ticketList.toString());
                System.out.println("ticket list: ok");
                break;
            default:
                unknownCommand();
                break;
        }
    }

    /**
     * Switch to operate all the possible commands for a client
     * and some for user list, including add, remove and list, calling
     * for methods in Client and UserList to implement the commands.
     *
     * @param message array of Strings with parameters of the command
     */
    private void optionsClient(String[] message) {
        if (message.length < 2) {
            System.out.println("Usage: client add/remove/list");
            return;
        }
        String command = message[1].toLowerCase();
        switch (command) {
            case "add":
                clientAdd(message);
                break;
            case "remove":
                clientRemove(message);
                break;
            case "list":
                clientList();
                break;
            default:
                unknownCommand();
        }
    }

    /**
     * Tries to add the client to the User list, checking every possible
     * error before trying to add
     * @param message parameters for client
     */
    private void clientAdd(String[] message) {
        try {
            String fullLine = String.join(" ", message);
            String name = utils.getNameScanner(fullLine);
            String[] rightParts = secondPartArray(fullLine);
            
            if (rightParts.length < 3) {
                System.out.println("client add: error");
                return;
            }
            String dni = rightParts[0];
            String email = rightParts[1];
            String cashId = rightParts[2];

            if (!Utils.validName(name)) {
                System.out.println("client add: error");
                return;
            }
            if (!(Utils.validDNI(dni) || Utils.validNIE(dni))) {
                System.out.println("client add: error");
                return;
            }
            if (!Utils.validEmail(email)) {
                System.out.println("client add: error");
                return;
            }
            if (!Utils.validCashId(cashId)) {
                System.out.println("client add: error");
                return;
            }

            Client c = new Client(name, dni, email, cashId);
            boolean added = userList.addClient(c);
            if (added) {
                System.out.println("client add: ok");
            } else {
                System.out.println("client add: error");
            }
        } catch (Exception e) {
            System.out.println("client add: error");
        }
    }

    /**
     * Tries to remove the client from the user list
     * @param message identification of the client
     */
    private void clientRemove(String[] message) {
        if (message.length < 3) {
            System.out.println("client remove: error");
            return;
        }
        String dni = message[2];
        boolean removed = userList.removeUser(dni);
        if (removed) {
            System.out.println("client remove: ok");
        } else {
            System.out.println("client remove: error");
        }
    }

    /**
     * Lists the clients from the user list, calling from
     * the method in UserList class
     */
    private void clientList() {
        userList.printClients();
        System.out.println("client list: ok");
    }

    /**
     * Switch to operate all the possible commands for a cashier
     * and some for user list, including add, remove, list, and list
     * all cashier tickets, calling for methods in Cash and UserList
     * to implement the commands
     *
     * @param message array of Strings with parameters of the command
     */
    private void optionsCash(String[] message) {
        if (message.length < 2) {
            System.out.println("Usage: cash add/remove/list/tickets");
            return;
        }
        String command = message[1].toLowerCase();
        switch (command) {
            case "add":
                cashAdd(message);
                break;
            case "remove":
                cashRemove(message);
                break;
            case "list":
                cashList();
                break;
            case "tickets":
                cashTickets(message);
                break;
            default:
                unknownCommand();
        }
    }

    /**
     * Tries to add the cash to the user list, checking every possible
     * error before trying to add
     * @param message parameters for cashier
     */
    private void cashAdd(String[] message) {
        try {
            String fullLine = String.join(" ", message);
            String name = utils.getNameScanner(fullLine);
            String[] rightParts = secondPartArray(fullLine);
            String email;
            String id = null;

            if (message.length >= 4 && message[2].startsWith("UW")) {
                id = message[2];
                email = message[message.length - 1];
            } else {
                email = message[message.length - 1];
            }

            if (!Utils.validName(name) || !Utils.validEmail(email) || (id != null && !Utils.validCashId(id))) {
                System.out.println("cash add: error");
                return;
            }

            Cash cash;
            if (id == null) cash = new Cash(name, email);
            else cash = new Cash(name, email, id);

            boolean added = userList.addCash(cash);
            if (added) {
                System.out.println("cash add: ok");
            } else {
                System.out.println("cash add: error");
            }
        } catch (Exception e) {
            System.out.println("cash add: error");
        }
    }

    /**
     * Tries to remove the cashier from the user list
     * @param message cashier identification
     */
    private void cashRemove(String[] message) {
        boolean removed = false;
        if (message.length < 3) {
            System.out.println("cash remove: error");
            return;
        }
        String id = message[2];
        Cash cash  = (Cash) userList.getUserByID(id);
        if (cash != null){
        ticketList.removeTicket(cash.getIdentifier());
        removed = userList.removeUser(id);
        }
        if (removed) System.out.println("cash remove: ok");
        else System.out.println("cash remove: error");
    }

    /**
     * Lists the cashiers from the user list, calling from
     * the method in UserList class
     */
    private void cashList() {
        userList.printCashiers();
        System.out.println("cash list: ok");
    }

    /**
     * Lists all the tickets from the cashier, checking in the
     * ticket list and printing if they are from said cashier
     * @param message cashier identification
     */
    private void cashTickets(String[] message) {
        if (message.length < 3) {
            System.out.println("cash tickets: error");
            return;
        }
        ArrayList<Ticket> tickets = new ArrayList<>();
        tickets = ticketList.getTicketofCashId(message[2]);
        for (int i =0;i<tickets.size();i++){
        System.out.println(tickets.get(i).toString());}
        //FALTA POR HACERLO
    }
    
    
    private String[] secondPartArray(String input){
        int firstQuote = input.indexOf('"');
        int secondQuote = input.indexOf('"', firstQuote + 1);

        String right = input.substring(secondQuote + 1).trim();
        String[] rightParts = right.split(" ");
        return rightParts;
    }

    private boolean validatePlanningTime(ProductType typeProduct, String expirationDate) {
        //YYYY-MM-DD-HH:MM
        String[] currentTimeString = Utils.getTime("GMT+1").trim().split("[:-]");
        String[] time = expirationDate.trim().split("[:-]");
        LocalDateTime now =  LocalDateTime.of(Integer.parseInt(currentTimeString[0]),Integer.parseInt(currentTimeString[1]),
                Integer.parseInt(currentTimeString[2]),Integer.parseInt(currentTimeString[3]),Integer.parseInt(currentTimeString[4]));
        LocalDateTime Date = LocalDateTime.of(Integer.parseInt(time[0]),Integer.parseInt(time[1]),Integer.parseInt(time[2]),
                0,0);
        long HourD = ChronoUnit.HOURS.between(now,Date);
        boolean isValid = true;
        if (typeProduct == ProductType.Food) {
            if (HourD < 72) {
                System.out.println("Error adding product");
                isValid = false;
            }
        }else{
            if (HourD< 12){
                System.out.println("Error adding meeting");
                isValid=false;
            }
        }
        return isValid;
    }

    /**
     * Boolean to check if a field introduced by the user is
     * valid or not
     *
     * @param field field to check its validity
     * @return true or false (valid or not)
     */
    private boolean validField(String field) {
        String[] allowedFields = {"name", "category", "price"};
        boolean validField = false;
        for (String f : allowedFields) {
            if (f.equals(field)) {
                validField = true;
                break;
            }
        }

        if (!validField) {
            System.out.println("Error: invalid field. The allowed fields are: name, category, price");
        }
        return validField;
    }

    /**
     * Welcomes the user to the program and suggests the use of the help command
     */
    private void init() {
        System.out.println("Welcome to the ticket module App.");
        System.out.println("Ticket module. Type 'help' to see commands:");

    }

    /**
     * Shows in screen all the possible commands for the user to choose
     */
    private void helpCommand() {
        String[] commands = {
                "◦ Client/Cash:",
                "   client add \"<name>\" <DNI> <email> <cashId>",
                "   client remove <DNI>",
                "   client list",
                "   cash add [<id>] \"<name>\" <email>",
                "   cash remove <id>",
                "   cash list",
                "   cash tickets <id>",

                "◦ Ticket:",
                "   ticket new [<id>] <cashId> <userId>",
                "   ticket add <ticketId> <cashId> <prodId> <amount> [--p<txt> --p<txt>]",
                "   ticket remove <ticketId> <cashId> <prodId>",
                "   ticket print <ticketId> <cashId>",
                "   ticket list",

                "◦ Product:",
                "   prod add [<id>] \"<name>\" <category> <price> [<maxPers>]",
                "   prod update <id> NAME|CATEGORY|PRICE <value>",
                "   prod addFood [<id>] \"<name>\" <price> <expiration: yyyy-MM-dd> <max_people>",
                "   prod addMeeting [<id>] \"<name>\" <price> <expiration: yyyy-MM-dd> <max_people>",
                "   prod list",
                "   prod remove <id>",

                "◦ General:",
                "   echo \"<texto>\"",
                "   help",
                "   exit"
        };
        System.out.println("Commands:");
        for (String cmd : commands) {
            System.out.println(" " + cmd);
        }

        System.out.println("\n" + "Categories: " +
                "MERCH, STATIONERY, CLOTHES, BOOK, ELECTRONICS" +
                "\nDiscounts if there are ≥2 units in the category: " +
                "MERCH 0%, STATIONERY 5%, CLOTHES 7%, BOOK 10%, ELECTRONICS 3%"
        );
    }

    /**
     * Shows in screen the farewell message to the user
     */
    private void exitProgram() {
        System.out.println("Closing application.");
        System.out.println("Goodbye!");
    }

    /**
     * Shows in screen that the command introduced is unknown
     */
    private void unknownCommand() {
        System.out.println("Command unknown. Type \"help\" to see commands:");
    }
}

