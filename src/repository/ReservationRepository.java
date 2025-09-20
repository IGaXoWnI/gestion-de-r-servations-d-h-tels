package repository;


import domain.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReservationRepository {
    private List<Reservation> reservations = new ArrayList<>();

    public ReservationRepository(){
        this.reservations = new ArrayList<>();
    }
    public void save(Reservation reservation){
        reservations.add(reservation);
        System.out.println("the reservation succesfully created");
    }

    public List<Reservation> getAllReservation(){
        return new ArrayList(reservations);
    }


    public Reservation getReservationById(UUID reservationId){
        for(Reservation reservation : reservations){
            if(reservation.getId().equals(reservationId)){
                return reservation;
            }
        }
        return null;
    }

    public List<Reservation> getClientReservations(UUID clientId){
        List clientReservations = new ArrayList<>();
        for(Reservation reservation : reservations){
            if(reservation.getClientId().equals(clientId)){
                clientReservations.add(reservation);
            }
        }
        return clientReservations ;
    }


    public boolean delete(Reservation reservation){
    boolean removed = reservations.remove(reservation);
    if(removed){
        System.out.println("the reserbation removed");
    }else {
        System.out.println("the reservation not removed");
    }
    return removed ;
    }

}
