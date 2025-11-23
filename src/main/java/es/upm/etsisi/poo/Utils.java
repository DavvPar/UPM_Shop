package es.upm.etsisi.poo;

import java.util.Calendar;
import java.util.TimeZone;

public class Utils {
    /**
     *method that returns the date of a time zone
     * @param timezone time Zone.For example, Spain is in the GMT+1 time zone.
     * @return YYYY-MM-DD-HH:MM
     */
    public static String getTime(String timezone){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(timezone));
        return calendar.get(Calendar.YEAR) + "-"+calendar.get(Calendar.MONTH)+"-"+
                calendar.get(Calendar.DAY_OF_MONTH) + "-"+ calendar.get(Calendar.HOUR_OF_DAY)+":"+
                calendar.get(Calendar.MINUTE);
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

}
