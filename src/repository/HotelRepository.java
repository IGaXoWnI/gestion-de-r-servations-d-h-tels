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

    public boolean deleteHotel(String hotelId) {
        for (int i = 0; i < hotels.size(); i++) {
            Hotel hotel = hotels.get(i);
            
            if (hotel.getHotelId().equals(hotelId)) {
                Hotel removedHotel = hotels.remove(i);
                System.out.println("✅ Hotel '" + removedHotel.getName() + "' deleted successfully!");
                return true; 
            }
        }
        
        System.out.println("Hotel with ID '" + hotelId + "' not found!");
        return false;
    }

    public boolean updateHotel(String hotelId, String newName, String newAddress, int newAvailableRooms) {
        for (int i = 0; i < hotels.size(); i++) {
            Hotel hotel = hotels.get(i);
            
            if (hotel.getHotelId().equals(hotelId)) {
                hotel.setName(newName);
                hotel.setAddress(newAddress);
                hotel.setAvailableRooms(newAvailableRooms);

                
                System.out.println("✅ Hotel '" + hotel.getName() + "' updated successfully!");
                return true;
            }
        }
        
        System.out.println(" Hotel not found!");
        return false;
    }

    public Hotel findHotelById(String hotelId) {
        for (Hotel hotel : hotels) {
            if (hotel.getHotelId().equals(hotelId)) {
                return hotel;
            }
        }
        return null;
    }


    public boolean reserve(String hotelId){
    Hotel hotel = findHotelById(hotelId);
    hotel.setAvailableRooms(hotel.getAvailableRooms() - 1);
        System.out.println(" Room reserved");
        return true ;
    }


    public boolean cancelreservation(String hotelId){
        Hotel hotel = findHotelById(hotelId);
        hotel.setAvailableRooms(hotel.getAvailableRooms()+1);
        return true;
    }

    public boolean hasAvailableRooms(String hotelId){
        Hotel hotel = findHotelById(hotelId);
        return hotel != null && hotel.getAvailableRooms()>0 ;
    }
}
