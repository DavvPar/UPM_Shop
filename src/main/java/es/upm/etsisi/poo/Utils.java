package es.upm.etsisi.poo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import es.upm.etsisi.poo.enums.*;


/**
 * Utils is a class created for storing useful methods for the program
 * that do not belong in any created class
 */
public class Utils {
    /**
     * Static method that converts the time format from dd/mm/yyyy to EEE MMM dd HH:mm:ss z yyyy
     * @param inputDate dd/mm/yyyy
     * @return EEE MMM dd HH:mm:ss z yyyy
     */
    public static String convertDate(String inputDate) {
        LocalDate date = LocalDate.parse(inputDate);
        ZoneId defaultZone = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = date.atStartOfDay(defaultZone);
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        return zonedDateTime.format(formatter);
    }
    /**
     * Get the short unique part of the id
     * @return shortId
     */
    public static String getShortId(String Id){
        String shortId ="";
            String[] message =Id.trim().split("[-:]");
            for (int i =0;i<message.length;i++){
                if(message[i].length()>=5){
                    shortId = message[i];
                }
            }
        return shortId;
    }
    public static TicketType TypeTicket( String ID,String command){
        TicketType type;
        if (Utils.validNIF(ID)){
            switch (command.toUpperCase()){
                case "C"-> type = TicketType.businessC;
                case "S"-> type = TicketType.businessS;
                default -> type = TicketType.businessP;
            }
        }else type = TicketType.Client;
        return type;
    }
    public static boolean validNIF(String nif) {
        if (nif == null || nif.length() != 9 || !nif.matches("^[ABCDEFGHJNPQRSUVW][0-9]{7}[0-9A-J]$")) {
            return false;
        }else{
            return true;
        }
    }
    /**
     *method that returns the date of now
     * @return YYYY-MM-DD-HH:MM
     */
    public static String getTime(){
        LocalDateTime date = LocalDateTime.now();
        return date.getYear() +"-"+date.getMonthValue()+"-"+date.getDayOfMonth()+"-"+date.getHour()+":"+date.getMinute();
    }
    /**
     * method that returns n random numbers
     * @param n numbers to need
     * @return random number with length n
     */
    public static int getRandomNumber(int n){
        StringBuilder resul = new StringBuilder();
        for(int i = 0;i<n;i++){
            resul.append((int) (Math.random() * 10));
        }
        return Integer.parseInt(resul.toString());
    }

    /**
     * Takes the name from between ""
     * @param message
     * @return the name inside
     */
    public static String getNameScanner(String message){
        int index0 = 0,indexN = 0;
        boolean found = false;
        String c = "";
        int i = message.length();
        int j = 0;
        while(j < i && !found){
            c = String.valueOf(message.charAt(j));
            if(c.equals("\"")){
                index0 = j+1;
                found  = true;
            }
            j++;
        }
        i = message.length()-1;
        found =false;
        while(i>0 && !found){
            c = String.valueOf(message.charAt(i));
            if(c.equals("\"")){
                indexN = i;
                found = true;
            }
            i--;
        }
        return message.substring(index0,indexN);
    }

    public static String generateCashId(){
        String id;
        id = ("UW" + (getRandomNumber(7)));
        return id;
    }

    public static boolean validName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public static boolean validEmail(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    public static boolean validDNI(String dni) {
        if (dni == null) return false;
        dni = dni.toUpperCase();
        if (!dni.matches("\\d{8}[A-Z]")) {
            return false;
        }
        String[] letters = {
                "T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X",
                "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"
        };
        int num = Integer.parseInt(dni.substring(0, 8));
        String correctLetter = letters[num % 23];
        String DNIletter = dni.substring(8);

        return DNIletter.equals(correctLetter);
    }

    public static boolean validNIE(String nie) {
        if (nie == null) return false;
        nie = nie.toUpperCase();
        if (!nie.matches("^[XYZ][0-9]{7}[A-Z]$")) {
            return false;
        }
        char first = nie.charAt(0);
        String number = switch (first) {
            case 'X' -> "0";
            case 'Y' -> "1";
            case 'Z' -> "2";
            default  -> "";
        };
        number += nie.substring(1, 8);
        String letters = "TRWAGMYFPDXBNJZSQVHLCKE";
        int num = Integer.parseInt(number);
        char correctLetter = letters.charAt(num % 23);
        return correctLetter == nie.charAt(8);
    }

    public static boolean validCashId(String cashId) {
        return ((cashId != null && (cashId.matches("^UW\\d{7}$"))));
    }

    public static boolean ValidDate(String Date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fecha = LocalDate.parse(Date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            System.out.println("Check that the date is correct, exists, and is in the correct format." +
                    "YYYY/MM/DD");
            return false;
        }
    }
}
