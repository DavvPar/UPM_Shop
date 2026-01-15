package es.upm.etsisi.poo.validation;

import es.upm.etsisi.poo.products.Product;
import es.upm.etsisi.poo.ticket.Ticket;
import es.upm.etsisi.poo.user.UserList;

public class ValidationTicket implements Validator<Ticket<Product>> {
    private UserList userList;

    public void validate(Ticket<Product> ticket) {

        if (!userList.containsId(cashId)) {
            System.out.println("ticket or cashID not found");
            return false;
        }
    }
}
