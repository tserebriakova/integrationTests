package enums;

public enum Credentials {
    VALID_CARD_333("380506548787", "123456", "1212",
            "5749991014219716", "10", "25", "891"),

    CARD_7777_RECOVERY("380505554211", "222222", "1212",
            "5749991014848506", "10", "25", "471"),
    CARD_8787("380950961515", "123456", "1735",
            "5749993010912898", "10", "25", "035"),

    WRONG_CARD_DETAILS("380505554211", "222222", "1212",
            "5749991014848506", "14", "25", "471"),
    EMPTY_CARD_DETAILS("", "", "",
            "", "", "", ""),
    VALID_0950961515("380950961515", "123456", "1212",
            "5248723331825776", "06", "26", "439"),
    VALID_0506541122("380506541122", "123456", "1735",
            "5248723335882609", "06", "26", "297"),
    VALID_0506542454_RECOVERY_ONLY("380506542454", "123456", "1735",
            "5248723332454568", "06", "26", "589"),
    INVALID_DATA("380509999999", "123456", "1735",
            "5454545454545454", "06", "26", "297"),
    NEW_USER("380676665544", "123456", "1735",
            "", "", "", ""),
    CARD_3DS_NOT_OXY("", "", "",
            "5375414104989800", "08", "28", "888"),
    PROD_CREDS("380507222161", "123456", "1212",
            "5406371576856094", "08", "22", "732"),
    DANIL_CREDS("3800934883502", "123456", "1012",
            "5406371576856094", "08", "22", "732");

    private final String phoneNumber;
    private final String otpCode;
    private final String passCode;
    private final String cardNumber;
    private final String cardExpMonth;
    private final String cardExpYear;
    private final String cardCvv;

    Credentials(String phoneNumber, String otpCode, String passCode, String cardNumber,
                String cardExpMonth, String cardExpYear, String cardCvv) {
        this.phoneNumber = phoneNumber;
        this.otpCode = otpCode;
        this.passCode = passCode;
        this.cardNumber = cardNumber;
        this.cardExpMonth = cardExpMonth;
        this.cardExpYear = cardExpYear;
        this.cardCvv = cardCvv;

    }

    public String phoneNumber() {
        return phoneNumber;
    }

    public String otpCode() {
        return otpCode;
    }

    public String passCode() {
        return passCode;
    }

    public String cardNumber() {
        return cardNumber;
    }

    public String cardExpMonth() {
        return cardExpMonth;
    }

    public String cardExpYear() {
        return cardExpYear;
    }

    public String cardCvv() {
        return cardCvv;
    }


}
