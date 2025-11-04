package es.upm.etsisi.poo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CashList {
    /**
     * Prefix for the cashIds
     */
    private static final String PREFIX = "UW";
    /**
     * List of cashierIds
     */
    private static Set<String> cashIdUsed = new HashSet<>();
    /**
     * List of cashiers
     */
    private static ArrayList<Cash> cashList = new ArrayList<>();
    /**
     * Current number of cashiers
     */
    private static int cashNum;

    /**
     * Constructor of the Class CashList
     */
    public CashList() {
        Set<String> cashIdUsed = new HashSet<>();
        ArrayList<Cash> cashiers = new ArrayList<>();
        cashNum = 0;
    }

    /**
     * Generates a new cashId that isn't already existing.
     * @return new unique cashId
     */
    public static String generateUniqueId() {
        String id;
        do{
            id = (PREFIX + String.format("%07d", Double.valueOf(Math.random() * 10_000_000).intValue()));
        } while (cashIdUsed(id));
        cashIdUsed.add(id);
        cashNum++;
        return id;
    }

    /**
     * Checks if cashId is used
     * @param id
     * @return true if cashId is already used
     */
    public static boolean cashIdUsed(String id){
        return cashIdUsed.contains(id);
    }

    //TODO toString
}
