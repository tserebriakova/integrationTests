package steps.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import enums.Credentials;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.auth.request.AccountPin;
import models.auth.request.AddCertificate;
import models.auth.request.GetAccessToken;
import models.auth.response.AddCertificateResponseModel;
import models.auth.response.GetAccessTokenResponseModel;

import java.io.IOException;
import java.security.*;
import java.util.Objects;

import static utils.EncodingUtils.toSha256;
import static utils.GenerateCertificates.*;


public class AuthSteps extends AuthBaseSteps{

    private String baseUrl;
    private final String xVersion;

    public AuthSteps(String baseUrl,String xVersion) {
        this.xVersion = xVersion;
        this.baseUrl = baseUrl;
    }


    private PrivateKey privateKey;
    private String accessToken;
    private String certificateId;
    private String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }
    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }
    public void setRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }
    public String getCertificateId(){return certificateId;}
    public String getRefreshToken(){return refreshToken;}


    @Step("Генерация и установка сертификата")
    public AuthSteps generateAndPublishCertificate(Credentials credentials, String accessToken)
            throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, IOException {

        var csrWithKeys = generateCSR(credentials.phoneNumber());
        var csr = csrWithKeys.getLeft();
        var priv = csrWithKeys.getRight();
        var sign = hmacSha256Hex(toSha256(credentials.otpCode()), csr);
        var certificateRequest = new AddCertificate(csr, sign);
        setPrivateKey(priv);
        var response =
                postCertificate(mainAuthRequest(baseUrl),accessToken, certificateRequest, 200, xVersion)
                        .as(AddCertificateResponseModel.class);
        setAccessToken(response.getAccess_token());
        setCertificateId(response.getCertificate_id());
        setRefreshToken(response.getRefresh_token());

        return this;
    }

    @Step("Генерация и установка сертификата для не клиента банка")
    public AuthSteps generateAndPublishCertificateNotClient(String credentials, String accessToken)
            throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, IOException {
        var csrWithKeys = generateCSR(credentials);
        var csr = Objects.requireNonNull(csrWithKeys).getLeft();
        var priv = csrWithKeys.getRight();
        var sign = hmacSha256Hex(toSha256("123456"), csr);
        var certificateRequest = new AddCertificate(csr, sign);
        setPrivateKey(priv);
        var response =
                postCertificate(mainAuthRequest(baseUrl),accessToken, certificateRequest, 200, xVersion).as(AddCertificateResponseModel.class);
        setAccessToken(response.getAccess_token());
        setCertificateId(response.getCertificate_id());
        setRefreshToken(response.getRefresh_token());

        return this;
    }

    @Step("Отправка нового пароля для входа в приложение")
    public Response changeAccountPin(String accessToken, AccountPin body, int statusCode) {
        return
                putChangeAccountPin(mainAuthRequest(baseUrl),accessToken, body, statusCode, xVersion);
    }


    @Step("Получение accessToken'a по RefreshToken")
    public void getAccessToken(String refreshToken, PrivateKey privateKey) throws JsonProcessingException, NoSuchAlgorithmException, InvalidKeyException,
            SignatureException {
        var ref = new GetAccessToken(sign(privateKey, refreshToken),refreshToken);
        var resp = postClientToken(mainAuthRequest(baseUrl),ref, 200, xVersion);
        var response = resp.as(GetAccessTokenResponseModel.class);
        setAccessToken(response.getAccess_token());
    }

}
