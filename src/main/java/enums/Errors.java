package enums;

public enum Errors {
    //FLOWS
    SOME_ERROR("","","");
    //M-BACKEND
    //AUTH
    //P2P
    //SMPAY
    //NOTIFY
    private final String en;
    private final String uk;
    private final String ru;

     Errors(String en, String uk, String ru){
        this.en = en;
        this.uk = uk;
        this.ru = ru;
    }

    public String en(){return en;}
    public String ru(){return ru;}
    public String uk(){return uk;}


}
