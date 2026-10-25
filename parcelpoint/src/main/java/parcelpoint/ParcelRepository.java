package parcelpoint;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ParcelRepository {
    private static final String HEADER = "code,studentId,size,status";

    private final Path outputPath;

    public ParcelRepository() {
        this(Path.of("parcels.csv"));
    }

    public ParcelRepository(Path outputPath) {
        this.outputPath = outputPath;
    }

    public void save(List<Parcel> parcels) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
            writer.write(HEADER);
            writer.newLine();
            for (Parcel parcel : parcels) {
                writer.write(toCsv(parcel));
                writer.newLine();
            }
        }
    }

    public List<Parcel> load() throws IOException {
        if (Files.notExists(outputPath)) {
            return List.of();
        }

        List<Parcel> parcels = new ArrayList<>();
        for (String line : Files.readAllLines(outputPath)) {
            if (line.isBlank() || line.equals(HEADER)) {
                continue;
            }
            parcels.add(fromCsv(line));
        }
        return parcels;
    }

    private static String toCsv(Parcel parcel) {
        return parcel.getCode().value() + ","
                + parcel.getStudentId().value() + ","
                + parcel.getSize().getCode() + ","
                + parcel.getStatus().name();
    }

    private static Parcel fromCsv(String line) throws IOException {
        String[] columns = line.split(",", -1);
        if (columns.length != 4) {
            throw new IOException("Invalid parcel record: " + line);
        }

        ParcelSize size;
        Parcel.Status status;
        try {
            size = ParcelSize.fromCode(Integer.parseInt(columns[2]));
            status = Parcel.Status.valueOf(columns[3]);
        } catch (IllegalArgumentException e) {
            throw new IOException("Invalid parcel record: " + line, e);
        }

        Parcel parcel = new Parcel(new ParcelCode(columns[0]), new StudentId(columns[1]), size);
        if (status == Parcel.Status.STORED) {
            parcel.markStored();
        } else if (status == Parcel.Status.PICKED_UP) {
            parcel.markPickedUp();
        }
        return parcel;
    }
}
