package es.upm.etsisi.poo;

import java.util.ArrayList;
import java.util.Comparator;

public class UserList {

    private ArrayList<User> users;

    public UserList() {
        users = new ArrayList<>();
    }

    public boolean addClient(Client client) {
        boolean added = false;
        if (validateClient(client)) {
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
        if (validateCash(cash)) {
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

    public boolean validateClient(Client client){
        return (validateClientId(client.getId()) && validateClientEmail(client.getEmail()) && validateClientCashId(client.getCashId()));
    }
    public boolean validateClientId(String clientId) {
        return !(containsId(clientId));
    }
    public boolean validateClientEmail(String email) {
        return !(containsEmail(email) && getUserByEmail(email) instanceof Cash);
    }
    public boolean validateClientCashId(String cashId) {
        User user = getUserByID(cashId);
        return user instanceof Cash;
    }

    public boolean validateCash(Cash cash){
        return (validateCashId(cash.getId()) && validateCashEmail(cash.getEmail()));
    }
    public boolean validateCashId(String cashId) {
        return !(containsId(cashId));
    }
    public boolean validateCashEmail(String email) {
        return !(containsEmail(email) && getUserByEmail(email) instanceof Client);
    }
    public boolean cashIdNull(String cashId) {
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

    public int getClientsNum(){return users.size();}

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

    public ArrayList<Client> getClients() {
        ArrayList<Client> clients = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Client) {
                clients.add((Client) user);
            }
        }
        return clients;
    }

    public ArrayList<Cash> getCashiers() {
        ArrayList<Cash> cashiers = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Cash) {
                cashiers.add((Cash) user);
            }
        }
        return cashiers;
    }

    boolean containsId(String id){
        return getUserByID(id) != null;
    }

    boolean containsEmail(String email){
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
