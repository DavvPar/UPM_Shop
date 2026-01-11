package es.upm.etsisi.poo.controller;

import es.upm.etsisi.poo.Utils;
import es.upm.etsisi.poo.enums.ClientType;
import es.upm.etsisi.poo.user.Client;
import es.upm.etsisi.poo.user.UserList;
import es.upm.etsisi.poo.user.UserValidator;

public class ClientController {
    private final UserList userList;

    public ClientController(UserList userList) {
        this.userList = userList;
    }

    public boolean addClient(String args){
        String[] message = args.split(" ");
        try {
            String fullLine = String.join(" ", message);
            String name = Utils.getNameScanner(fullLine);
            String[] rightParts = secondPartArray(fullLine);

            if (rightParts.length < 3) {
                System.out.println("client add: error");
                return false;
            }
            String dni = rightParts[0];
            String email = rightParts[1];
            String cashId = rightParts[2];

            if (!UserValidator.validName(name)) {
                System.out.println("client add: error name");
                return false;
            }
            if (!UserValidator.validEmail(email)) {
                System.out.println("client add: error email");
                return false;
            }
            if (!UserValidator.validCashId(cashId)) {
                System.out.println("client add: error CashId");
                return false;
            }
            Client c = null;
            if (UserValidator.validNIF(dni)){
                c = new Client(name, dni, email, cashId, ClientType.Business);
            }
            if (UserValidator.validDNI(dni) ||UserValidator.validNIE(dni)){
                c = new Client(name,dni,email,cashId,ClientType.Client);
            }
            if (c != null){
                if (userList.addClient(c)) {
                    System.out.println("client add: ok");
                }
                else {
                    System.out.println("client add: error");
                }
            }else{
                System.out.println("ClientID error,Incorrect clientID only accepts: DNI,NIE,NIF");
            }
        } catch (Exception e) {
            System.out.println("client add: error");
        }
        return false;
    }

    private String[] secondPartArray(String input){
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

    public boolean removeClient(String args){
        String[] message = args.split(" ");
        if (message.length < 3) {
            System.out.println("client remove: error");
            return false;
        }
        String dni = message[2];
        boolean removed = userList.removeUser(dni);
        if (removed) {
            System.out.println("client remove: ok");
        } else {
            System.out.println("client remove: error");
        }
        return false;
    }

    public boolean listClient(){
        userList.printClients();
        System.out.println("client list: ok");
        return true;
    }
}
