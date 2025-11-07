package es.upm.etsisi.poo;

//import java.util.ArrayList;

public class ClientList {

    //private static ArrayList<Client> clientList = new ArrayList<>();

    private Client[] clients;
    /**
     * Current number of clients
     */
    private int MaxNumClients;
    int clientNum;
    //TODO pasarlo a arrayList
    public ClientList(){
        clients = new Client[MaxNumClients];
        clientNum = 0;
    }

    public int getClientsNum(){return clientNum;}

    public boolean addClients(Client client) {
        boolean added = false, exists = false;
        if (clientNum < MaxNumClients) {
            for (int i = 0; i < clientNum; i++) {
                if (client.getDNI().equals(clients[i].getDNI())) {
                    exists = true;
                }
            }
            if (!exists) {
                clients[clientNum] = client;
                clientNum++;
                added = true;
            } else
                System.out.println("The clients already exists");
        } else
            System.out.println("No further clients can be added");
        return added;
    }

    public boolean removeClients(Client selected) {
        boolean removed = false;
        for (int i = 0; i < clientNum; i++) {
            if (clients[i].getDNI().equals(selected.getDNI())) {
                for (int j = i + 1; j < clientNum; j++) {
                    clients[j - 1] = clients[j];
                }
                clientNum--;
                clients[clientNum] = null;
                removed = true;
            }
        }
        return removed;
    }
}
