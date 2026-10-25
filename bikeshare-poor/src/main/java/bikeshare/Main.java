package bikeshare;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static final int STUDENT = 1;
    public static final int STAFF = 2;
    public static final String[] STATIONS = {"Library", "Canteen", "Dorm"};
    public static final int[] BIKE_STATIONS = {0, 0, 1, 1, 2, 2};
    public static final boolean[] BIKE_RENTED = new boolean[6];
    public static final String[] MEMBER_IDS = new String[6];
    public static final int[] MEMBER_TYPES = new int[6];

    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            while (true) {
                System.out.print("Command (rent, return, quit): ");
                String command = input.nextLine().trim().toLowerCase(Locale.ROOT);
                switch (command) {
                    case "rent" -> rent(input);
                    case "return" -> returnBike(input);
                    case "quit" -> {
                        return;
                    }
                    default -> System.out.println("Enter rent, return or quit.");
                }
            }
        }
    }

    private static void rent(Scanner input) {
        System.out.print("Member ID: ");
        String memberId = input.nextLine().trim();
        if (memberId.isEmpty()) {
            System.out.println("Member ID cannot be empty.");
            return;
        }

        System.out.print("Member type (student/staff): ");
        String type = input.nextLine().trim().toLowerCase(Locale.ROOT);
        int memberType = switch (type) {
            case "student" -> STUDENT;
            case "staff" -> STAFF;
            default -> 0;
        };
        if (memberType == 0) {
            System.out.println("Member type must be student or staff.");
            return;
        }

        int station = readStation(input);
        if (station < 0) {
            return;
        }

        int bike = -1;
        for (int i = 0; i < BIKE_STATIONS.length; i++) {
            if (!BIKE_RENTED[i] && BIKE_STATIONS[i] == station) {
                bike = i;
                break;
            }
        }
        if (bike < 0) {
            System.out.println("No bikes are available at " + STATIONS[station] + ".");
            return;
        }

        updateBike(bike, station, memberId, memberType, true);
        System.out.println("Bike B" + (bike + 1) + " rented to " + memberId + ".");
    }

    private static void returnBike(Scanner input) {
        System.out.print("Bike ID (B1-B6): ");
        String bikeId = input.nextLine().trim().toUpperCase(Locale.ROOT);
        if (!bikeId.matches("B[1-6]")) {
            System.out.println("Enter a bike ID from B1 to B6.");
            return;
        }
        int bike = Integer.parseInt(bikeId.substring(1)) - 1;
        if (!BIKE_RENTED[bike]) {
            System.out.println(bikeId + " is not currently rented.");
            return;
        }

        int station = readStation(input);
        if (station < 0) {
            return;
        }

        System.out.print("Ride time in minutes: ");
        int minutes;
        try {
            minutes = Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Ride time must be a positive whole number.");
            return;
        }
        if (minutes <= 0) {
            System.out.println("Ride time must be a positive whole number.");
            return;
        }

        String memberId = MEMBER_IDS[bike];
        int memberType = MEMBER_TYPES[bike];
        int pickupStation = BIKE_STATIONS[bike];
        long fee = (long) minutes * (memberType == STUDENT ? 2 : 3);
        try {
            saveRide(bike, pickupStation, station, minutes, fee, memberId, memberType);
        } catch (IOException e) {
            System.out.println("Could not save ride to rides.csv: " + e.getMessage());
            return;
        }

        updateBike(bike, station, "", 0, false);
        System.out.println("Receipt: bike B" + (bike + 1) + ", " + minutes
                + " minutes, " + fee + " baht.");
    }

    private static int readStation(Scanner input) {
        System.out.print("Station (Library/Canteen/Dorm): ");
        String stationName = input.nextLine().trim();
        for (int i = 0; i < STATIONS.length; i++) {
            if (STATIONS[i].equalsIgnoreCase(stationName)) {
                return i;
            }
        }
        System.out.println("Unknown station.");
        return -1;
    }

    private static void updateBike(int bike, int station, String memberId,
                                   int memberType, boolean renting) {
        BIKE_RENTED[bike] = renting;
        BIKE_STATIONS[bike] = station;
        MEMBER_IDS[bike] = renting ? memberId : null;
        MEMBER_TYPES[bike] = renting ? memberType : 0;
    }

    private static void saveRide(int bike, int pickupStation, int returnStation,
                                 int minutes, long fee,
                                 String memberId, int memberType) throws IOException {
        Path file = Path.of("rides.csv");
        boolean writeHeader = !Files.exists(file) || Files.size(file) == 0;
        try (BufferedWriter writer = Files.newBufferedWriter(file,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            if (writeHeader) {
                writer.write("member_id,member_type,bike,pickup_station,return_station,minutes,fee_baht");
                writer.newLine();
            }
            writer.write(String.join(",",
                    csv(memberId),
                    memberType == STUDENT ? "student" : "staff",
                    "B" + (bike + 1),
                    csv(STATIONS[pickupStation]),
                    csv(STATIONS[returnStation]),
                    Integer.toString(minutes),
                    Long.toString(fee)));
            writer.newLine();
        }
    }

    private static String csv(String value) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}
