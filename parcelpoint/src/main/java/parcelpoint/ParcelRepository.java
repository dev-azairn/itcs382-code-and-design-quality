package parcelpoint;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ParcelRepository {
    private final Path outputPath;

    public ParcelRepository() {
        this(Path.of("output.txt"));
    }

    public ParcelRepository(Path outputPath) {
        this.outputPath = outputPath;
    }

    public void save(List<Parcel> parcels) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
            for (Parcel parcel : parcels) {
                writer.write(parcel.getCode() + "," + parcel.getStatus());
                writer.newLine();
            }
        }
    }
}
