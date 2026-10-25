package bikeshare;

public record MemberId(String value) {
    public MemberId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Member ID cannot be empty.");
        }
        value = value.trim();
    }
}
