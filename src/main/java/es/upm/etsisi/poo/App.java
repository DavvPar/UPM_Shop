package es.upm.etsisi.poo;

import java.sql.SQLOutput;
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
    public Category MERCH = new Category(CategoryType.MERCH);
    public Category STATIONERY = new Category(CategoryType.STATIONERY);
    public Category CLOTHES = new Category(CategoryType.CLOTHES);
    public Category BOOK = new Category(CategoryType.BOOK);
    public Category ELECTRONICS = new Category(CategoryType.ELECTRONICS);
    static Utils utils = null;
    static Scanner sc = new Scanner(System.in);
    static double totalPrice = 0;
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
        App application = new App();
        application.init();
        application.run();
    }

    /**
     * Welcomes the user to the program and suggests
     * the use of the help command.
     */
    private void init() {
        System.out.println("Welcome to the ticket module App.");
        System.out.println("Ticket module. Type 'help' to see commands:");

    }

    /**
     * This method is the core of the main program and
     * determines how the application runs, it executes
     * the introduced commands.
     */
    private void run() {
        boolean cont = true;
        while (cont) {
            System.out.print("tUPM> ");
            String line = sc.nextLine();
            String[] lineSepSpace = line.split(" ");

            switch (lineSepSpace[0].toLowerCase()) {
                case "prod":
                    optionsOfProd(lineSepSpace);
                    break;
                // Continuar casos "prod" y "ticket" cuando hagamos las otras clases.
                case "ticket":
                    switch (lineSepSpace[1]) {
                        case "new":
                            break;
                    }
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
    private void optionsOfProd(String[] messaje) {
        if (messaje.length < 2) {
            System.out.println("Usage: prod with add, list, update or remove");
            return;
        }

        String command = messaje[1];
        switch (command.toLowerCase()) {
            case "add":
                if (messaje.length < 6) {
                    System.out.println("Usage: prod add <id> \"<name>\" <category> <price>");
                    return;
                }
                try {
                    //line: prod add id \name con espacios\ category price
                    String line = String.join(" ", messaje);
                    //Separamos por comillas
                    String[] parts = line.split("\"");
                    // parts[0] = "prod add <id> "
                    // parts[1] = "<name>" = nombre con espacios
                    // parts[2] = " <category> <price>"

                    String[] firstPart = parts[0].trim().split(" "); //prod, add, id

                    int id = Integer.parseInt(firstPart[2]);
                    String name = parts[1];

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
                if (messaje.length < 5) {
                    System.out.println("Usage: prod update <id> <field> <value>");
                    return;
                }
                int idToUpdate = Integer.parseInt(messaje[2]);
                String field = messaje[3].toLowerCase();
                String value = messaje[4];
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
                int idRemove = Integer.parseInt(messaje[2]);
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

    private boolean validField(String field){
        String[] allowedFields = {"name", "category", "price"};
        boolean validField = false;
        for (String f : allowedFields) {
            if (f.equals(field)) {
                validField = true;
            }
        }

        if (!validField) {
            System.out.println("Error: invalid field. The allowed fields are: name, category, price");
        }
        return validField;
    }


    /**
     * Shows in screen all the possible commands for
     * the user to choose.
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
        System.out.println("Command list:");
        for (String cmd : commands) {
            System.out.println(" " + cmd);
        }

        System.out.println("\nCategories: " +
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

    private void unknownCommand() {
        System.out.println("Command unknown. Type \"help\" to see commands:");
    }
}