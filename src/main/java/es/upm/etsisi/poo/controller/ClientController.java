package es.upm.etsisi.poo.controller;

import es.upm.etsisi.poo.MapDB.MapDBManager;
import es.upm.etsisi.poo.Utils;
import es.upm.etsisi.poo.enums.ClientType;
import es.upm.etsisi.poo.user.Client;
import es.upm.etsisi.poo.user.UserList;
import es.upm.etsisi.poo.validation.ValidationUser;

public class ClientController extends Controller{

    private final UserList userList;
    private final ValidationUser userValidator;

    public ClientController(MapDBManager mapDBManager) {
        super(mapDBManager);
        this.userList = mapDBManager.getUserList();
        this.userValidator = new ValidationUser();
    }

    public boolean addClient(String args){
        String[] message = args.split(" ");
        try {
            String name = Utils.getNameScanner(args);

            if (message.length < 3) {
                System.out.println("client add: error");
                return false;
            }
            String dni = message[message.length-3];
            String email = message[message.length-2];
            String cashId = message[message.length-1];

            String[] params = new String[]{name, email, cashId};
            Client c = null;
            if (userValidator.validNIF(dni) && userValidator.validate(params)){
                c = new Client(name, dni, email, cashId, ClientType.Business);
            }
            if (userValidator.validDNI(dni) ||userValidator.validNIE(dni) && userValidator.validate(params)){
                c = new Client(name,dni,email,cashId,ClientType.Client);
            }
            if (c != null){
                if (userList.addClient(c)) {
                    mapDBManager.addUser(c);
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


    public boolean removeClient(String args){
        String dni = args;

        boolean removed = userList.removeUser(dni);
        if (removed) {
            mapDBManager.removeUser(dni);
            System.out.println("client remove: ok");
        } else {
            System.out.println("client remove: error");
        }
        return removed;
    }

    public boolean listClient(){
        userList.printClients();
        System.out.println("client list: ok");
        return true;
    }
}
