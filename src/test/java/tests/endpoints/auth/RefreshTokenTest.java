package tests.endpoints.auth;

import io.qameta.allure.Feature;
import models.mbackend.response.profile.MeResponseModel;
import org.testng.annotations.Test;
import steps.ApiSteps;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public class RefreshTokenTest  extends ApiSteps {


    @Feature("Релогин")
    @Test(description = "Проверка возможности входа по валидному рефреш токену")
    public void reLoginTest() throws IOException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        var PROFILE_METHOD_PATH = "/me";

        mobileBackendSteps()
                .startOnboardingFlow();

        flowsSteps()
                .sendPhoneNumber(getMainCreds(), mobileBackendSteps().getUuid(), mobileBackendSteps().getTaskId())
                .sendOtpCode(getMainCreds())
                .sendPinHash(getMainCreds());

        authSteps()
                .generateAndPublishCertificate(getMainCreds(), flowsSteps().getAccessToken());

        var oldAccessToken = authSteps().getAccessToken();

        var response = mobileBackendSteps()
                .startOnboardingFlowWithTokenAndCertificate(authSteps().getCertificateId(), authSteps().getRefreshToken());

        verify("Должен быть шаг enter_pin_hash", response.getStep(), "enter_pin_hash", true);

        authSteps().getAccessToken(authSteps().getRefreshToken(), authSteps().getPrivateKey());


        var me =
                mobileBackendSteps()
                        .mobileBackendProcess(PROFILE_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(MeResponseModel.class);

        var newAccessToken = authSteps().getAccessToken();

        verify("Старый accessToken не равен новому accessToken", oldAccessToken, newAccessToken, false);
        verify("Номер пользователя при запросе профиля соответсвует номеру введенного на этапе онбординга",
                me.getPhone(), getMainCreds().phoneNumber(), true);

    }

    @Test(description = "Проверка возможности входа по невалидному рефреш токену")
    public void reLoginExpiredTokenTest(){

    }
}
