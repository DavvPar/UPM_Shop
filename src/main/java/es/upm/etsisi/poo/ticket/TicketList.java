package es.upm.etsisi.poo.ticket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import es.upm.etsisi.poo.MapDB.MapDBManager;
import es.upm.etsisi.poo.products.*;
import es.upm.etsisi.poo.enums.stateTicket;
import es.upm.etsisi.poo.enums.TicketType;
import es.upm.etsisi.poo.Utils;

public class TicketList <T extends Ticket<Product>>  {

    private HashMap<String,HashMap<String,Object>> ticketList;

    public TicketList(){
        ticketList = new HashMap<>();
    }

    private boolean validId(String id){
        if (id == null) return false;
        if (!id.matches("[0-9]+") || id.length() < 5) return false;
        for (HashMap<String,Object> ticket : ticketList.values() ) {
            String IdTicket = ((Ticket)ticket.get("ticket")).getTicketId();
            if (IdTicket.equals(id)){
            return false;
            }
        }
        return true;
    }

    public Ticket createTicket(String TicketId,String CashId,String clientId,TicketType type,MapDBManager mapDBManager){
        Ticket t;
        HashMap<String,Object> ticket = new HashMap<>();
        if (TicketId == null){
            ticket.put("opentime",Utils.getTime());
            TicketId = createId();
        }
        if (validId(TicketId)){
            if (type ==TicketType.Client) {
                t = new TicketClient(TicketId, stateTicket.empty, type);
            }else {
                t= new TicketBusiness(TicketId,stateTicket.empty,type);
            }
            ticket.put("ticket",t);
            ticket.put("Cashid",CashId);
            ticket.put("clientId",clientId);
            ticketList.put(TicketId,ticket);
            addTicket(ticket);
            mapDBManager.addTicket(ticket);
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
        return id;
    }

    public boolean addTicket(HashMap<String,Object> ticket) {
        boolean added = false;
        String id = ((Ticket)ticket.get("ticket")).getTicketId();
        if (ticketList.get(id) == null){
            added = true;
            ticketList.put(id,ticket);
        }
        return added;
    }

    public boolean removeTicket(String cashid) {
        boolean removed = false;
        for(HashMap<String, Object> ticketData : ticketList.values()) {
            String ticketCashId = (String) ticketData.get("cashId");
            if (cashid.equals(ticketCashId)) {
                ticketList.remove(ticketData);
                removed = true;
            }
        }
        return removed;
    }

    public Ticket getTicket(String id) {
        return (Ticket) ticketList.get(id).get("ticket");
    }
    public HashMap<String,Object> getTicketFull(String id){return ticketList.get(id);}
    public void CloseTicket(Ticket ticket,String date){
        if (ticket.getState() != stateTicket.closed){
            HashMap<String,Object> ticketDate = ticketList.get(ticket.getTicketId());
        ticketDate.put("closetime",date);
        ticket.setState(stateTicket.closed);
        }
    }

    public TicketList getTicketsOfCash(String cashId){
        TicketList ticketsOfCash = new TicketList();
        for(HashMap<String, Object> ticketData : ticketList.values()){
            String ticketCashId = (String) ticketData.get("cashId");
            if (cashId.equals(ticketCashId)) {
            if(ticketCashId.equals(cashId)){
                ticketsOfCash.addTicket(ticketData);
            }
            }
        }
        return ticketsOfCash;
    }

    public String toString(){
        String text = "";
        for (HashMap<String,Object> ticket :ticketList.values()){
            Ticket t = (Ticket) ticket.get("ticket");
            String state = String.valueOf(t.getState());
            text += t.getTicketId() + " - " + state.toUpperCase() + "\n";
        }
        return text;
    }
}
