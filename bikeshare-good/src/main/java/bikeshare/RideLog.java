package bikeshare;

import java.io.IOException;

public interface RideLog {
    void append(Ride ride) throws IOException;
}
