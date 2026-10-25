package parcelpoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.OptionalInt;

public class ParcelService {
    private List<Locker> lockers = new ArrayList<>();
    private List<Parcel> parcels = new ArrayList<>();
    private final ParcelRepository parcelRepository;

    public ParcelService() {
        this(new ParcelRepository());
    }

    public ParcelService(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
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

    public OptionalInt storeParcel(Parcel parcel) throws IOException {
        for (Locker locker : lockers) {
            if (locker.tryStore(parcel)) {
                parcel.markStored();
                parcels.add(parcel);
                saveParcels();
                return OptionalInt.of(locker.getNumber());
            }
        }
        return OptionalInt.empty();
    }

    public double pickUp(ParcelCode code, int days) throws IOException {
        for (Locker locker : lockers) {
            Parcel parcel = locker.removeParcel(code);
            if (parcel != null) {
                parcel.markPickedUp();
                saveParcels();
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

    public List<Parcel> getWaitingParcels() {
        return parcels.stream()
                .filter(parcel -> parcel.getStatus() == Parcel.Status.STORED)
                .toList();
    }

    public List<Parcel> getParcels() {
        return List.copyOf(parcels);
    }

    private void saveParcels() throws IOException {
        parcelRepository.save(List.copyOf(parcels));
    }
}
