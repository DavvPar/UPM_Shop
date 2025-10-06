package es.upm.etsisi.poo;

import java.util.Scanner;

/**
 * App is the main class where the program is
 * executed. It uses the methods from other classes
 * and executes a Command Line Interface for the
 * user to interact with and introduce commands
 * to operate with the data.
 */
public class App {
    static  Utils utils = null;
    static Scanner sc = new Scanner(System.in);
    static int totalPrice = 0;
    /**
     * Maximum number of products that the ticket
     * or product list can have.
     */
    static int MaxNumProduct = 200;
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
    private void init(){
        System.out.println("Welcome to the ticket module App.");
        System.out.println("Ticket module. Type 'help' to see commands:");

    }

    /**
     * This method is the core of the main program and
     * determines how the application runs, it executes
     * the introduced commands.
     */
    private void run(){
        boolean cont = true;
        while (cont){
            System.out.print("> ");
            String line = sc.nextLine();
            String[] lineSepSpace = line.split(" ");

            switch (lineSepSpace[0]){
                case "prod":
                    // Continuar casos "prod" y "ticket" cuando hagamos las otras clases.
                    switch (lineSepSpace[1]){
                        case "add":
                            break;
                    }
                case "ticket":
                    switch (lineSepSpace[1]){
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

    private void optionsOfProd(String[] messaje){
        if (messaje.length < 2) {
            System.out.println("Usage: prod with add, list, update or remove");
            return;
        }

        String command = messaje[1];

        switch (command){
            case "add":
                if (messaje.length < 6) {
                    System.out.println("Usage: prod add <id> \"<name>\" <category> <price>");
                    return;
                }
                /*
                int id = Integer.parseInt(messaje[2]);
                String name = messaje[3].replace("\"", ""); // quita comillas
                Category category = Category.valueOf(messaje[4].toUpperCase());
                double price = Double.parseDouble(messaje[5]);

                Product p = new Product(id, name, category, price);
                productlist.addProduct(p);*/
                break;

            case "list":
                productlist.listProducts();
                break;

            case "update":
                if (messaje.length < 5) {
                    System.out.println("Usage: prod update <id> <field> <value>");
                    return;
                }
                int idToUpdate = Integer.parseInt(messaje[2]);
                String field = messaje[3].toLowerCase();
                String value = messaje[4];

                break;

            case "remove":
                break;
        }
    }



    /**
     * Shows in screen all the possible commands for
     * the user to choose.
     */
    private void helpCommand(){
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
        for(String cmd : commands){
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
    private void exitProgram(){
        System.out.println("Closing application.");
        System.out.println("Goodbye!");
    }

    private void unknownCommand(){
        System.out.println("Command unknown. Type \"help\" to see commands:");
    }
}