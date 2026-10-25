package bikeshare;

public enum MemberType {
    STUDENT("student", 2),
    STAFF("staff", 3);

    private final String label;
    private final int bahtPerMinute;

    MemberType(String label, int bahtPerMinute) {
        this.label = label;
        this.bahtPerMinute = bahtPerMinute;
    }

    public String label() {
        return label;
    }

    public long feeFor(int minutes) {
        if (minutes <= 0) {
            throw new IllegalArgumentException("Ride time must be a positive whole number.");
        }
        return (long) minutes * bahtPerMinute;
    }

    public static MemberType parse(String text) {
        String entered = text.trim();
        for (MemberType type : values()) {
            if (type.label.equalsIgnoreCase(entered)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Member type must be student or staff.");
    }
}
