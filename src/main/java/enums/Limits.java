package enums;

public enum Limits {

    // CASH ATM AMOUNT / OPERATIONS
    CASH_ATM_AMOUNT("CRDAAWDL"),
    CASH_ATM_OPERATIONS("CRDCAFIN"),

    // PAY WITH TRADE ORGANIZATIONS
    TRADE_ORG_AMOUNT("CAPCD1PAY0"),
    TRADE_ORG_OPERATIONS("CCPCD1PAY0"),

    // INTERNET PAYMENTS
    INTERNET_PAYMENT_AMOUNT("CAECD1EPY0"),
    INTERNET_PAYMENT_OPERATIONS("CCECD1EPY0");

    private final String limitId;

    Limits(String limitId) {
        this.limitId = limitId;
    }

    public String id() {
        return limitId;
    }


}
