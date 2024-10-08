package models.p2p.response.status;

import lombok.Data;

@Data
public class CheckStatusResponseModel {

    public String id;
    public String status;
    public String user_action_url;
    public boolean required_3ds;


}
