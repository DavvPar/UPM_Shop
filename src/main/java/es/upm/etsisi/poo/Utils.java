package es.upm.etsisi.poo;

public class Utils {

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
