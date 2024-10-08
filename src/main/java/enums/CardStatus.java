package enums;

public enum CardStatus {
    ACTIVE("ACTIVE"),
    BLOCKED("BLOCKED"),
    INACTIVE("INACTIVE"),
    INACTIVE_BY_PIN("INACTIVE_BY_PIN");

    private final String cardStatus;

    CardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String status() {
        return cardStatus;
    }

}
