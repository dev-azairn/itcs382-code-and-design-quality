package bikeshare;

public record Receipt(String bikeId, int minutes, long feeBaht) {
    public String text() {
        return "Receipt: bike " + bikeId + ", " + minutes + " minutes, " + feeBaht + " baht.";
    }
}
