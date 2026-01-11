package es.upm.etsisi.poo.controller;

import es.upm.etsisi.poo.Utils;
import es.upm.etsisi.poo.ticket.TicketList;
import es.upm.etsisi.poo.user.Cash;
import es.upm.etsisi.poo.user.UserList;
import es.upm.etsisi.poo.user.UserValidator;

public class CashController { //TODO revisar los returns de los metodos
    private final TicketList ticketList;
    private final UserList userList;

    public CashController(TicketList ticketList, UserList userList) {
        this.ticketList = ticketList;
        this.userList = userList;
    }

    public boolean addCash(String args){
        String[] message = args.split(" ");
        try {
            String fullLine = String.join(" ", message);
            String name = Utils.getNameScanner(fullLine);
            //Never used: String[] rightParts = secondPartArray(fullLine);
            String email;
            String id = null;

            if (message.length >= 4 && message[2].startsWith("UW")) {
                id = message[2];
                email = message[message.length - 1];
            } else {
                email = message[message.length - 1];
            }

            if (!UserValidator.validName(name) || !UserValidator.validEmail(email) || (id != null && !UserValidator.validCashId(id))) {
                System.out.println("cash add: error");
                return false;
            }

            Cash cash = new Cash(name, email, id);

            boolean added = userList.addCash(cash);
            if (added) {
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
        String[] message = args.split(" ");
        boolean removed = false;
        if (message.length < 3) {
            System.out.println("cash remove: error");
            return false;
        }
        String id = message[2];
        Cash cash  = (Cash) userList.getUserByID(id);
        if (cash != null){
            ticketList.removeTicket(cash.getId());
            removed = userList.removeUser(id);
        }
        if (removed) System.out.println("cash remove: ok");
        else System.out.println("cash remove: error");
        return removed;
    }

    public boolean listCash(){
        userList.printCashiers();
        System.out.println("cash list: ok");
        return true;
    }

    public boolean ticketsCash(String args){
        String[] message = args.split(" ");
        if (message.length < 3) {
            System.out.println("cash tickets: error");
            return false;
        }
        String cashier = message[2];
        if (UserValidator.validCashId(cashier) && userList.containsId(cashier)) {
            TicketList ticketsOfCash = ticketList.getTicketsOfCash(cashier);
            System.out.println("Tickets:");
            System.out.println(ticketsOfCash.toString());
            System.out.println("cash tickets: ok");
        }else{
            System.out.println("cash tickets:error");
        }
        return true;
    }
}
