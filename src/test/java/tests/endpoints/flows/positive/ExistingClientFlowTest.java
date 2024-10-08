package tests.endpoints.flows.positive;

import io.qameta.allure.Feature;
import models.mbackend.response.profile.MeResponseModel;
import org.testng.annotations.Test;
import steps.ApiSteps;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;

public class ExistingClientFlowTest extends ApiSteps {


    @Feature("Флоу прохождения онбординга")
    @Test(description = "Проверка возможности прохождения онбординг флоу существующим клиентом по паролю")
    public void existingClientFlowTest() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException,
            IOException {


        mobileBackendSteps()
                .startOnboardingFlow();

        flowsSteps()
                .sendPhoneNumber(getMainCreds(), mobileBackendSteps().getUuid(), mobileBackendSteps().getTaskId())
                .sendOtpCode(getMainCreds())
                .sendPinHash(getMainCreds());

        authSteps()
                .generateAndPublishCertificate(getMainCreds(), flowsSteps().getAccessToken());


        var me =
                mobileBackendSteps()
                        .mobileBackendProcess(PROFILE_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(MeResponseModel.class);

        verify("Номер пользователя при запросе профиля соответсвует номеру введенного на этапе онбординга",
                me.getPhone(), getMainCreds().phoneNumber(), true);


    }

    @Feature("Флоу прохождения онбординга")
    @Test(description = "Проверка возможности прохождения онбординг флоу существующим клиентом c установкой пароля")
    public void existingClientFlowWithPassSetupTest() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException,
            IOException {


        mobileBackendSteps()
                .startOnboardingFlow();

        flowsSteps()
                .sendPhoneNumber(getMainCreds(), mobileBackendSteps().getUuid(), mobileBackendSteps().getTaskId())
                .sendOtpCode(getMainCreds())
                .sendPinHash(getMainCreds());

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


