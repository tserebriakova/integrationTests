package enums;

public enum SecurityOptions {


    //"title": "Безконтактна оплата"
    WIRELESSPAYMENT("CCXCD1BAN2"),

    //"title": "Зняття готівки в банкоматі"
    CASHATM("CCXCD1BAN3"),

    //"title": "Інтернет-платежі"
    INTERNETPAYMENTS("CCPCD1BAN0"),

    //"title": "Закордонні платежі"
    ABROADPAYMENTS("CCXCD1BAN4"),

    //"title": "Оплата магнітною смугою"
    STRIPPAYMENT("CCECD1BAN1");


    private final String securityId;

    SecurityOptions(String securityId) {
        this.securityId = securityId;
    }

    public String id() {
        return securityId;
    }


}




