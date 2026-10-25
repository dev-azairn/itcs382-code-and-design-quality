package bikeshare;

public enum Station {
    LIBRARY("Library"),
    CANTEEN("Canteen"),
    DORM("Dorm");

    private final String label;

    Station(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }

    public static Station parse(String text) {
        String entered = text.trim();
        for (Station station : values()) {
            if (station.label.equalsIgnoreCase(entered)) {
                return station;
            }
        }
        throw new IllegalArgumentException("Unknown station.");
    }
}
