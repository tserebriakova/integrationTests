package enums;

import lombok.Getter;

@Getter
public enum StaticMethodsPath {
    CARDS("/cards"),
    ME("/me"),
    DESIGN("/design"),
    PAN("/pan"),
    CVV("/cvv"),
    PAN_CVV("/pan-cvv"),
    SECURITY("/security"),
    OPTIONS("/options"),
    LIMITS("/limits"),
    PIN("/pin");


    private final String path;

    StaticMethodsPath(String path){
        this.path = path;
    }
}
