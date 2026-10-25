package bikeshare;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public final class CsvRideLog implements RideLog {
    private static final String HEADER =
            "member_id,member_type,bike,pickup_station,return_station,minutes,fee_baht";

    private final Path file;

    public CsvRideLog(Path file) {
        this.file = file;
    }

    @Override
    public void append(Ride ride) throws IOException {
        boolean writeHeader = !Files.exists(file) || Files.size(file) == 0;
        try (BufferedWriter writer = Files.newBufferedWriter(
                file, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            if (writeHeader) {
                writer.write(HEADER);
                writer.newLine();
            }
            writer.write(String.join(",",
                    csv(ride.memberId().value()),
                    csv(ride.memberType().label()),
                    csv(ride.bikeId()),
                    csv(ride.pickupStation().label()),
                    csv(ride.returnStation().label()),
                    Integer.toString(ride.minutes()),
                    Long.toString(ride.feeBaht())));
            writer.newLine();
        }
    }

    private static String csv(String value) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}
