package es.upm.etsisi.poo.ticket;

import java.io.Serializable;
import java.util.ArrayList;

import es.upm.etsisi.poo.MapDB.MapDBManager;
import es.upm.etsisi.poo.products.*;
import es.upm.etsisi.poo.enums.stateTicket;
import es.upm.etsisi.poo.enums.TicketType;
import es.upm.etsisi.poo.Utils;

public class TicketList <T extends Ticket> implements Serializable {

    private ArrayList<T> ticketList;

    private ArrayList<String> id ;// "clientId CashId"

    public TicketList(){
        ticketList = new ArrayList<>();
        id = new ArrayList<>();
    }

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

    public Ticket createTicket(String TicketId,String CashId,String clientId,TicketType type,MapDBManager mapDBManager){
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
            mapDBManager.addTicket(t);
            }
        else{
            System.out.println("Invalid TicketId");
            return null;
        }
        return t ;
    }

    private String createId(){
        String id;
        do {
            id =  String.valueOf(Utils.getRandomNumber(5));
        } while (!validId(id));
        return Utils.getTime() + "-" + id;
    }

    public boolean addTicket(Ticket ticket) {
        boolean added = false, exists = false;
            for(Ticket t : ticketList){
                if (ticket.getTicketId().equals(t.getTicketId())) {
                    exists = true;
                }
            }

            if (!exists ) {
                ticketList.add((T)ticket);
                added = true;
            } else
                System.out.println("The Ticket already exists");
        return added;
    }

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

    public Ticket getTicket(String id) {
        Ticket ticket = null;
        for (Ticket t : ticketList){
            if (t.getTicketId().contains(id)){
                ticket = t;
            }
        }
        return ticket;
    }

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
