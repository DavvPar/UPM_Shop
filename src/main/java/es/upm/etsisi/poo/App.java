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
    static int MaxNumProduct = 100;
    static Ticket currentTicket = new Ticket(MaxNumProduct);
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
                    listCommands();
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
     * Shows in screen all the possible commands for
     * the user to choose.
     */
    private void listCommands(){
        System.out.println("Command list:");
        System.out.println("prod add <id> \"<name>\" <category> <price>");
        System.out.println("prod list");
        System.out.println("prod update <id> NAME|CATEGORY|PRICE <value>");
        System.out.println("prod remove <id>");
        System.out.println("ticket new");
        System.out.println("ticket add <prodId> <quantity>");
        System.out.println("ticket remove <prodId>");
        System.out.println("ticket print");
        System.out.println("echo \"<texto>\"");
        System.out.println("help");
        System.out.println("exit");
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