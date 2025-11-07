package es.upm.etsisi.poo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class TicketList {
private ArrayList<Ticket> ticketList;
private Utils utils;
public TicketList(){
    ticketList = new ArrayList<>();
}

    /**
     * method that checks if the ID is valid
     * @param id
     * @return true if id is valid and false if not
     */
    private boolean ValidId(String id){
    boolean ok = true;
        for (Ticket ticket : ticketList) {
            if (ticket.getTicketId().contains(id) && id.length() != 5) {
                ok = false;
                break;
            }
        }
    return ok;
}
    public Ticket createTicket(String TicketId,String clientId,String CashId){
        Ticket t = null;
        if (TicketId ==null){
            t = new Ticket(createId(),CashId,clientId);
        }else {
            if (ValidId(TicketId)){
            t = new Ticket(utils.getTime("GMT+1") + "-" + TicketId, clientId, CashId);
            }
        }
        if(t != null){
        addTicket(t);
        }
        return t ;
    }
    /**
     * method that creates an ID to assign
     * @return return a valid id to assign
     */
    private String createId(){
    String id = utils.getTime("GMT+1") + "-"+utils.getRandomNumber(5);
    while(!ValidId(id)){
        id =utils.getTime("GMT+1") + "-"+utils.getRandomNumber(5);
    }
    return id;
}

    /**
     * method that adds a ticket to the ticket list
      * @param ticket
     * @return success or failure
     */
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

    /**
     *method that deletes the ticket from the list
     * @param id
     * @return returns whether it was deleted or not;
     * if false, the ticket searched for is not found in the list
     */
    public boolean removeTicket(String id) {
        boolean removed = false;
        Ticket ticket = getTicket(id);
        if (ticketList.contains(ticket)){
            ticketList.remove(ticket);
            removed = true;
        }
        return removed;
    }

    /**
     *method that returns the ticket according to its ID
     * @param id ticket id
     * @return return null if it does not exist and ticket if it does
     */
    public Ticket getTicket(String id) {
        Ticket ticket = null;
        for (Ticket t : ticketList){
            if (t.getTicketId().contains(id)){
                ticket = t;
            }
        }
        return ticket;
    }

    /**
     * Method for changing the ticket ID at the time of completion
     * @param ticket
     */
    public void SetId(Ticket ticket){
        int IndexO = 0;
        boolean found= false;
        removeTicket(ticket.getTicketId());
        for (int i = ticket.getTicketId().length()-1;i>=0;i--){
            if (ticket.getTicketId().charAt(i) == '-' && !found){
                IndexO = i;
                found = true;
            }
        }
        String NewId = utils.getTime("GMT+1") + ticket.getTicketId().substring(IndexO);
        ticket.setTicketId(NewId);
        addTicket(ticket);
    }

    /**
     * method that returns all tickets according to the cashId
     * @param cashId
     * @return tickets according to be cashId
     */
    public ArrayList<Ticket> getTicketofCashId(String cashId){
        ArrayList<Ticket> t = new ArrayList<>();
        for (Ticket ticket: ticketList){
            if (ticket.getCashId().equals(cashId)){
                t.add(ticket);
            }
        }
        t.sort(Comparator.nullsLast(
                Comparator.comparing(
                        Ticket::getCashId,
                        String.CASE_INSENSITIVE_ORDER)
                                    )
                );
        return t;
    }
}
