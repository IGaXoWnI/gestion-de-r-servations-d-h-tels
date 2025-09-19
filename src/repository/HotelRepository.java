package repository;


import domain.Client;
import domain.Hotel;

import java.util.ArrayList;
import java.util.List;

public class HotelRepository {
    private List<Hotel> hotels = new ArrayList<>();

    public  HotelRepository (){
        this.hotels = new ArrayList<>();
    }

    public void addHotel(String name, String address, int availableRooms) {
        String hotelId = "HOTEL_" + (hotels.size() + 1);
        
        Hotel newHotel = new Hotel(hotelId, name, address, availableRooms, 0.0);
        
        hotels.add(newHotel);
        
    }

    public List<Hotel> getAllHotels() {
        return hotels;
    }

    // DELETE - Remove hotel by ID
    public boolean deleteHotel(String hotelId) {
        // Loop through all hotels to find the one with matching ID
        for (int i = 0; i < hotels.size(); i++) {
            Hotel hotel = hotels.get(i);
            
            // Check if this hotel's ID matches what we want to delete
            if (hotel.getHotelId().equals(hotelId)) {
                // Found it! Remove from ArrayList
                Hotel removedHotel = hotels.remove(i);
                System.out.println("✅ Hotel '" + removedHotel.getName() + "' deleted successfully!");
                return true; // Success!
            }
        }
        
        // If we get here, hotel was not found
        System.out.println("❌ Hotel with ID '" + hotelId + "' not found!");
        return false; // Failed to delete
    }
}
