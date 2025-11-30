package es.upm.etsisi.poo;

import java.util.ArrayList;
import java.util.Comparator;

public class TicketList {
    /**
     * Array of tickets to create the list of tickets
     */
    private ArrayList<Ticket> ticketList;

    /**
     * Constructor of the class
     * No parameters
     */
    public TicketList(){
        ticketList = new ArrayList<>();
    }

    /**
     * Checks if passed ID is valid
     * @param id ticket id to check validness
     * @return true if valid id, false if not
     */
    private boolean validId(String id){
        boolean ok = true;
        if (!ticketList.isEmpty()){
            for (Ticket ticket : ticketList) {
                if (ticket.getTicketId().contains(id) || !id.matches("[0-9]+") || id.length()<5) {
                    ok = false;
                }
            }
        }
        return ok;
    }

    /**
     * A way to create tickets, similar to a ticket constructor
     * @param TicketId id for the new ticket, can be either null (creates new)
     *                 or a valid one, already created
     * @param clientId client that buys the ticket
     * @param CashId cashier that operates the ticket
     * @return ticket
     */
    public Ticket createTicket(String TicketId,String CashId,String clientId){
        Ticket t = null;
        if (TicketId == null){
            String newId = createId();
            t = new Ticket(newId,CashId,clientId,stateTicket.empty);
            addTicket(t);
        }else {
            if (validId(TicketId)){
            t = new Ticket(Utils.getTime("GMT+1") + "-" + TicketId, clientId, CashId,stateTicket.empty);
            addTicket(t);
            }
            else{
                System.out.println("Invalid TicketId");
                return null;
            }
        }
        return t ;
    }

    /**
     * Creates a ticketId (for tickets with null id) with valid format
     * @return valid id to assign
     */
    private String createId(){
        String id;
        do {
            id =  String.valueOf(Utils.getRandomNumber(5));
        } while (!validId(id));
        String finalId = Utils.getTime("GMT+1") + "-" + id;
        return finalId;
    }

    /**
     * Adds a new ticket to the ticket list
     * @param ticket ticked added
     * @return true if success, false if failed
     */
    public boolean addTicket(Ticket ticket) {
        boolean added = false, exists = false;
            for(Ticket t : ticketList){
                if (ticket.getTicketId().equals(t.getTicketId())) {
                    exists = true;
                }
            }

            if (!exists ) {
                ticketList.add(ticket);
                added = true;
            } else
                System.out.println("The Ticket already exists");
        return added;
    }

    /**
     * Deletes all the tickets created with passed cashId
     * @param cashid cashId that will have its tickets removed
     * @return returns true if deleted, false if not found ticket/cashId
     */
    public boolean removeTicket(String cashid) {
        boolean removed = false;
        for (Ticket ticket: ticketList) {
            String CashId= ticket.getCashId();
            if (CashId.equals(cashid)) {
                ticketList.remove(ticket);
                removed = true;
            }
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
    public void CloseTicket(Ticket ticket,String date){
        if (ticket.getState() != stateTicket.closed){
        String NewId = ticket.getTicketId() +"-"+date;
        ticket.setTicketId(NewId);
        ticket.setState(stateTicket.closed);}

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

    public String toString(){
        String text = "Ticket list:\n";
        for (int i = 0; i < ticketList.size()-1; i++){
            Ticket t = ticketList.get(i);
            text += t.getTicketId() + " - " + t.getState();
        }
        return text;
    }
}
