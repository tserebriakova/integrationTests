package tests.endpoints.flows.positive;

import io.qameta.allure.Feature;
import models.mbackend.response.profile.MeResponseModel;
import org.testng.annotations.Test;
import steps.ApiSteps;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;

public class NewUserFlowTest extends ApiSteps {

    @Feature("Флоу прохождения онбординга")
    @Test(description = "Проверка возможности прохождения онбординг флоу НЕ клиентом банка")
    void newUserFlowTest() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, IOException {

        var phone = "38063"+ gen();

        mobileBackendSteps()
                .startOnboardingFlow();

        flowsSteps()
                .sendPhoneNumberString(phone, mobileBackendSteps().getUuid(), mobileBackendSteps().getTaskId())
                .sendOtpCode(getMainCreds())
                .setNewPinHash(getMainCreds());

        authSteps()
                .generateAndPublishCertificateNotClient(phone, flowsSteps().getAccessToken());

        var me =
                mobileBackendSteps()
                        .mobileBackendProcess(PROFILE_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(MeResponseModel.class);

        verify("Номер пользователя при запросе профиля соответсвует номеру введенного на этапе онбординга",
                me.getPhone(), phone, true);
    }


}
