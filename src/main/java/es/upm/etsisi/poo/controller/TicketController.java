package es.upm.etsisi.poo.controller;

import es.upm.etsisi.poo.Utils;
import es.upm.etsisi.poo.enums.ProductType;
import es.upm.etsisi.poo.enums.TicketType;
import es.upm.etsisi.poo.enums.stateTicket;
import es.upm.etsisi.poo.products.*;
import es.upm.etsisi.poo.products.ProductList;
import es.upm.etsisi.poo.ticket.Ticket;
import es.upm.etsisi.poo.ticket.TicketList;
import es.upm.etsisi.poo.user.UserList;

public class TicketController {

    private final TicketList ticketList;

    public final ProductList productList;

    private final UserList userList;

    private final ProductController productController;//esto es por valid time

    private Ticket currentTicket;

    public TicketController(TicketList ticketList, ProductList productList, UserList userList, ProductController productController) {
        this.ticketList = ticketList;
        this.productList = productList;
        this.userList = userList;
        this.productController = productController;
    }

    public boolean addTicket(String args){
        String[] message = args.split(" ");

        try {
            String custom = "";
            int quantity;

            currentTicket = ticketList.getTicket(message[0]);
            String cashId = message[1];
            String id = message[2];

            Product p = productList.getProduct(id);

            if (!userList.containsId(cashId)) {
                System.out.println("ticket or cashID not found");
                return false;
            }

            if (p.getProductType() != ProductType.Service) {
                quantity = Integer.parseInt(message[3]);

                if (p.getProductType() == ProductType.ProductPersonalized
                        || p.getProductType() == ProductType.Product) {

                    for (int i = 4; i < message.length; i++) {
                        custom += message[i];
                    }

                } else if (p.getProductType() == ProductType.Meeting
                        || p.getProductType() == ProductType.Food) {

                    EventProduct product = (EventProduct) p;
                    product.setPeople(quantity);

                    if (quantity > product.getMAX_PEOPLE()) {
                        System.out.println("have exceeded the maximum number of people allowed");
                        return false;
                    }
                    quantity = 1;
                }
            } else {
                quantity = 1;
            }

            if (currentTicket.getState() == stateTicket.closed) {
                System.out.println("ticket closed");
                return false;
            }

            currentTicket.addProductToTicket(productList, id, quantity, custom);
            System.out.println(currentTicket);
            System.out.println("ticket add: ok");
            return true;

        } catch (Exception e) {
            System.out.println("inappropriate format");
            System.out.println("ticket add <ticketID> <CashId> <id> <quantity>");
            return false;
        }
    }

    public boolean printTicket(String args){
        String[] message = args.split(" ");

        try {
            currentTicket = ticketList.getTicket(message[0]);
            String cashId = message[1];

            if (!userList.containsId(cashId) || currentTicket == null) {
                System.out.println("ticket or cashID not found");
                return false;
            }

            for (int i = 0; i < currentTicket.getNumProductInTicket(); i++) {
                Product p = currentTicket.getProduct(i);

                if (p.getProductType() == ProductType.Food
                        || p.getProductType() == ProductType.Meeting) {

                    EventProduct product = (EventProduct) p;
                    String date = product.getExpirationDate();

                    if (!productController.validatePlanningTime(p.getProductType(), date)) {
                        currentTicket.removeProduct(p.getID());
                    }
                }
            }

            ticketList.CloseTicket(currentTicket, Utils.getTime());
            System.out.println(currentTicket);
            System.out.println("ticket print: ok");
            return true;

        } catch (Exception e) {
            System.out.println("ticket print: fail");
            return false;
        }
    }

    public boolean removeTicket(String args){
        String[] message = args.split(" ");

        if (message.length != 3) {
            System.out.println("ticket remove <ticketId> <cashId> <prodId>");
            return false;
        }

        try {
            currentTicket = ticketList.getTicket(message[0]);
            String cashId = message[1];
            String id = message[2];

            if (!userList.containsId(cashId) || currentTicket == null) {
                System.out.println("ticket or cashID not found");
                return false;
            }

            if (currentTicket.getState() == stateTicket.closed) {
                System.out.println("ticket closed");
                return false;
            }

            currentTicket.removeProduct(id);
            System.out.println(currentTicket);
            System.out.println("ticket remove: ok");
            return true;

        } catch (Exception e) {
            System.out.println("inappropriate format");
            return false;
        }
    }

    public boolean listTicket(){ //TODO RETURN TRUE?
        System.out.println("Ticket list:");
        System.out.print(ticketList.toString());
        System.out.println("ticket list: ok");
        return true;
    }

    public boolean newTicket(String args){ //MAL COPIADO CREO
        String[] input = args.split("-");
        String[] firstPart = input[0].trim().split(" ");

        String typeTicket = input.length > 1 ? input[1] : "";

        if (!userList.containsId(firstPart[firstPart.length - 1])
                || !userList.containsId(firstPart[firstPart.length - 2])) {

            System.out.println("Incorrect format or IDs");
            return false;
        }

        TicketType type = Utils.TypeTicket(firstPart[firstPart.length - 1], typeTicket);

        if (firstPart[0].matches("[0-9]+") && firstPart[0].length() >= 5) {
            currentTicket = ticketList.createTicket(firstPart[0], firstPart[1], firstPart[2], type);
        } else {
            currentTicket = ticketList.createTicket(null, firstPart[0], firstPart[1], type);
        }

        currentTicket.setState(stateTicket.empty);
        System.out.println(currentTicket);
        System.out.println("ticket new: ok");
        return true;
    }
}
