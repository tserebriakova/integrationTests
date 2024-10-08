package models.mbackend.request.init;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InitRequestVarsModel {

    String refresh_token;
    String certificate_id;

    @Override
    public String toString() {
        return "{\n" +
                "\t\"type\": \"uapi_onboard_oksi_v3\",\n" +
                "\t\"certificate_id\": \"" + certificate_id + "\",\n" +
                "\t\"refresh_token\": \"" + refresh_token + "\"\n" +
                "}";
    }

}
