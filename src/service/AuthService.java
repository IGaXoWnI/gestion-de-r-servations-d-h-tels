package service;

import domain.Client;
import repository.ClientRepository;

public class AuthService {
    private ClientRepository clientRepository;  
    private Client currentUser;                
    
    public AuthService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
        this.currentUser = null;
    }
    
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    public Client getCurrentUser() {
        return currentUser;
    }
    
    public void logout() {
        if (currentUser != null) {
            System.out.println("See you later " + currentUser.getFullName());
            currentUser = null;
        }
    }
    
    public boolean register(String fullName, String email, String password) {
        if (fullName == null || fullName.trim().isEmpty()) {
            System.out.println("Please enter your full name");
            return false;
        }
        
        if (email == null || !isValidEmail(email)) {
            System.out.println("Please enter a valid email address");
            return false;
        }
        
        if (password == null || password.length() < 6) {
            System.out.println("Password should be at least 6 characters long");
            return false;
        }
        
        if (clientRepository.emailExists(email.toLowerCase())) {
            System.out.println("An account with this email already exists");
            return false;
        }
        
        Client newClient = new Client(fullName.trim(), email.toLowerCase(), password);
        clientRepository.save(newClient);
        System.out.println("Account created successfully! Welcome " + fullName);
        return true;
    }
    
    public boolean login(String email, String password) {
        if (email == null || password == null) {
            System.out.println("Please provide both email and password");
            return false;
        }
        
        Client client = clientRepository.findByEmail(email.toLowerCase());
        
        if (client == null) {
            System.out.println("No account found with this email address");
            return false;
        }
        
        if (!client.getPassword().equals(password)) {
            System.out.println("Password is incorrect");
            return false;
        }
        
        currentUser = client;
        System.out.println("Welcome back " + client.getFullName());
        return true;
    }
    
    private boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    public ClientRepository getClientRepository() {
        return clientRepository;
    }
}
