import domain.Client;
import domain.Hotel;
import repository.ClientRepository;
import repository.HotelRepository;
import repository.ReservationRepository;
import service.AuthService;
import service.ReservationService;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static AuthService authService;
    private static ReservationService reservationService;
    private static HotelRepository hotelRepository;

    public static void main(String[] args) {
        ClientRepository clientRepository = new ClientRepository();
        hotelRepository = new HotelRepository();
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

    private static void handleListHotels() {
        
        List<Hotel> hotels = hotelRepository.getAllHotels();
        
        if (hotels.isEmpty()) {
            System.out.println("📋 No hotels available yet.");
            System.out.println("💡 Create some hotels first!");
            return;
        }
        
        System.out.println("========================" );
        for (int i = 0; i < hotels.size(); i++) {
            Hotel hotel = hotels.get(i);
            System.out.println((i + 1) + ". " + hotel.getName());
            System.out.println("    Address: " + hotel.getAddress());
            System.out.println("    Available Rooms: " + hotel.getAvailableRooms());
            System.out.println("    Rating: " + hotel.getRating() + "/5");
            System.out.println("    ID: " + hotel.getHotelId());
            System.out.println("========================" );
        }
        System.out.println("Total hotels: " + hotels.size());
    }
    private static void handleLogout() {
        authService.logout();
        System.out.println(" You have been logged out successfully!");
    }
    private static void handleCreateHotel() {
        
        System.out.print("Hotel name: ");
        String name = scanner.nextLine().trim();
        
        System.out.print("Hotel address: ");
        String address = scanner.nextLine().trim();
        
        System.out.print("Number of available rooms: ");
        String roomsInput = scanner.nextLine().trim();
        
        try {
            int availableRooms = Integer.parseInt(roomsInput);
            
            if (name.isEmpty()) {
                System.out.println(" Hotel name cannot be empty!");
                return;
            }
            if (address.isEmpty()) {
                System.out.println("Hotel address cannot be empty!");
                return;
            }
            if (availableRooms < 0) {
                System.out.println(" Number of rooms cannot be negative!");
                return;
            }
            
            hotelRepository.addHotel(name, address, availableRooms);
            System.out.println("✅ Hotel '" + name + "' created successfully!");
            
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number for rooms!");
        }
    }

    private static void handleReserveRoom() {
        System.out.println("Room reservation feature coming soon...");
    }

    private static void handleCancelReservation() {
        System.out.println("Reservation cancellation feature coming soon...");
    }

    private static void handleReservationHistory() {
        System.out.println("Reservation history feature coming soon...");
    }

    private static void handleUpdateProfile() {
        System.out.println("Profile update feature coming soon...");
    }

    private static void handleChangePassword() {
        System.out.println("Password change feature coming soon...");
    }

    private static void createAdminAccount() {
        authService.register("Admin", "admin@hotel.com", "admin123");
        System.out.println("Admin login: admin@hotel.com / admin123");
    }


}
