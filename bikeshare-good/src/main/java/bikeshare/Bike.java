package bikeshare;

public final class Bike {
    private final String id;
    private Station station;
    private MemberId memberId;
    private MemberType memberType;

    public Bike(String id, Station station) {
        this.id = id;
        this.station = station;
    }

    public String id() {
        return id;
    }

    public boolean availableAt(Station station) {
        return !isRented() && this.station == station;
    }

    public boolean hasId(String bikeId) {
        return id.equalsIgnoreCase(bikeId.trim());
    }

    public void lendTo(MemberId memberId, MemberType memberType) {
        if (isRented()) {
            throw new IllegalArgumentException(id + " is already rented.");
        }
        this.memberId = memberId;
        this.memberType = memberType;
    }

    public Ride toRide(Station returnStation, int minutes) {
        if (!isRented()) {
            throw new IllegalArgumentException(id + " is not currently rented.");
        }
        return new Ride(id, memberId, memberType, station, returnStation, minutes,
                memberType.feeFor(minutes));
    }

    public void parkAt(Station station) {
        this.station = station;
        this.memberId = null;
        this.memberType = null;
    }

    private boolean isRented() {
        return memberId != null;
    }
}
