package es.upm.etsisi.poo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * App is the main class where the program is
 * executed. It uses the methods from other classes
 * and executes a Command Line Interface for the
 * user to interact with and introduce commands
 * to operate with the data.
 */
public class App {
    /**
     * Declares all the possible categories on
     * the program with their discount included.
     */
    static Scanner sc = new Scanner(System.in);
    /**
     * Maximum number of products that the ticket
     * or product list can have.
     */
    static int MaxNumProduct = 200;
    static int MaxNumProductTicket = 100;
    /**
     * Initialize the ticket with 200 as the
     * maximum amount of products.
     */
    static Ticket currentTicket = new Ticket(MaxNumProduct);
    static ProductList productlist = new ProductList(MaxNumProduct);

    /**
     * Main structure for executing the app.
     */
    public static void main(String[] args) {
        try {
            if (args.length>=1) {
                String file_name = args[0];
                sc = new Scanner(new FileReader(file_name));
            }
            else{
                sc =new Scanner(System.in);
            }
        } catch (FileNotFoundException e) {
            sc =new Scanner(System.in);
            throw new RuntimeException(e);
        }
        App application = new App();
        application.init();
        application.run(sc);
    }

    /**
     * This method is the core of the main program and
     * determines how the application runs, it executes
     * the introduced commands.
     */
    private void run( Scanner scanner) {
        boolean cont = true;
        while (cont) {
            System.out.print("tUPM> ");
            String line = scanner.nextLine();
            String[] lineSepSpace = line.split(" ");

            switch (lineSepSpace[0].toLowerCase()) {
                case "prod":
                    optionsOfProd(lineSepSpace);
                    break;
                case "ticket":
                    optionsOfTicket(lineSepSpace);
                    break;
                case "echo":
                    String[] echoSepSpace = line.split("\"");
                    System.out.println("echo \"" + echoSepSpace[1] + "\"");
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
     * Switch to operate all the possible commands for a product,
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
        switch (command) {
            case "add":
                if (message.length < 6) {
                    System.out.println("Usage: prod add <id> \"<name>\" <category> <price>");
                    return;
                }
                try {
                    //line: prod add id \name con espacios\ category price
                    String line = String.join(" ", message);
                    //Separamos por comillas
                    String[] parts = line.split("\"");
                    // parts[0] = "prod add <id> "
                    // parts[1] = "<name>" = nombre con espacios
                    // parts[2] = " <category> <price>"

                    String[] firstPart = parts[0].trim().split(" "); //prod, add, id

                    int id = Integer.parseInt(firstPart[2]);
                    Utils utils = new Utils();
                    String name = utils.getNameScanner(line); //cambio en get nombre de scanner

                    String[] rest = parts[2].trim().split(" ");//category, price

                    CategoryType type = CategoryType.valueOf(rest[0].toUpperCase());
                    Category category = new Category(type);
                    double price = Double.parseDouble(rest[1]);

                    try {
                        Product p = new Product(id, name, category, price);
                        if (productlist.addProduct(p)) {
                            String stringProd = p.toString();
                            System.out.println(stringProd);
                            System.out.println("prod add: ok");
                        } else {
                            System.out.println("prod add: error");
                        }
                    } catch (Exception e) {
                        System.out.println("Error creating product: " + e.getMessage());
                    }
                } catch (Exception e) {
                    System.out.println("Error: wrong format. Use prod add <id> \"<name>\" <category> <price>");
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
                String value;
                if(field.equalsIgnoreCase("name")){
                    String line = String.join(" ", message);
                    String[] parts = line.split("\"");
                    value = parts[1];
                }else{
                    value = message[4];
                }
                if(validField(field)){
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
        }
    }

    /**
     * Switch to operate all the possible commands for a ticket,
     * including new, add, remove and print, calling for
     * methods in the Ticket class that implement the commands.
     *
     * @param message array of Strings with parameters of the command
     */
    private void optionsOfTicket(String[] message){
        if (message.length < 2) {
            System.out.println("Usage: ticket with new, add, remove or print");
            return;
        }
        String command = message[1].toLowerCase();
        switch (command) {
            case "new":
                currentTicket = new Ticket(MaxNumProductTicket);
                System.out.println("ticket new: ok");
                break;
            case "add":
                try {
                    int id,quantity;
                    id =Integer.parseInt(message[2]);
                    quantity =Integer.parseInt(message[3]);
                    try{
                        currentTicket.addProductToTicket(productlist,id, quantity);
                        System.out.println(currentTicket.toString());
                        System.out.println("ticket add: ok");
                    }
                    catch (Exception e){
                        System.out.println("Error adding product");
                    }
                }catch (Exception e){
                    System.out.println("inappropriate format");
                    System.out.println("ticket add <id> <quantity>");
                }
                break;
            case "remove":
                if (message.length != 3){
                    System.out.println("inappropriate format" + "\n"+"ticket remove<id>");
                    return;
                }
                try{
                    int id = Integer.parseInt(message[2]);
                    currentTicket.removeProduct(id);
                    System.out.println(currentTicket.toString());
                    System.out.println("ticket remove: ok");
                }catch (Exception e){
                    System.out.println("inappropriate format" + "\n"+"ticket remove<id>");
                }
                break;
            case "print":
                try{
                    System.out.println(currentTicket.toString());
                    System.out.println("ticket print: ok");
                }
                catch (Exception e){
                    System.out.println("ticket print: fail");
                }
                break;
        }
    }

    /**
     * Boolean to check if a field introduced by the user is
     * valid or not.
     *
     * @param field field to check its validity
     * @return true or false (valid or not)
     */
    private boolean validField(String field){
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
     * Welcomes the user to the program and suggests the use of the help command.
     */
    private void init() {
        System.out.println("Welcome to the ticket module App.");
        System.out.println("Ticket module. Type 'help' to see commands:");

    }

    /**
     * Shows in screen all the possible commands for the user to choose.
     */
    private void helpCommand() {
        String[] commands = {
                "prod add <id> \"<name>\" <category> <price>",
                "prod update <id> NAME|CATEGORY|PRICE <value>",
                "prod remove <id>",
                "ticket new",
                "ticket add <prodId> <quantity>",
                "ticket remove <prodId>",
                "ticket print",
                "echo \"<texto>\"",
                "help",
                "exit"
        };
        System.out.println("Commands:");
        for (String cmd : commands) {
            System.out.println(" " + cmd);
        }

        System.out.println("\n"+"Categories: " +
                "MERCH, STATIONERY, CLOTHES, BOOK, ELECTRONICS" +
                "\nDiscounts if there are ≥2 units in the category: " +
                "MERCH 0%, STATIONERY 5%, CLOTHES 7%, BOOK 10%, ELECTRONICS 3%"
        );
    }

    /**
     * Shows in screen the farewell message to the user.
     */
    private void exitProgram() {
        System.out.println("Closing application.");
        System.out.println("Goodbye!");
    }

    /**
     * Shows in screen that the command introduced is unknown.
     */
    private void unknownCommand() {
        System.out.println("Command unknown. Type \"help\" to see commands:");
    }
}
