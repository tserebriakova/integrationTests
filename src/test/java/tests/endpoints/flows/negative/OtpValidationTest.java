package tests.endpoints.flows.negative;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import steps.ApiSteps;


public class OtpValidationTest extends ApiSteps {


    @Feature("Валидация ОТП")
    @Test(description = "Проверка валидации ввода ОТП кода")
    public void otpValidationTest() throws JsonProcessingException {


        mobileBackendSteps()
                .startOnboardingFlow();

        flowsSteps()
                .sendPhoneNumber(getMainCreds(), mobileBackendSteps().getUuid(), mobileBackendSteps().getTaskId())
                .sendWrongOtpCode("111111", 200);

        verify("счетчик ошибок", flowsSteps().getErrorsBlock().length, 1, true );

        flowsSteps()
                .sendWrongOtpCode("666666",200);

        verify("счетчик ошибок", flowsSteps().getErrorsBlock().length, 2, true );

        flowsSteps()
                .sendWrongOtpCode("12345",202);

        verify("счетчик ошибок", flowsSteps().getErrorsBlock().length, 1, true );

    }

}
