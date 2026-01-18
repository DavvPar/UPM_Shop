package es.upm.etsisi.poo.user;

import java.util.ArrayList;
import java.util.Comparator;
import es.upm.etsisi.poo.Utils;

public class UserList {

    private ArrayList<User> users;

    public UserList() {
        users = new ArrayList<>();
    }

    public boolean addClient(Client client) {
        boolean added = false;
        if (availableClient(client)) {
            addUserOrdered(client);
            added = true;
        } else {
            System.out.println("client add: error");
        }
        return added;
    }

    public boolean addCash(Cash cash) {
        boolean added = false;
        if (cashIdNull(cash.getId())) {
            cash.setId(generateUniqueCashId());
        }
        if (availableCash(cash)) {
            addUserOrdered(cash);
            added = true;
        } else {
            System.out.println("cash add: error");
        }
        return added;
    }



    public void addUserOrdered(User user) {
        users.add(user);
        users.sort(Comparator.nullsLast(
                Comparator.comparing(
                        User::getName,
                        String.CASE_INSENSITIVE_ORDER)));
        System.out.println(user.toString());
    }

    private boolean isIdAvailable(String id){
        return !containsId(id);
    }

    private boolean isEmailAvailableFor(Class<?> clazz, String email) {
        User user = getUserByEmail(email);
        return user == null || clazz.isInstance(user);
    }

    private boolean availableClient(Client client){
        return (isIdAvailable(client.getId()) &&
                isEmailAvailableFor(Client.class, client.getEmail())
                && validateClientCashId(client.getCashId()));
    }


    private boolean validateClientCashId(String cashId) {
        User user = getUserByID(cashId);
        return user instanceof Cash;
    }

    private boolean availableCash(Cash cash){
        return (isIdAvailable(cash.getId()) && isEmailAvailableFor(Cash.class, cash.getEmail()));
    }


    private boolean cashIdNull(String cashId) {
        return (cashId == null);
    }

    public boolean removeUser(String identifier) {
        User user = getUserByID(identifier);
        boolean removed = false;
        if (user != null) {
            users.remove(user);
            removed = true;
        }
        return removed;
    }

    private int getClientsNum(){return users.size();}

    private String generateUniqueCashId() {
        String id;
        do {
            id = Utils.generateCashId();
        } while (getUserByID(id) != null);
        return id;
    }

    public User getUserByID(String identifier) {
        identifier = identifier.toUpperCase();
        for (User user : users) {
            if (user.getId().equals(identifier)) {
                return user;
            }
        }
        return null; //If not found
    }

    private User getUserByEmail(String email) {
        email = email.toUpperCase();
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }


    public boolean containsId(String id){
        return getUserByID(id) != null;
    }

    public boolean containsEmail(String email){
        return getUserByEmail(email) != null;
    }

    public void printClients() {
        System.out.println("Clients:");
        for (User user : users) {
            if (user instanceof Client) {
                System.out.println("  " + user);
            }
        }
    }

    public void printCashiers() {
        System.out.println("Cashiers:");
        for (User user : users) {
            if (user instanceof Cash) {
                System.out.println("  " + user);
            }
        }
    }

    public void printUsers() {
        for (User user : users) {
            System.out.println(user);
        }
    }
}
