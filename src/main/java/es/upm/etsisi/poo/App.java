package es.upm.etsisi.poo;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import es.upm.etsisi.poo.command.CommandManager;
import es.upm.etsisi.poo.controller.ExitController;
import es.upm.etsisi.poo.enums.*;
import es.upm.etsisi.poo.products.*;
import es.upm.etsisi.poo.ticket.*;
import es.upm.etsisi.poo.user.*;

public class App {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean readingFromFile = false;
        Scanner fileScanner = null;
        Scanner consoleScanner = null;

        try {
            if (args.length >= 1) {
                readingFromFile = true;
                String file_name = args[0];
                fileScanner = new Scanner(new FileReader(file_name));
            }
        } catch (FileNotFoundException e) {
            System.err.println("Archivo no encontrado: " + args[0]);
            System.err.println("Continuando en modo interactivo...");
        }
        consoleScanner = new Scanner(System.in);

        App application = new App();
        application.init();
        application.run(fileScanner, consoleScanner, readingFromFile);
    }
    private void run(Scanner fileScanner, Scanner consoleScanner, boolean readingFromFile) {
        ExitController exitController = new ExitController();
        CommandManager commandManager = new CommandManager(exitController);
        boolean fileFinished = false;
        while (!exitController.isExitRequested()) {
            Scanner currentScanner;
            if (readingFromFile && !fileFinished && fileScanner != null) {
                currentScanner = fileScanner;
            } else {
                currentScanner = consoleScanner;
                readingFromFile = false;
                if (!fileFinished && fileScanner != null) {
                    fileScanner.close();
                    fileFinished = true;
                }
            }
            if (currentScanner == consoleScanner) {
                System.out.print("\ntUPM> ");
            }
            String line;
            if (currentScanner.hasNextLine()) {
                line = currentScanner.nextLine();

                if (currentScanner == fileScanner) {
                    System.out.println("\ntUPM> " + line);
                }
            } else {
                if (currentScanner == fileScanner) {
                    fileScanner.close();
                    readingFromFile = false;
                    fileFinished = true;
                    continue;
                } else {
                    break;
                }
            }

            if (line.isBlank()) continue;

            boolean result = commandManager.execute(line);
        }

        if (fileScanner != null) {
            fileScanner.close();
        }
        if (consoleScanner != null) {
            consoleScanner.close();
        }
    }

    private void init() {
        System.out.println("Welcome to the ticket module App.");
        System.out.println("Ticket module. Type 'help' to see commands:");
    }
}


    /*private void run(Scanner scanner, boolean readingFromFile) {
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
                    Product product = null;
                    if (message.length==4){
                      String expireDate = message[2];
                      ServiceType Stype = ServiceType.valueOf(message[3].toUpperCase());
                      product =new Service((productlist.getNumservice()+1)+"S",Stype,expireDate,ProductType.Service);
                    }else{
                        String line = String.join(" ", message);
                        String name = Utils.getNameScanner(line); //cambio en get nombre de scanner
                        String id = message[2];

                        CategoryType type = CategoryType.valueOf(rightParts[0].toUpperCase());
                        Category category = new Category(type);
                        double price = Double.parseDouble(rightParts[1]);
                        if (rightParts.length == 3) {
                            int maxPers = Integer.parseInt(message[message.length-1]);
                            product = new CustomProduct(id, name, category, price, maxPers,ProductType.ProductPersonalized);
                        } else {
                            product = new CustomProduct(id, name, category, price,0,ProductType.Product);
                        }
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

                String idToUpdate = message[2];
                String field = message[3].toLowerCase();
                String value;
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
                String idRemove = message[2];
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
                    String name = Utils.getNameScanner(line);
                    String id = message[2];
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
                // Never used: String in = String.join(" ", message);
                if (rightParts.length != 3) {
                    System.out.println("Usage: prod "+command+" <id> <name> <price> <expiration: yyyy-MM-dd> <max_people>");
                    return;
                }
                try {
                    String line = String.join(" ", message);
                    String name = Utils.getNameScanner(line);
                    String id = message[2];
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

    private void optionsOfTicket(String[] message) {
        if (message.length < 2) {
            System.out.println("Usage: ticket with new, add, remove or print");
            return;
        }
        String command = message[1].toLowerCase();


        switch (command) {
            case "new":
                String[] input = String.join(" ", message).split("-");
                String[] Firspart = input[0].split(" ");
                String typeTicket ="";
                TicketType type;
                if (input.length>1){
                    typeTicket =input[1];
                }
                if (userList.containsId(Firspart[Firspart.length-1])&& userList.containsId(Firspart[Firspart.length-2])) {
                    type = Utils.TypeTicket(Firspart[Firspart.length-1],typeTicket);
                    if (Firspart[2].matches("[0-9]+") && Firspart[2].length() >= 5) {
                        currentTicket = ticketList.createTicket(Firspart[2], Firspart[3], Firspart[4],type);
                        if(currentTicket == null){
                            System.out.println("ticket new: error");
                        }else{
                            currentTicket.setState(stateTicket.empty);
                            System.out.println(currentTicket.toString());
                            System.out.println("ticket new: ok");
                        }
                    } else {

                        currentTicket = ticketList.createTicket(null, Firspart[2], Firspart[3],type);
                        System.out.println(currentTicket.toString());
                        System.out.println("ticket new: ok");
                    }
                }
                else{
                    System.out.println("Incorrect format or IDs, use:");
                    System.out.println("ticket new <ticketId> <CashId> <ClientID>");
                    System.out.println("ticket new <CashId> <ClientId>");
                }
                break;
            case "add":
                try {
                    String Custom = "";
                    int quantity;
                    currentTicket = ticketList.getTicket(message[2]);
                    String CashId = message[3];
                    String id = message[4];
                    Product p = productlist.getProduct(id);

                    if (userList.containsId(CashId)&& currentTicket !=null){
                        if (p.getProductType() != ProductType.Service){
                        quantity = Integer.parseInt(message[5]);
                        if (p.getProductType() == ProductType.ProductPersonalized || p.getProductType() == ProductType.Product){
                        for (int i =6;i<message.length;i++){
                            Custom += (message[i]);
                        }
                        }else if(p.getProductType() == ProductType.Meeting || p.getProductType() == ProductType.Food){
                            ComplexProduct product = (ComplexProduct) p;
                            product.setPeople(quantity);
                            if(!(quantity <= product.getMAX_PEOPLE())){
                                System.out.println("have exceeded the maximum number of people allowed");
                                break;
                            }
                            quantity = 1;
                        }
                        }else{
                            quantity = 1;
                        }
                        try {
                            if (currentTicket.getState() != stateTicket.closed){
                                currentTicket.addProductToTicket(productlist, id, quantity,Custom);
                                System.out.println(currentTicket.toString());
                                System.out.println("ticket add: ok");}
                            else {
                                System.out.println("ticket closed");
                            }
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
                        String id = message[4];
                        if (currentTicket.getState() != stateTicket.closed){
                        currentTicket.removeProduct(id);
                        System.out.println(currentTicket.toString());
                        System.out.println("ticket remove: ok");}
                        else {
                            System.out.println("ticket closed");
                        }
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
                    String date;
                    if (userList.containsId(CashId)&& currentTicket !=null){
                        for (int i = 0; i<currentTicket.getNumProductInTicket();i++){
                            Product p = currentTicket.getProduct(i);
                            if (p.getProductType() == ProductType.Food || p.getProductType() == ProductType.Meeting){
                                ComplexProduct product = (ComplexProduct) p;
                                date = (product).getExpirationDate();
                                if (!validatePlanningTime(p.getProductType(),date)){
                                    currentTicket.removeProduct(p.getID());
                                }
                            }

                        }
                        ticketList.CloseTicket(currentTicket,Utils.getTime());
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
                System.out.println("Ticket list:");
                System.out.print(ticketList.toString());
                System.out.println("ticket list: ok");
                break;
            default:
                unknownCommand();
                break;
        }
    }
    
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

    private void clientAdd(String[] message) {
        try {
            String fullLine = String.join(" ", message);
            String name = Utils.getNameScanner(fullLine);
            String[] rightParts = secondPartArray(fullLine);
            
            if (rightParts.length < 3) {
                System.out.println("client add: error");
                return;
            }
            String dni = rightParts[0];
            String email = rightParts[1];
            String cashId = rightParts[2];

            if (!UserValidator.validName(name)) {
                System.out.println("client add: error name");
                return;
            }
            if (!UserValidator.validEmail(email)) {
                System.out.println("client add: error email");
                return;
            }
            if (!UserValidator.validCashId(cashId)) {
                System.out.println("client add: error CashId");
                return;
            }
            Client c = null;
            if (UserValidator.validNIF(dni)){
            c = new Client(name, dni, email, cashId,ClientType.Business);
            }
            if (UserValidator.validDNI(dni) ||UserValidator.validNIE(dni)){
            c = new Client(name,dni,email,cashId,ClientType.Client);
            }
            if (c != null){
                if (userList.addClient(c)) {
                    System.out.println("client add: ok");
                }
                else {
                    System.out.println("client add: error");
                }
            }else{
                System.out.println("ClientID error,Incorrect clientID only accepts: DNI,NIE,NIF");
            }
        } catch (Exception e) {
            System.out.println("client add: error");
        }
    }

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

    private void clientList() {
        userList.printClients();
        System.out.println("client list: ok");
    }

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

    private void cashAdd(String[] message) {
        try {
            String fullLine = String.join(" ", message);
            String name = Utils.getNameScanner(fullLine);
            //Never used: String[] rightParts = secondPartArray(fullLine);
            String email;
            String id = null;

            if (message.length >= 4 && message[2].startsWith("UW")) {
                id = message[2];
                email = message[message.length - 1];
            } else {
                email = message[message.length - 1];
            }

            if (!UserValidator.validName(name) || !UserValidator.validEmail(email) || (id != null && !UserValidator.validCashId(id))) {
                System.out.println("cash add: error");
                return;
            }

            Cash cash = new Cash(name, email, id);

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

    private void cashRemove(String[] message) {
        boolean removed = false;
        if (message.length < 3) {
            System.out.println("cash remove: error");
            return;
        }
        String id = message[2];
        Cash cash  = (Cash) userList.getUserByID(id);
        if (cash != null){
        ticketList.removeTicket(cash.getId());
        removed = userList.removeUser(id);
        }
        if (removed) System.out.println("cash remove: ok");
        else System.out.println("cash remove: error");
    }

    private void cashList() {
        userList.printCashiers();
        System.out.println("cash list: ok");
    }

    private void cashTickets(String[] message) {
        if (message.length < 3) {
            System.out.println("cash tickets: error");
            return;
        }
        String cashier = message[2];
        if (UserValidator.validCashId(cashier) && userList.containsId(cashier)) {
            TicketList ticketsOfCash = ticketList.getTicketsOfCash(cashier);
            System.out.println("Tickets:");
            System.out.println(ticketsOfCash.toString());
            System.out.println("cash tickets: ok");
        }else{
            System.out.println("cash tickets:error");
        }
    }
    
    
    private String[] secondPartArray(String input){
        int firstQuote = input.indexOf('"');
        int secondQuote = input.indexOf('"', firstQuote + 1);

        String right = input.substring(secondQuote + 1).trim();
        return right.split(" ");
    }

    private boolean validatePlanningTime(ProductType typeProduct, String expirationDate) {
        //YYYY-MM-DD-HH:MM
        String[] currentTimeString = Utils.getTime().trim().split("[:-]");
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

    private void init() {
        System.out.println("Welcome to the ticket module App.");
        System.out.println("Ticket module. Type 'help' to see commands:");
    }

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
                "   ticket new [<id>] <cashId> <userId> -[c|p|s] (default -p option)",
                "   ticket add <ticketId> <cashId> <prodId> <amount> [--p<txt> --p<txt>]",
                "   ticket remove <ticketId> <cashId> <prodId>",
                "   ticket print <ticketId> <cashId>",
                "   ticket list",

                "◦ Product:",
                "   prod add [<id>] \"<name>\" <category> <price> [<maxPers>] || (\"<name>\" <category> )",
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

    private void exitProgram() {
        System.out.println("Closing application.");
        System.out.println("Goodbye!");
    }

    private void unknownCommand() {
        System.out.println("Command unknown. Type \"help\" to see commands:");
    }*/


