package models.mbackend.request.init;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InitReloginRequestModel {

    String certificate_id,
            refresh_token;

    String
            type;

    public InitReloginRequestModel(String type) {
        this.type = type;
    }

}