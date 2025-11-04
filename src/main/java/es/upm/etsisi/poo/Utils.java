package es.upm.etsisi.poo;

import java.util.Calendar;
import java.util.TimeZone;

public class Utils {
    /**
     *method that returns the date of a time zone
     * @param timezone time Zone.For example, Spain is in the GMT+1 time zone.
     * @return YYYY-MM-DD-HH:MM
     */
    public String getTime(String timezone){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(timezone));
        String resul = calendar.get(Calendar.YEAR) + "-"+calendar.get(Calendar.MONTH)+"-"+
                calendar.get(Calendar.DAY_OF_MONTH) + "-"+ calendar.get(Calendar.HOUR_OF_DAY)+":"+
                calendar.get(Calendar.MINUTE);
        return resul;
    }
    /**
     * method that returns n random numbers
     * @param n numbers to need
     * @return random number with length n
     */
    public int getRandomNumber(int n){
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
    public String getNameScanner(String message){
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

    public boolean validName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public boolean validEmail(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    public boolean validCashId(String cashId) {
        return ((cashId != null && (cashId.matches("^UW\\d{7}$"))) && !CashList.cashIdUsed(cashId));
    }

}
