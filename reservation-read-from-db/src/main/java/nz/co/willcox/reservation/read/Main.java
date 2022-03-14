package nz.co.willcox.reservation.read;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        final ReservationReader reservationReader = new ReservationReader();

        reservationReader.readReservations();
    }
}
