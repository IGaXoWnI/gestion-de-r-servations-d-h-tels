package service;

import domain.Hotel;
import domain.Reservation;
import repository.HotelRepository;
import repository.ReservationRepository;

import java.util.UUID;

public class ReservationService {
    private ReservationRepository reservationRepository;
    private HotelRepository hotelRepository;

    public ReservationService (ReservationRepository reservationRepository , HotelRepository hotelRepository){
    this.reservationRepository = reservationRepository;
    this.hotelRepository = hotelRepository;
    }


    public boolean createReservation(UUID clientId,String hotelId , int nights) {
        if (clientId == null) {
            System.out.println("the client id not correct");
            return false;
        }
        if (hotelId == null) {
            System.out.println("the hotel id invalid");
            return false;
        }
        if (nights < 0) {
            System.out.println("the nights number must be upper 0");
            return false;
        }
        Hotel hotel = hotelRepository.findHotelById(hotelId);
        if(hotel==null){
            System.out.println("the hotel you insert not found try again ");
            return false ;
        }

        if(!hotelRepository.hasAvailableRooms(hotelId)){
            System.out.println("the hotel full you can reserve right now ");
            return false;
        }

        try{
        Reservation reservation = new Reservation(hotelId , clientId , nights);
        reservationRepository.save(reservation);

        boolean roomReserved = hotelRepository.reserve(hotelId);
        if(!roomReserved){
            System.out.println("Failed to reserve room");
            return false ;
        }
            System.out.println(" Reservation successful");
        return true ;
        }
        catch(Exception e){
            System.out.println("echec to create this reservation " +e.getMessage() );
            return false ;
        }
    }
}
