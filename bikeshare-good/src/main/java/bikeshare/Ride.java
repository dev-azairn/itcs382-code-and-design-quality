package bikeshare;

public record Ride(
        String bikeId,
        MemberId memberId,
        MemberType memberType,
        Station pickupStation,
        Station returnStation,
        int minutes,
        long feeBaht) {

    public Receipt receipt() {
        return new Receipt(bikeId, minutes, feeBaht);
    }
}
