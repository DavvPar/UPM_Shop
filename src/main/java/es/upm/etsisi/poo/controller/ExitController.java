package es.upm.etsisi.poo.controller;

public class ExitController {

    private boolean exitRequested = false;

    public void requestExit() {
        this.exitRequested = true;
        System.out.println("Closing application.");
        System.out.println("Goodbye!");
    }

    public  boolean isExitRequested() {
        return exitRequested;
    }
}
