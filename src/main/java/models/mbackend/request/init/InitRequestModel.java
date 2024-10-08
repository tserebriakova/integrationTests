package models.mbackend.request.init;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InitRequestModel {

    // For attaching data to report
    @Override
    public String toString() {
        return "{\n" +
                "    \"type\": \"uapi_onboard_oksi_v3\"\n" +
                "}";
    }

}
