package es.upm.etsisi.poo.controller;

import es.upm.etsisi.poo.MapDB.MapDBManager;
import es.upm.etsisi.poo.Utils;
import es.upm.etsisi.poo.ticket.TicketList;
import es.upm.etsisi.poo.user.Cash;
import es.upm.etsisi.poo.user.UserList;
import es.upm.etsisi.poo.validation.ValidationUser;

public class CashController extends  Controller{
    private final TicketList ticketList;
    private final UserList userList;
    private final ValidationUser userValidation;

    public CashController(MapDBManager mapDBManager) {
        super(mapDBManager);
        this.ticketList = mapDBManager.getTicketList();
        this.userList = mapDBManager.getUserList();
        this.userValidation = new ValidationUser();
    }

    public boolean addCash(String args){
        String[] message = args.split(" ");
        try {
            String name = Utils.getNameScanner(args);
            String email;
            String id = null;

            if (message.length >= 3 && message[0].startsWith("UW")) {
                id = message[0];
                email = message[message.length - 1];
            } else {
                email = message[message.length - 1];
            }

            String[] params = new String[]{name, email, id};

            /*if (!userValidation.validName(name) || !userValidation.validEmail(email)
                    || (id != null && !userValidation.validCashId(id))) {
                System.out.println("cash add: error");
                return false;
            }*/
            if(!userValidation.validate(params)){
                System.out.println("cash add: error");
                return false;
            }

            Cash cash = new Cash(name, email, id);

            boolean added = userList.addCash(cash);
            if (added) {
                mapDBManager.addUser(cash);
                System.out.println("cash add: ok");
            } else {
                System.out.println("cash add: error");
            }
        } catch (Exception e) {
            System.out.println("cash add: error");
        }
        return false;
    }

    public boolean removeCash(String args){

        boolean removed = false;
        String id = args;

        Cash cash  = (Cash) userList.getUserByID(id);
        if (cash != null){
            ticketList.removeTicket(cash.getId());
            removed = userList.removeUser(id);
        }

        if (removed) {
            mapDBManager.removeUser(id);
            System.out.println("cash remove: ok");
        } else System.out.println("cash remove: error");
        return removed;
    }

    public boolean listCash(){
        userList.printCashiers();
        System.out.println("cash list: ok");
        return true;
    }

    public boolean ticketsCash(String args){

        String cashId = args;
        boolean printed = false;

        if (userValidation.validCashId(cashId) && userList.containsId(cashId)) {
            TicketList ticketsOfCash = ticketList.getTicketsOfCash(cashId);
            System.out.println("Tickets:");
            System.out.println(ticketsOfCash.toString());
            System.out.println("cash tickets: ok");
            printed = true;
        }else{
            System.out.println("cash tickets:error");
        }
        return printed;
    }
}
