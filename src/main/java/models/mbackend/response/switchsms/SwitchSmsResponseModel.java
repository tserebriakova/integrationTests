package models.mbackend.response.switchsms;

import lombok.Data;

@Data
public class SwitchSmsResponseModel {
    public int code;
    public String message;
    public String name;
    public int status;
    public SwitchSmsErrorsResponseModel[] errors;
}
