package models.auth.response;

import lombok.Data;

@Data
public class GetAccessTokenResponseModel {
    String access_token;
    String access_token_lifespan;
}
