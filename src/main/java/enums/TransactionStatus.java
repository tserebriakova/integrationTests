package enums;

public enum TransactionStatus {

    SUCCESS("SUCCESS"),
    FAIL("FAIL");

    private final String transactionStatus;

    TransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String status() {
        return transactionStatus;
    }

}

