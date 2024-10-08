package models.auth.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor

public class AddCertificate {

    public final String csr;
    public final String sign;

}
