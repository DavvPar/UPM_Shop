package es.upm.etsisi.poo;

public class ClientsList {
    private Clients[] clients;
    int MaxNumClients;
    int ClientsNum;
    public ClientsList(int MaxNumclients){
        clients = new Clients[MaxNumclients];
        this.MaxNumClients = MaxNumclients;
        ClientsNum = 0;
    }

    public int getClientsNum(){return ClientsNum;}

    public boolean addClients(Clients client) {
        boolean added = false, exists = false;
        if (ClientsNum < MaxNumClients) {
            for (int i = 0; i < ClientsNum; i++) {
                if (client.getCashId() ==clients[i].getCashId()) {
                    exists = true;
                }
            }
            if (!exists) {
                clients[ClientsNum] = client;
                ClientsNum++;
                added = true;
            } else
                System.out.println("The clients already exists");
        } else
            System.out.println("No further clients can be added");
        return added;
    }

    public boolean removeClients(Clients selected) {
        boolean removed = false;
        for (int i = 0; i < ClientsNum; i++) {
            if (clients[i].getCashId() == selected.getCashId()) {
                for (int j = i + 1; j < ClientsNum; j++) {
                    clients[j - 1] = clients[j];
                }
                ClientsNum--;
                clients[ClientsNum] = null;
                removed = true;
            }
        }
        return removed;
    }
}
