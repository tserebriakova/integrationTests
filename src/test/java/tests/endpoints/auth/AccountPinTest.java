package tests.endpoints.auth;

import io.qameta.allure.Feature;
import models.auth.request.AccountPin;
import models.mbackend.response.profile.MeResponseModel;
import org.testng.annotations.Test;
import steps.ApiSteps;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

@Test(groups = {"auth-client"})
public class AccountPinTest extends ApiSteps {

    @Feature("Смена Пароля приложения")
    @Test(description = "Проверка возможности смены пароля приложения")
    public void changeAccountPinTest() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, IOException {

        var changeAccountPin =
                authSteps().
                        changeAccountPin(authSteps().getAccessToken(), new AccountPin("1212"), 200);
    }

    @Feature("Смена Пароля приложения")
    @Test(description = "Проверка возможности смены пароля приложения и вход с рефреш токеном")
    public void changeAccountPinTest2() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, IOException, SignatureException, InvalidKeyException {
        var PROFILE_METHOD_PATH = "/me";

        var changeAccountPin =
                authSteps().
                        changeAccountPin(authSteps().getAccessToken(), new AccountPin("1212"), 200);
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

}
