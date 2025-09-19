package repository;

import domain.Client;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientRepository {
    private List<Client> clients = new ArrayList<>();
    
    public ClientRepository() {
        this.clients = new ArrayList<>();
    }
    
    public void save(Client client) {
        clients.add(client);
    }
    
    public boolean emailExists(String email) {
        for(Client c : clients) {
            if(c.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
    
    public Client findByEmail(String email) {
        for(Client c : clients) {
            if(c.getEmail().equals(email)) {
                return c;
            }
        }
        return null;
    }
    
    public void update(Client client) {
        for(int i = 0; i < clients.size(); i++) {
            if(clients.get(i).getId().equals(client.getId())) {
                clients.set(i, client);
                return;
            }
        }
    }
}
