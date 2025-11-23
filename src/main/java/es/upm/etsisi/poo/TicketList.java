package es.upm.etsisi.poo;

import java.util.ArrayList;
import java.util.Comparator;

public class TicketList {
private ArrayList<Ticket> ticketList;

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
    if (!ticketList.isEmpty()){
        for (Ticket ticket : ticketList) {
            if (ticket.getTicketId().contains(id) || !id.matches("[0-9]+") || id.length()<5) {
                ok = false;
                break;
            }
        }
    }
    return ok;
}

    /**
     * method that returns a created ticket
     * @param TicketId
     * @param clientId
     * @param CashId
     * @return ticket
     */
    public Ticket createTicket(String TicketId,String CashId,String clientId){
        Ticket t = null;
        if (TicketId == null){
            t = new Ticket(createId(),CashId,clientId,stateTicket.empty);
        }else {
            if (ValidId(TicketId)){
            t = new Ticket(Utils.getTime("GMT+1") + "-" + TicketId, clientId, CashId,stateTicket.empty);
            }
            else{
                throw new IllegalArgumentException("invalid TicketId");
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
    String id = Utils.getTime("GMT+1") + "-"+Utils.getRandomNumber(5);
    while(!ValidId(id)){
        id =Utils.getTime("GMT+1") + "-"+Utils.getRandomNumber(5);
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

            if (!exists ) {
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
            if (t.getTicketId().equals(GetIdnumber(id))){
                ticket = t;
            }
        }
        return ticket;
    }
    private String GetIdnumber(String Id){
        String[] resul = Id.trim().split("-");
        String r = null;
        for (int i =0; i<resul.length;i++){
            if (resul[i].length()>=5 && resul[i].matches("[0-9]+")){
                r = resul[i];
            }
        }
        return r;
    }

    /**
     * Method for changing the ticket ID at the time of completion
     * @param ticket
     */
    public void CloseTicket(Ticket ticket){
        String NewId = ticket.getTicketId() +"-"+Utils.getTime("GMT+1");
        ticket.setTicketId(NewId);
        ticket.setState(stateTicket.closed);
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
