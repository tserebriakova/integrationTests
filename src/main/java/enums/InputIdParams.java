package enums;

public enum InputIdParams {

    PHONE("phone", ""),
    OTP("otp_code", "123456"),
    PIN("pin_hash", ""),
    RESEND_OTP("action_resend_code", "resend_code"),
    BACK_FROM_OTP("action_otp_code_back", "back"),
    PIN_RECOVERY("action_pin_hash", "recovery"),
    BACK_TO_ENTER_PIN("action_back_to_enter_pin_hash", "back"),
    RECOVERY_CARD_NUM("recovery_card_num", ""),
    RECOVERY_CARD_CVV("recovery_card_cvv", ""),
    RECOVERY_CARD_EXP_YEAR("recovery_card_exp_year", ""),
    RECOVERY_CARD_EXP_MONTH("recovery_card_exp_month", ""),
    PIN_INIT("init_pin_hash", "");


    private final String id;
    private final String value;

    InputIdParams(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public String id() {
        return id;
    }

    public String value() {
        return value;
    }
}
