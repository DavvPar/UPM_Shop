package es.upm.etsisi.poo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import es.upm.etsisi.poo.enums.*;
import es.upm.etsisi.poo.user.UserValidator;

public class Utils {
    public static String convertDate(String inputDate) {
        LocalDate date = LocalDate.parse(inputDate);
        ZoneId defaultZone = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = date.atStartOfDay(defaultZone);
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        return zonedDateTime.format(formatter);
    }

    public static String getShortId(String Id){
        String shortId = "";
            String[] message =Id.trim().split("[-:]");
            for (int i = 0 ; i < message.length; i++){
                if(message[i].length() >= 5){
                    shortId = message[i];
                }
            }
        return shortId;
    }

    public static TicketType TypeTicket( String ID,String command){
        TicketType type;
        if (UserValidator.validNIF(ID)){
            switch (command.toUpperCase()){
                case "C"-> type = TicketType.businessC;
                case "S"-> type = TicketType.businessS;
                default -> type = TicketType.businessP;
            }
        }else type = TicketType.Client;
        return type;
    }

    public static String getTime(){
        LocalDateTime date = LocalDateTime.now();
        return date.getYear() + "-"+date.getMonthValue() + "-"+date.getDayOfMonth() + "-"+date.getHour() + ":" + date.getMinute();
    }

    public static int getRandomNumber(int n){
        StringBuilder resul = new StringBuilder();
        for(int i = 0;i<n;i++){
            resul.append((int) (Math.random() * 10));
        }
        return Integer.parseInt(resul.toString());
    }

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

    }public static String generateCashId(){
        String id;
        id = ("UW" + (getRandomNumber(7)));
        return id;
    }

    public static boolean validDate(String Date) {
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

    public static String[] secondPartArray(String input){
        int firstQuote = input.indexOf('"');
        int secondQuote = input.indexOf('"', firstQuote + 1);

        if (firstQuote == -1 || secondQuote == -1) {
            return new String[0];
        }

        String rightPart = input.substring(secondQuote + 1).trim();

        if (rightPart.isEmpty()) {
            return new String[0];
        }
        return rightPart.split(" ");
    }
}
