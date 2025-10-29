package es.upm.etsisi.poo;

public class Utils {
    public String getNameScanner(String message){
        int index0 = 0,indexN = 0;
        boolean found = false;
        String c = "";
    for(int i = 0; i<message.length() && !found;i++){
        c = String.valueOf(message.charAt(i));
        if(!found && c.equals("\"")){
            index0 = i+1;
            found  = true;
        }
    }
    found =false;
        for(int i = message.length()-1;i>=0 && !found ;i--){
            c = String.valueOf(message.charAt(i));
            if(!found && c.equals("\"")){
                indexN = i-1;
                found = true;
            }
        }
        return message.substring(index0,indexN);
    }
}
