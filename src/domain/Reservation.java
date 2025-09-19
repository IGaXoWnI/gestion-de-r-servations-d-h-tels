package domain;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Reservation {
    private UUID id;
    private Instant timestamp;
    private String hotelId;
    private UUID clientId;
    private int nights;

    public Reservation(String hotelId, UUID clientId, int nights) {
        this.id = UUID.randomUUID();
        this.timestamp = Instant.now();
        this.hotelId = hotelId;
        this.clientId = clientId;
        this.nights = nights;
    }

    public Reservation(UUID id, Instant timestamp, String hotelId, UUID clientId, int nights) {
        this.id = id;
        this.timestamp = timestamp;
        this.hotelId = hotelId;
        this.clientId = clientId;
        this.nights = nights;
    }

    public UUID getId() {
        return id;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getHotelId() {
        return hotelId;
    }

    public UUID getClientId() {
        return clientId;
    }

    public int getNights() {
        return nights;
    }

    public String getFormattedDate() {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(timestamp, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return localDateTime.format(formatter);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", timestamp=" + getFormattedDate() +
                ", hotelId='" + hotelId + '\'' +
                ", clientId=" + clientId +
                ", nights=" + nights +
                '}';
    }
}
