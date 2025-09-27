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

    /**
     * Main structure for executing the app.
     */
    public static void main(String[] args) {
        App application = new App();
        application.init();
    }

    /**
     * Welcomes the user to the program and suggests
     * the use of the help command.
     */
    private void init(){
        System.out.println("Welcome to the ticket module App.\nTicket module. Type 'help' to see commands:");
    }
}