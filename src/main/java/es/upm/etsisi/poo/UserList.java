package es.upm.etsisi.poo;

import java.util.ArrayList;

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
     * Name and email validations must be done in main
     * @param client user
     */
    public boolean addClient(Client client) {
        boolean canAdd = true;
        boolean added = false;
        if (getUser(client.getIdentifier()) != null) {
            System.out.println("Already exists a client with DNI: " + client.getIdentifier());
            canAdd = false;
        }
        User cashier = getUser(client.getCashId());
        if (!(cashier instanceof Cash)) {
            System.out.println("No existing cashier with cashId: " + client.getCashId());
            canAdd = false;
        }
        if (canAdd){
            users.add(client);
            added = true;
            System.out.println(client.toString());
        }
        return added;
    }

    /**
     * Generates a new unique and valid cashId.
     * @return cashId
     */
    private String generateUniqueCashId() {
        String id;
        do {
            id = Utils.generateCashId();
        } while (getUser(id) != null);
        return id;
    }

    /**
     * Adds a cash to the List
     * Name and email validations must be done in main
     * @param cash user
     */
    public boolean addCash(Cash cash) {
        boolean canAdd = true;
        boolean added = false;
        if (cash.getIdentifier() == null) {
            cash.setCashId(generateUniqueCashId());
        } else if (getUser(cash.getIdentifier()) != null) {
            System.out.println("Already exists a cash with DNI: " + cash.getIdentifier());
            canAdd = false;
        }
        if(canAdd){
            users.add(cash);
            added = true;
            System.out.println(cash.toString());
        }
        return added;
    }

    /**
     * Removes user from the List
     * @param identifier DNI for Client, CashId for Cash
     */
    public boolean removeUser(String identifier) {
        User user = getUser(identifier);
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
     * Getter for a certain user, searching for its identifier
     * @param identifier DNI for Client, CashId for Cashiers
     * @return User with said identifier
     */
    public User getUser(String identifier) {
        for (User user : users) {
            if (user.getIdentifier().equals(identifier)) {
                return user;
            }
        }
        return null; //If not found
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
