package parcelpoint;

import java.io.IOException;
import java.util.OptionalInt;
import java.util.Scanner;

public class Main {

    // main code to get command and process command on line
    public static void main(String[] args) {
        ParcelService service = new ParcelService();

        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Commands: store <code> <studentId> <size 1-3>, pickup <code> <days>, list, staff, quit");

            while (in.hasNextLine()) {
                String line = in.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                try {
                    if (processCommand(line, service)) {
                        break;
                    }
                } catch (IOException e) {
                    System.err.println("Unable to save parcel data: " + e.getMessage());
                }
            }
        }
    }

    // process command depending on the input value
    // control coupling, logical cohesion 
    private static boolean processCommand(String line, ParcelService service) throws IOException {
        String[] words = line.split("\\s+");
        switch (words[0]) {
            case "store":
                handleStore(words, service);
                break;
            case "pickup":
                handlePickup(words, service);
                break;
            case "list":
                if (hasExpectedArgumentCount(words, 1)) {
                    printWaitingParcels(service);
                } else {
                    printInvalidCommand();
                }
                break;
            case "staff":
                if (hasExpectedArgumentCount(words, 1)) {
                    printStaffView(service);
                } else {
                    printInvalidCommand();
                }
                break;
            case "quit":
                if (hasExpectedArgumentCount(words, 1)) {
                    return true;
                }
                printInvalidCommand();
                break;
            default:
                printInvalidCommand();
        }
        return false;
    }


    private static void handleStore(String[] words, ParcelService service) throws IOException {
        if (!hasExpectedArgumentCount(words, 4)) {
            System.out.println("Usage: store <code> <studentId> <size 1-3>");
            return;
        }

        ParcelSize size;
        try {
            size = ParcelSize.fromCode(Integer.parseInt(words[3]));
        } catch (IllegalArgumentException e) {
            System.out.println("Size must be a number from 1 to 3");
            return;
        }

        Parcel parcel = new Parcel(new ParcelCode(words[1]), new StudentId(words[2]), size);
        OptionalInt lockerNumber = service.storeParcel(parcel);
        if (lockerNumber.isPresent()) {
            System.out.println("SMS to " + parcel.getStudentId().value() + ": parcel "
                    + parcel.getCode().value() + " is in locker " + lockerNumber.getAsInt());
            System.out.println("Locker " + lockerNumber.getAsInt());
        } else {
            System.out.println("No free locker");
        }
    }

    private static void handlePickup(String[] words, ParcelService service) throws IOException {
        if (!hasExpectedArgumentCount(words, 3)) {
            System.out.println("Usage: pickup <code> <days>");
            return;
        }

        int days;
        try {
            days = Integer.parseInt(words[2]);
        } catch (NumberFormatException e) {
            System.out.println("Days must be a non-negative number");
            return;
        }
        if (days < 0) {
            System.out.println("Days must be a non-negative number");
            return;
        }

        System.out.println("Fee: " + service.pickUp(new ParcelCode(words[1]), days));
    }

    private static boolean hasExpectedArgumentCount(String[] words, int expectedCount) {
        return words.length == expectedCount;
    }

    private static void printInvalidCommand() {
        System.out.println("Invalid command or arguments");
    }

    private static void printStaffView(ParcelService service) {
        for (Parcel parcel : service.getParcels()) {
            System.out.println(parcel.getCode().value() + " " + parcel.getStudentId().value()
                    + " " + parcel.getStatus());
        }
        System.out.println("Free lockers: " + service.getFreeLockerCount());
    }

    private static void printWaitingParcels(ParcelService service) {
        for (Parcel parcel : service.getWaitingParcels()) {
            System.out.println(parcel.getCode().value() + " is waiting");
        }
    }
}
