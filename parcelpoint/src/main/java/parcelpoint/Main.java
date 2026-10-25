package parcelpoint;

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

                if (processCommand(line, service)) {
                    break;
                }
            }
        }
    }

    // process command depending on the input value
    // control coupling, logical cohesion 
    private static boolean processCommand(String line, ParcelService service) {
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
                    service.printWaitingParcels();
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


    private static void handleStore(String[] words, ParcelService service) {
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

        Parcel parcel = new Parcel(words[1], words[2], size);
        Locker locker = service.storeParcel(parcel);
        System.out.println(locker == null ? "No free locker" : "Locker " + locker.getNumber());
    }

    private static void handlePickup(String[] words, ParcelService service) {
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

        System.out.println("Fee: " + service.pickUp(words[1], days));
    }

    private static boolean hasExpectedArgumentCount(String[] words, int expectedCount) {
        return words.length == expectedCount;
    }

    private static void printInvalidCommand() {
        System.out.println("Invalid command or arguments");
    }

    private static void printStaffView(ParcelService service) {
        service.printStaffParcels();
        System.out.println("Free lockers: " + service.getLockers().stream().filter(Locker::isFree).count());
    }
}
