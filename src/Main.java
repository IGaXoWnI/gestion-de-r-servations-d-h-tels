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
    private static ReservationRepository reservationRepository;

    public static void main(String[] args) {
        ClientRepository clientRepository = new ClientRepository();
        hotelRepository = new HotelRepository();
        reservationRepository = new ReservationRepository();

        authService = new AuthService(clientRepository);
        reservationService = new ReservationService(reservationRepository, hotelRepository);
        
        createAdminAccount();
        createTestData();
        
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
        System.out.println("2. Delete hotel");
        System.out.println("3. Update hotel");
        System.out.println("4. Logout");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1" -> handleCreateHotel();
            case "2" -> handleDeleteHotel();
            case "3" -> handleUpdateHotel();
            case "4" -> handleLogout();
            case "5" -> {
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
            System.out.println("No hotels available yet.");
            System.out.println("Create some hotels first!");
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
            System.out.println(" Hotel '" + name + "' created successfully!");
            
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number for rooms!");
        }
    }

    private static void handleDeleteHotel() {
        
        List<Hotel> hotels = hotelRepository.getAllHotels();
        
        if (hotels.isEmpty()) {
            System.out.println("No hotels available to delete.");
            return; 
        }
        
        System.out.println("\nAvailable hotels:");
        System.out.println("========================");
        for (int i = 0; i < hotels.size(); i++) {
            Hotel hotel = hotels.get(i);
            System.out.println((i + 1) + ". " + hotel.getName() + " (ID: " + hotel.getHotelId() + ")");
            System.out.println("    " + hotel.getAddress() + " - " + hotel.getAvailableRooms() + " rooms");
        }
        
        System.out.print("\nEnter hotel ID to delete: ");
        String hotelId = scanner.nextLine().trim();
        
        if (hotelId.isEmpty()) {
            System.out.println("Hotel ID cannot be empty!");
            return;
        }
        
        boolean success = hotelRepository.deleteHotel(hotelId);
        

    }

    private static void handleUpdateHotel() {
        System.out.println("\n=== UPDATE HOTEL ===");
        
        List<Hotel> hotels = hotelRepository.getAllHotels();
        
        if (hotels.isEmpty()) {
            System.out.println("No hotels available to update.");
            return;
        }
        
        System.out.println("\nAvailable hotels:");
        System.out.println("===============");
        for (int i = 0; i < hotels.size(); i++) {
            Hotel hotel = hotels.get(i);
            System.out.println((i + 1) + ". " + hotel.getName() + " (ID: " + hotel.getHotelId() + ")");
            System.out.println("    Address: " + hotel.getAddress());
            System.out.println("     Rooms: " + hotel.getAvailableRooms());
            System.out.println("    Rating: " + hotel.getRating() + "/5");
            System.out.println("===============" );
        }
        
        System.out.print("\nEnter hotel ID to update: ");
        String hotelId = scanner.nextLine().trim();
        
        
        
        Hotel existingHotel = hotelRepository.findHotelById(hotelId);
        if (existingHotel == null) {
            System.out.println(" Hotel with ID '" + hotelId + "' not found!");
            return;
        }
        
        System.out.println("Name: " + existingHotel.getName());
        System.out.println("Address: " + existingHotel.getAddress());
        System.out.println("Rooms: " + existingHotel.getAvailableRooms());
        
        System.out.println("\nEnter new information (press Enter to keep current value):");
        
        System.out.print("New hotel name [" + existingHotel.getName() + "]: ");
        String newName = scanner.nextLine().trim();
        if (newName.isEmpty()) {
            newName = existingHotel.getName(); 
        }
        
        System.out.print("New hotel address [" + existingHotel.getAddress() + "]: ");
        String newAddress = scanner.nextLine().trim();
        if (newAddress.isEmpty()) {
            newAddress = existingHotel.getAddress();
        }
        
        System.out.print("New available rooms [" + existingHotel.getAvailableRooms() + "]: ");
        String roomsInput = scanner.nextLine().trim();
        int newRooms = existingHotel.getAvailableRooms(); 
        
        if (!roomsInput.isEmpty()) {
            try {
                newRooms = Integer.parseInt(roomsInput);
                if (newRooms < 0) {
                    System.out.println(" Number of rooms cannot be negative!");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println(" Please enter a valid number for rooms!");
                return;
            }
        }
        
       
        
        boolean success = hotelRepository.updateHotel(hotelId, newName, newAddress, newRooms);
        
    }

    private static void handleReserveRoom() {
        System.out.println("\n=== Reserve Room ===");
        
        List<Hotel> hotels = hotelRepository.getAllHotels();
        
        if (hotels.isEmpty()) {
            System.out.println("No hotels available for reservation.");
            return;
        }
        
        System.out.println("\nAvailable hotels:");
        System.out.println("==================");
        for (int i = 0; i < hotels.size(); i++) {
            Hotel hotel = hotels.get(i);
            System.out.println((i + 1) + ". " + hotel.getName());
            System.out.println("    Address: " + hotel.getAddress());
            System.out.println("    Available Rooms: " + hotel.getAvailableRooms());
            System.out.println("    Rating: " + hotel.getRating() + "/5");
            System.out.println("    ID: " + hotel.getHotelId());
            System.out.println("==================");
        }
        
        System.out.print("\nEnter hotel ID: ");
        String hotelId = scanner.nextLine().trim();
        
        if (hotelId.isEmpty()) {
            System.out.println("Hotel ID cannot be empty!");
            return;
        }
        
        Hotel selectedHotel = hotelRepository.findHotelById(hotelId);
        if (selectedHotel == null) {
            System.out.println("Hotel not found! Please enter a valid hotel ID.");
            return;
        }
        
        if (selectedHotel.getAvailableRooms() <= 0) {
            System.out.println("Sorry, no rooms available at " + selectedHotel.getName());
            return;
        }
        
        System.out.print("Enter number of nights: ");
        String nightsInput = scanner.nextLine().trim();
        
        try {
            int nights = Integer.parseInt(nightsInput);
            
            if (nights <= 0) {
                System.out.println("Number of nights must be greater than 0!");
                return;
            }
            
            Client currentUser = authService.getCurrentUser();
            boolean success = reservationService.createReservation(currentUser.getId(), hotelId, nights);
            
            if (success) {
                System.out.println("Reservation created successfully!");
                System.out.println("Hotel: " + selectedHotel.getName());
                System.out.println("Nights: " + nights);
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number for nights!");
        }
    }

    private static void handleCancelReservation() {
        System.out.println("\n=== Cancel Reservation ===");
        
        Client currentUser = authService.getCurrentUser();
        
        List<domain.Reservation> userReservations = reservationRepository.getClientReservations(currentUser.getId());
        
        if (userReservations.isEmpty()) {
            System.out.println("You have no reservations to cancel.");
            return;
        }
        
        System.out.println("\nYour reservations:");
        System.out.println("==================");
        for (int i = 0; i < userReservations.size(); i++) {
            domain.Reservation reservation = userReservations.get(i);
            Hotel hotel = hotelRepository.findHotelById(reservation.getHotelId());
            
            System.out.println((i + 1) + ". Reservation ID: " + reservation.getId());
            System.out.println("    Hotel: " + (hotel != null ? hotel.getName() : "Unknown"));
            System.out.println("    Date: " + reservation.getFormattedDate());
            System.out.println("    Nights: " + reservation.getNights());
            System.out.println("==================");
        }
        
        System.out.print("\nEnter reservation ID to cancel: ");
        String reservationIdInput = scanner.nextLine().trim();
        
        if (reservationIdInput.isEmpty()) {
            System.out.println("Reservation ID cannot be empty!");
            return;
        }
        
        try {
            UUID reservationId = UUID.fromString(reservationIdInput);
            boolean success = reservationService.cancelReservation(currentUser.getId(), reservationId);
            
            if (success) {
                System.out.println("Reservation cancelled successfully!");
            }
            
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid reservation ID format!");
        }
    }

    private static void handleReservationHistory() {
        System.out.println("\n=== My Reservation History ===");
        
        Client currentUser = authService.getCurrentUser();
        
        List<domain.Reservation> userReservations = reservationRepository.getClientReservations(currentUser.getId());
        
        if (userReservations.isEmpty()) {
            System.out.println("You have no reservation history.");
            return;
        }
        
        System.out.println("\nYour reservations:");
        System.out.println("==================");
        
        for (int i = 0; i < userReservations.size(); i++) {
            domain.Reservation reservation = userReservations.get(i);
            Hotel hotel = hotelRepository.findHotelById(reservation.getHotelId());
            
            System.out.println((i + 1) + ". Reservation Details:");
            System.out.println("    ID: " + reservation.getId());
            System.out.println("    Hotel: " + (hotel != null ? hotel.getName() : "Hotel not found"));
            if (hotel != null) {
                System.out.println("    Address: " + hotel.getAddress());
            }
            System.out.println("    Date: " + reservation.getFormattedDate());
            System.out.println("    Nights: " + reservation.getNights());
            System.out.println("==================");
        }
        
        System.out.println("\nTotal reservations: " + userReservations.size());
    }

    

    private static void createAdminAccount() {
        authService.register("Admin", "admin@hotel.com", "admin123");
        System.out.println("Admin login: admin@hotel.com / admin123");
    }

    private static void createTestData() {
        authService.register("ismail baguni", "igaxowni@gmail.com", "password123");
        System.out.println("Test client login: igaxowni@gmail.com / password123");

        hotelRepository.addHotel("Grand Palace Hotel", "123 Main Street, Downtown", 15);
        hotelRepository.addHotel("Ocean View Resort", "456 Beach Road, Seaside", 8);
        
        System.out.println("Test data created: 1 client and 2 hotels");
    }


}
