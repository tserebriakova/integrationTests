package enums;

public enum TransactionDirections {
    FROM_PAN_TO_PAN("pan", "pan"),
    FROM_PAN_ID_TO_PAN("panId", "pan"),
    FROM_PAN_ID_TO_PAN_ID("panId", "panId"),
    FROM_PAN_TO_PAN_ID("pan", "panId"),


    OXI_PAN_OXI_PAN("pan", "pan"),
    OXI_PAN_OTHER_PAN("pan", "pan"),
    OXI_PAN_ID_OXI_PAN_ID("panId", "panId"),
    OXI_PAN_ID_OXI_PAN("panId", "pan"),
    OXI_PAN_ID_OTHER_PAN("panId", "pan"),
    OTHER_PAN_OXI_PAN("pan", "pan"),
    OTHER_PAN_OXI_PAN_ID("pan", "panId"),
    OTHER_PAN_OTHER_PAN("pan", "pan"),
    OXI_PAN_MOBILE("pan", "mobile"),
    OXI_PAN_ID_MOBILE("panId", "mobile"),
    OTHER_PAN_MOBILE("pan", "mobile");


    private final String debitSrcType;
    private final String creditSrcType;

    TransactionDirections(String debitSrcType, String creditSrcType) {
        this.debitSrcType = debitSrcType;
        this.creditSrcType = creditSrcType;
    }

    public String debitSrc() {
        return debitSrcType;
    }

    public String creditSrc() {
        return creditSrcType;
    }

}
