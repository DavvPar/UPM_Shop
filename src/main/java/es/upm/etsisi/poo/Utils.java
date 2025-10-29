package es.upm.etsisi.poo;

public class Utils {
    public String getNameScanner(String message){
        int index0 = 0,indexN = 0;
        boolean found = false;
    for(int i = 0; i<message.length();i++){
        if(!found && message.charAt(i) == '"'){
            index0 = i;
            found  = true;
        }
    }
    found =false;
        for(int i = message.length()-1;i<=0;i--){
            if(!found && message.charAt(i) == '"'){
                indexN = i;
                found = true;
            }
        }
        return message.substring(index0,indexN);
    }
}
