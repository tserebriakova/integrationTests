package tests.endpoints.flows.negative;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import steps.ApiSteps;

public class LoginWrongToken extends ApiSteps {

    @Feature("Попытка использования Access Token полученного после enter_pin на mbackend")
    @Test(description = "Прохождение онбординга с попыткой ввода невалидных и валидных данных в шагах")
    public void existingUserWithInvalidDataEntryTest() throws JsonProcessingException {

        mobileBackendSteps()
                .startOnboardingFlow();

        var accessToken = flowsSteps()
                .sendPhoneNumber(getMainCreds(), mobileBackendSteps().getUuid(), mobileBackendSteps().getTaskId())
                .sendOtpCode(getMainCreds())
                .sendPinHash(getMainCreds())
                .getAccessToken();

        mobileBackendSteps()
                .mobileBackendProcess(CARDS_METHOD_PATH, accessToken, 401);

        }
}
