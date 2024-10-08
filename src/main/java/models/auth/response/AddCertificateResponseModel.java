package models.auth.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddCertificateResponseModel {
    public String access_token;
    public String refresh_token;
    public String certificate_id;
    public String access_token_lifespan;


}
