package tests.endpoints.auth;

import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import steps.ApiSteps;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public class AccessTokenExpirationTest extends ApiSteps {
    @Feature("Релогин")
    @Test(description = "Проверка возможности входа по валидному рефреш токену")
    public void accessTokenExpirationTest() throws IOException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {

        mobileBackendSteps()
                .startOnboardingFlow();

        flowsSteps()
                .sendPhoneNumber(getMainCreds(), mobileBackendSteps().getUuid(), mobileBackendSteps().getTaskId())
                .sendOtpCode(getMainCreds())
                .sendPinHash(getMainCreds());

        authSteps()
                .generateAndPublishCertificate(getMainCreds(), flowsSteps().getAccessToken());

//        wait()

    }
}
