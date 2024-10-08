package models.flows.response;

import lombok.*;
import models.mbackend.response.profile.MeResponseModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlowsResponseVars {

    public String access_token;
    public String user_id;
    public MeResponseModel profile;

}
