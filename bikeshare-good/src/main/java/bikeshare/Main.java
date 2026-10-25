package bikeshare;

import java.nio.file.Path;
import java.util.Scanner;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        BikeShare bikeShare = new BikeShare(new CsvRideLog(Path.of("rides.csv")));
        try (Scanner input = new Scanner(System.in)) {
            new Menu(bikeShare, input).run();
        }
    }
}
