package enums;

public enum AcceptedLanguages {
    UK("uk"),
    RU("ru");

    private final String language;

    AcceptedLanguages(String language) {
        this.language = language;
    }

    public String language() {
        return language;
    }

}
