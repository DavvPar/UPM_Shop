package es.upm.etsisi.poo;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * UserList is a class created to manage all types of users in a list,
 * adding them, removing them and with some control methods for cashiers
 * and clients
 */
public class UserList {
    /**
     * List of users
     */
    private ArrayList<User> users;

    /**
     * Constructor of the class
     * No parameters, just creates the ArrayList
     */
    public UserList() {
        users = new ArrayList<>();
    }

    /**
     * Adds a client to the List
     * Name and email format validations must be done in main
     * before adding a client
     * @param client user
     */
    public boolean addClient(Client client) {
        boolean canAdd = true;
        boolean added = false;
        // Checks if id is in userlist
        if (containsId(client.getIdentifier())) {
            System.out.println("Already exists a client with DNI: " + client.getIdentifier());
            canAdd = false;
        }
        // Checks if email is in userlist
        if (containsEmail(client.getEmail()) && getUserByEmail(client.getEmail()) instanceof Cash){
            System.out.println("Already exists a cashier with email: " + client.getEmail());
            canAdd = false;
        }
        User cashier = getUserByID(client.getCashId());
        // Checks if cashier exists in userlist
        if (!(cashier instanceof Cash)) {
            System.out.println("No existing cashier with cashId: " + client.getCashId());
            canAdd = false;
        }
        if (canAdd){
            users.add(client);
            added = true;
            users.sort(Comparator.nullsLast(
                    Comparator.comparing(
                            User::getName,
                            String.CASE_INSENSITIVE_ORDER)));
            System.out.println(client.toString());
        }
        return added;
    }

    /**
     * Adds a cash to the List
     * Name and email format validations must be done in main
     * before adding a cashier
     * @param cash user
     */
    public boolean addCash(Cash cash) {
        boolean canAdd = true;
        boolean added = false;
        // Checks if id was given on creation, if not, gives it one
        if (cash.getIdentifier() == null) {
            cash.setCashId(generateUniqueCashId());
        // If it had id, checks if id is in userlist
        } else if (containsId(cash.getIdentifier())) {
            System.out.println("Already exists a cash with cashId: " + cash.getIdentifier());
            canAdd = false;
        }
        // Checks if email is in userlist
        if (containsEmail(cash.getEmail()) && getUserByEmail(cash.getEmail()) instanceof Client){
            System.out.println("Already exists a client with email: " + cash.getEmail());
            canAdd = false;
        }
        if(canAdd){
            users.add(cash);
            added = true;
            users.sort(Comparator.nullsLast(
                    Comparator.comparing(
                            User::getName,
                            String.CASE_INSENSITIVE_ORDER)));
            System.out.println(cash.toString());
        }
        return added;
    }

    /**
     * Removes user from the List
     * @param identifier DNI for Client, CashId for Cash
     */
    public boolean removeUser(String identifier) {
        User user = getUserByID(identifier);
        boolean b = false;
        if (user != null) {
            users.remove(user);
            b = true;
        }
        return b;
    }

    /**
     * Getter of the current number of users.
     * @return current number of users in the list
     */
    public int getClientsNum(){return users.size();}

    /**
     * Generates a new unique and valid cashId.
     * @return cashId
     */
    private String generateUniqueCashId() {
        String id;
        do {
            id = Utils.generateCashId();
        } while (getUserByID(id) != null);
        return id;
    }

    /**
     * Getter for a certain User, searching for its identifier
     * @param identifier DNI for Client, CashId for Cashiers
     * @return User with passed identifier
     */
    public User getUserByID(String identifier) {
        identifier = identifier.toUpperCase();
        for (User user : users) {
            if (user.getIdentifier().equals(identifier)) {
                return user;
            }
        }
        return null; //If not found
    }

    /**
     * Getter for a certain User, searching for its email
     * @param email email for all types of User
     * @return User with passed email
     */
    private User getUserByEmail(String email) {
        email = email.toUpperCase();
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Getter for all clients
     * @return ArrayList with only Clients
     */
    public ArrayList<Client> getClients() {
        ArrayList<Client> clients = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Client) {
                clients.add((Client) user);
            }
        }
        return clients;
    }

    /**
     * Getter for all cashiers
     * @return ArrayList with only Cashiers
     */
    public ArrayList<Cash> getCashiers() {
        ArrayList<Cash> cashiers = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Cash) {
                cashiers.add((Cash) user);
            }
        }
        return cashiers;
    }

    /**
     * @param id to check if is contained
     * @return true if there is already a user on the list with this id
     */
    boolean containsId(String id){
        return getUserByID(id) != null;
    }

    /**
     * @param email to check if is contained
     * @return true if there is already a user on the list with this email
     */
    boolean containsEmail(String email){
        return getUserByEmail(email) != null;
    }

    /**
     * Prints only the clients from the List
     */
    public void printClients() {
        System.out.println("Clients:");
        for (User user : users) {
            if (user instanceof Client) {
                System.out.println(user);
            }
        }
    }

    /**
     * Prints only the cashiers from the List
     */
    public void printCashiers() {
        System.out.println("Cashiers:");
        for (User user : users) {
            if (user instanceof Cash) {
                System.out.println(user);
            }
        }
    }

    /**
     * Prints all the users in the List
     */
    public void printUsers() {
        for (User user : users) {
            System.out.println(user);
        }
    }
}
