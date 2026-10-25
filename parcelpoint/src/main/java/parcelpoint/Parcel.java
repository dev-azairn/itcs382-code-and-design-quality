package parcelpoint;

public class Parcel {
    public enum Status {
        NEW,
        STORED,
        PICKED_UP
    }

    private String code;
    private String studentId;
    private ParcelSize size;
    private Status status;

    public Parcel(String code, String studentId, ParcelSize size) {
        this.code = code;
        this.studentId = studentId;
        this.size = size;
        this.status = Status.NEW;
    }

    public String getCode() {
        return code;
    }

    public String getStudentId() {
        return studentId;
    }

    public ParcelSize getSize() {
        return size;
    }

    public Status getStatus() {
        return status;
    }

    public void markStored() {
        status = Status.STORED;
    }

    public void markPickedUp() {
        status = Status.PICKED_UP;
    }
}
