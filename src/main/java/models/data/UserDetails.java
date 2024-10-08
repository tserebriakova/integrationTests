package models.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@AllArgsConstructor
@Data
@Setter
public class UserDetails {

    private String
                    phone,
                    otp,
                    password,
                    userId,
                    privateCertificate,
                    publicCertificate,
                    accessToken,
                    refreshToken,
                    paymentSignature;

    private UserCardDetails[] userCardDetails;

}
