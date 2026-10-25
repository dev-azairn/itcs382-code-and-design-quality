package parcelpoint;

public class Parcel {
    public enum Status {
        NEW,
        STORED,
        PICKED_UP
    }

    private ParcelCode code;
    private StudentId studentId;
    private ParcelSize size;
    private Status status;

    public Parcel(ParcelCode code, StudentId studentId, ParcelSize size) {
        this.code = code;
        this.studentId = studentId;
        this.size = size;
        this.status = Status.NEW;
    }

    public ParcelCode getCode() {
        return code;
    }

    public StudentId getStudentId() {
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
