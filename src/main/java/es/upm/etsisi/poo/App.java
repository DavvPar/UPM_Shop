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

