package nz.co.willcox.reservation.read;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReservationReader {

    public ReservationReader() {}

    public void readReservations() throws IOException {
        final String readFile = readFile("currentRSVP.txt");
        System.out.println(readFile);
    }

    private String readFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);

        return Files.readAllLines(path).get(0);
    }
}
