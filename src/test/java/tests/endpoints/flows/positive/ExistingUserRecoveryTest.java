package tests.endpoints.flows.positive;

import io.qameta.allure.Feature;
import models.mbackend.response.profile.MeResponseModel;
import org.testng.annotations.Test;
import steps.ApiSteps;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;

public class ExistingUserRecoveryTest extends ApiSteps {


    @Feature("Флоу прохождения онбординга при восстановлении пароля")
    @Test(description = "Проверка возможности прохождения рекавери флоу")
    void existingClientRecoveryTest() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, IOException {


        mobileBackendSteps()
                .startOnboardingFlow();

        flowsSteps()
                .sendPhoneNumber(getMainCreds(), mobileBackendSteps().getUuid(), mobileBackendSteps().getTaskId())
                .sendOtpCode(getMainCreds())
                .requestPinRecovery()
                .sendCardDetailsForRecoveryTest(getMainCreds(), 200)
                .sendOtpCode(getMainCreds())
                .setNewPinHash(getMainCreds());

        authSteps()
                .generateAndPublishCertificate(getMainCreds(), flowsSteps().getAccessToken());

        var me =
                mobileBackendSteps()
                        .mobileBackendProcess(PROFILE_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(MeResponseModel.class);

        verify("Номер пользователя при запросе профиля соответсвует номеру введенного на этапе онбординга",
                me.getPhone(), getMainCreds().phoneNumber(), true);

    }

}
