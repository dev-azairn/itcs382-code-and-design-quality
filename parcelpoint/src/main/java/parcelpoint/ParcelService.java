package parcelpoint;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.OptionalInt;

public class ParcelService {
    private List<Locker> lockers = new ArrayList<>();
    private List<Parcel> parcels = new ArrayList<>();

    public ParcelService() {
        lockers.add(new Locker(1, ParcelSize.SMALL));
        lockers.add(new Locker(2, ParcelSize.MEDIUM));
        lockers.add(new Locker(3, ParcelSize.LARGE));
    }

    public long getFreeLockerCount() {
        return lockers.stream().filter(Locker::isFree).count();
    }

    public List<Locker> getLockers() {
        return Collections.unmodifiableList(lockers);
    }

    public OptionalInt storeParcel(Parcel parcel) {
        for (Locker locker : lockers) {
            if (locker.tryStore(parcel)) {
                parcel.markStored();
                parcels.add(parcel);
                System.out.println("SMS to " + parcel.getStudentId() + ": parcel "
                        + parcel.getCode() + " is in locker " + locker.getNumber());
                saveToFile();
                return OptionalInt.of(locker.getNumber());
            }
        }
        return OptionalInt.empty();
    }

    public double pickUp(String code, int days) {
        for (Locker locker : lockers) {
            Parcel parcel = locker.removeParcel(code);
            if (parcel != null) {
                parcel.markPickedUp();
                saveToFile();
                return fee(parcel, days);
            }
        }
        return 0;
    }

    public double fee(Parcel parcel, int days) {
        if (days <= 3) {
            return 0;
        }
        return (days - 3) * parcel.getSize().getDailyFee();
    }

    public void printWaitingParcels() {
        for (Parcel parcel : parcels) {
            if (parcel.getStatus() == Parcel.Status.STORED) {
                System.out.println(parcel.getCode() + " is waiting");
            }
        }
    }

    public void printStaffParcels() {
        for (Parcel parcel : parcels) {
            System.out.println(parcel.getCode() + " " + parcel.getStudentId() + " " + parcel.getStatus());
        }
    }

    private void saveToFile() {
        try (FileWriter writer = new FileWriter("output.txt")) {
            for (Parcel parcel : parcels) {
                writer.write(parcel.getCode() + "," + parcel.getStatus() + "\n");
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
