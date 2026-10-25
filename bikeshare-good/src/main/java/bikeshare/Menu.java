package bikeshare;

import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public final class Menu {
    private final BikeShare bikeShare;
    private final Scanner input;

    public Menu(BikeShare bikeShare, Scanner input) {
        this.bikeShare = bikeShare;
        this.input = input;
    }

    public void run() {
        System.out.println("Commands: rent, return, quit");
        while (true) {
            String command = readLine("Command (rent, return, quit): ").toLowerCase(Locale.ROOT);
            try {
                switch (command) {
                    case "rent" -> rent();
                    case "return" -> returnBike();
                    case "quit" -> {
                        return;
                    }
                    default -> System.out.println("Enter rent, return or quit.");
                }
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            } catch (IOException ex) {
                System.out.println("Could not save ride to rides.csv: " + ex.getMessage());
            }
        }
    }

    private void rent() {
        MemberId memberId = new MemberId(readLine("Member ID: "));
        MemberType memberType = MemberType.parse(readLine("Member type (student/staff): "));
        Station station = Station.parse(readLine("Station (Library/Canteen/Dorm): "));
        String bikeId = bikeShare.rent(memberId, memberType, station);
        System.out.println("Bike " + bikeId + " rented to " + memberId.value() + ".");
    }

    private void returnBike() throws IOException {
        String bikeId = readLine("Bike ID: ");
        Station station = Station.parse(readLine("Station (Library/Canteen/Dorm): "));
        int minutes = readMinutes();
        Receipt receipt = bikeShare.returnBike(bikeId, station, minutes);
        System.out.println(receipt.text());
    }

    private int readMinutes() {
        String text = readLine("Ride time in minutes: ");
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Ride time must be a positive whole number.");
        }
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return input.nextLine().trim();
    }
}
