package enums;

public enum P2PStatus {
    SUCCESS("SUCCESS"),
    IN_PROGRESS("IN_PROGRESS"),
    FAILED("FAILED"),
    CANCELLED("CANCELLED");

    private final String status;

    P2PStatus(String status) {
        this.status = status;
    }

    public String status() {
        return status;
    }
}
