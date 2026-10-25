package parcelpoint;

public class Locker {
    private int number;
    private ParcelSize size;
    private Parcel parcel;

    public Locker(int number, ParcelSize size) {
        this.number = number;
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public ParcelSize getSize() {
        return size;
    }

    public Parcel getParcel() {
        return parcel;
    }

    boolean tryStore(Parcel parcel) {
        if (!isFree() || !size.canFit(parcel.getSize())) {
            return false;
        }
        this.parcel = parcel;
        return true;
    }

    Parcel removeParcel(String code) {
        if (parcel == null || !parcel.getCode().equals(code)) {
            return null;
        }
        Parcel removedParcel = parcel;
        parcel = null;
        return removedParcel;
    }

    public boolean isFree() {
        return parcel == null;
    }
}
