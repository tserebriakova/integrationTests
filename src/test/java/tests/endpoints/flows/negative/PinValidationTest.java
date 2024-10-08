package tests.endpoints.flows.negative;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import steps.ApiSteps;


public class PinValidationTest extends ApiSteps {

    @Feature("Валидация пароля приложения")
    @Test(description = "Проверка валидации введенного ПИН кода")
    public void pinValidationTest() throws JsonProcessingException {

        
        mobileBackendSteps()
                .startOnboardingFlow();

        flowsSteps()
                .sendPhoneNumber(getMainCreds(), mobileBackendSteps().getUuid(), mobileBackendSteps().getTaskId())
                .sendOtpCode(getMainCreds())
                .sendWrongPinHash("1234");

        verify("счетчик ошибок", flowsSteps().getErrorsBlock().length, 1, true );

        flowsSteps().
                sendWrongPinHash("");
        verify("счетчик ошибок", flowsSteps().getErrorsBlock().length, 2, true );

        flowsSteps().
                sendWrongPinHash("ASD#$");

        verify("Текущий статус finished = true", flowsSteps().isFinished(), true, true );
        verify("Поле result id = pin_hash_errors_too_much", flowsSteps().getResultId(), "pin_hash_errors_too_much", true );
        verify("Блок messages содержит 1 объект", flowsSteps().getMessages().length, 1, true );
        verify("Блок messages содержит текст = Too many pin errors", flowsSteps().getMessages()[0].getMessage(), "Too many pin errors", true );


    }

}
