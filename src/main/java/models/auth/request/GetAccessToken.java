package models.auth.request;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetAccessToken {

    public String sign;
    public String refresh_token;

}
