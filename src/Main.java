import domain.Client;
import repository.ClientRepository;
import repository.HotelRepository;
import repository.ReservationRepository;
import service.AuthService;
import service.ReservationService;

import java.util.Scanner;
import java.util.UUID;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static AuthService authService;
    private static ReservationService reservationService;

    public static void main(String[] args) {
        ClientRepository clientRepository = new ClientRepository();
        HotelRepository hotelRepository = new HotelRepository();
        ReservationRepository reservationRepository = new ReservationRepository();

        authService = new AuthService(clientRepository);
        
        createAdminAccount();
        
        System.out.println("Welcome to Hotel Reservation System");
        System.out.println("===================================");

        // Main application loop
        while (true) {
            if (authService.isLoggedIn()) {
                showLoggedInMenu();
            } else {
                showLoginMenu();
            }
        }
    }


    private static void showLoginMenu() {
        System.out.println("\n===================");
        System.out.println("    MAIN MENU");
        System.out.println("===================");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1" -> handleRegister();
            case "2" -> handleLogin();
            case "3" -> {
                System.out.println("Goodbye!");
                System.exit(0);
            }
            default -> System.out.println("Invalid option");
        }
    }

    private static void showLoggedInMenu() {
        Client currentUser = authService.getCurrentUser();
        
        
        if (currentUser.getEmail().equals("admin@hotel.com")) {
            showAdminMenu();
        } else {
            showClientMenu();
        }
    }

    private static void showAdminMenu() {
        Client currentUser = authService.getCurrentUser();
        System.out.println("\n===================");
        System.out.println("  ADMIN MENU - " + currentUser.getFullName());
        System.out.println("===================");
        System.out.println("1. Create hotel");
        System.out.println("2. Logout");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1" -> handleCreateHotel();
            case "2" -> handleLogout();
            case "3" -> {
                System.out.println("Goodbye!");
                System.exit(0);
            }
            default -> System.out.println("Invalid option");
        }
    }

    private static void showClientMenu() {
        Client currentUser = authService.getCurrentUser();
        System.out.println("\n===================");
        System.out.println("  CLIENT MENU - " + currentUser.getFullName());
        System.out.println("===================");
        System.out.println("1. List all hotels");
        System.out.println("2. Reserve room");
        System.out.println("3. Cancel reservation");
        System.out.println("4. My reservation history");
        System.out.println("5. Logout");
        System.out.println("6. Exit");
        System.out.print("Choose an option: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1" -> handleListHotels();
            case "2" -> handleReserveRoom();
            case "3" -> handleCancelReservation();
            case "4" -> handleReservationHistory();
            case "5" -> handleLogout();
            case "6" -> {
                System.out.println("Goodbye!");
                System.exit(0);
            }
            default -> System.out.println("Invalid option");
        }
    }

    private static void handleRegister() {
        System.out.println("\n=== Registration ===");
        System.out.print("Full name: ");
        String fullName = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Password (minimum 6 characters): ");
        String password = scanner.nextLine();

        authService.register(fullName, email, password);
    }

    private static void handleLogin() {
        System.out.println("\n=== Login ===");
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        authService.login(email, password);
    }


}
