package models.mbackend.response.switchsms;

import lombok.Data;

@Data
public class SwitchSmsErrorsResponseModel {
    public String message;
    public String type;
    public String error_code;
}
