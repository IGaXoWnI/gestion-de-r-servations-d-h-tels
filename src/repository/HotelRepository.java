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

    // Get all hotels
    public List<Hotel> getAllHotels() {
        return hotels;
    }
}
