package es.upm.etsisi.poo;

import java.util.ArrayList;

public class TicketList {
private ArrayList<Ticket> ticketList;
private ArrayList<String> ticketid;
private Utils utils;
public TicketList(){
    ticketList = new ArrayList<>();
}
private boolean ValidId(String id){
    boolean ok = true;
    for(String string:ticketid){
        if (string.equals(id)){
            ok = false;
        }
    }
    return ok;
}
private String createId(){
    String id = utils.getTime("GMT+1") + "-"+utils.getRandomNumber(5);
    while(!ValidId(id)){
        id =utils.getTime("GMT+1") + "-"+utils.getRandomNumber(5);
    }
    return id;
}
    public boolean addTicket(Ticket ticket) {
        boolean added = false, exists = false;
            for(Ticket t : ticketList){
                if (ticket.getTicketId().equals(t.getTicketId())) {
                    exists = true;
                }
            }

            if (!exists) {
                ticketList.add(ticket);
                added = true;
            } else
                System.out.println("The Ticket already exists");
        return added;
    }
}
