package bikeshare;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class BikeShare {
    private static final int BIKES_PER_STATION = 2;

    private final List<Bike> bikes = new ArrayList<>();
    private final RideLog rideLog;

    public BikeShare(RideLog rideLog) {
        this.rideLog = rideLog;
        int number = 1;
        for (Station station : Station.values()) {
            for (int made = 0; made < BIKES_PER_STATION; made++) {
                bikes.add(new Bike("B" + number++, station));
            }
        }
    }

    public String rent(MemberId memberId, MemberType memberType, Station station) {
        for (Bike bike : bikes) {
            if (bike.availableAt(station)) {
                bike.lendTo(memberId, memberType);
                return bike.id();
            }
        }
        throw new IllegalArgumentException("No bikes are available at " + station.label() + ".");
    }

    public Receipt returnBike(String bikeId, Station returnStation, int minutes) throws IOException {
        Bike bike = find(bikeId);
        Ride ride = bike.toRide(returnStation, minutes);
        rideLog.append(ride);
        bike.parkAt(returnStation);
        return ride.receipt();
    }

    private Bike find(String bikeId) {
        for (Bike bike : bikes) {
            if (bike.hasId(bikeId)) {
                return bike;
            }
        }
        throw new IllegalArgumentException("Unknown bike.");
    }
}
