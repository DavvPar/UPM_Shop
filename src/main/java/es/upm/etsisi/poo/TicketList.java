package es.upm.etsisi.poo;

import java.util.ArrayList;
import java.util.Comparator;

public class TicketList {
    /**
     * Array of tickets to create the list of tickets
     */
    private ArrayList<Ticket> ticketList;
    private ArrayList<String> id ;// "clientId CashId"

    /**
     * Constructor of the class
     * No parameters
     */
    public TicketList(){
        ticketList = new ArrayList<>();
        id = new ArrayList<>();
    }

    /**
     * Checks if passed ID is valid
     * @param id ticket id to check validness
     * @return true if valid id, false if not
     */
    private boolean validId(String id){
        id = Utils.getShortId(id);
        if (id == null) return false;
        if (!id.matches("[0-9]+") || id.length() < 5) return false;
        for (Ticket current : ticketList) {
            if (Utils.getShortId(current.getTicketId()).equals(id)) {
                return false;
            }
        }
        return true;
    }

    /**
     * A way to create tickets, similar to a ticket constructor
     * @param TicketId id for the new ticket, can be either null (creates new)
     *                 or a valid one, already created
     * @param clientId client that buys the ticket
     * @param CashId cashier that operates the ticket
     * @return ticket
     */
    public Ticket createTicket(String TicketId,String CashId,String clientId,TicketType type){
        Ticket t;
        if (TicketId == null){
            TicketId = createId();
        }else{
            TicketId = Utils.getTime() + "-" +TicketId;
        }
        if (validId(TicketId)){
            if (type ==TicketType.Client) {
                t = new TicketClient(TicketId, stateTicket.empty, type);
            }else {
                t= new TicketBusiness(TicketId,stateTicket.empty,type);
            }
            id.add(clientId+" "+CashId);
            addTicket(t);
            }
        else{
            System.out.println("Invalid TicketId");
            return null;
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
        return Utils.getTime() + "-" + id;
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
        int i =0;
        while (i<ticketList.size()) {
            String[] Id= id.get(i).split(" ");// clientId CashId
            if (Id[1].equals(cashid)) {
                ticketList.remove(i);
                removed = true;
            }else{
                i++;
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

    public TicketList getTicketsOfCash(String cashId){
        TicketList ticketsOfCash = new TicketList();
        for(int i =0;i<ticketList.size();i++){
            String[] IDs = id.get(i).split(" ");
            if(IDs[1].equals(cashId)){
                ticketsOfCash.addTicket(ticketList.get(i));
            }
        }
        return ticketsOfCash;
    }

    public String toString(){
        String text = "";
        for (int i = 0; i < ticketList.size(); i++){
            Ticket t = ticketList.get(i);
            String state = String.valueOf(t.getState());
            text += t.getTicketId() + " - " + state.toUpperCase() + "\n";
        }
        return text;
    }
}
