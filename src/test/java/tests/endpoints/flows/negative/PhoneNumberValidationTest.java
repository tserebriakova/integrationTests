package tests.endpoints.flows.negative;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import steps.ApiSteps;


public class PhoneNumberValidationTest extends ApiSteps {


    @Feature("Валидация номера телефона")
    @Test(description = "Проверка ввода невалидных номеров - {phone}",  dataProvider = "getWrongPhoneNumbers")
    void phoneNumberValidationTest(String phone) throws JsonProcessingException {

        mobileBackendSteps()
                .startOnboardingFlow();

        flowsSteps()
                .sendWrongPhoneNumber("333", 202, mobileBackendSteps().getUuid(), mobileBackendSteps().getTaskId())
                .sendWrongPhoneNumber(phone, 202, flowsSteps().getUuid(), flowsSteps().getTaskId());

        verify("счетчик ошибок", flowsSteps().getErrorsBlock().length, 1, true);

    }
    @Feature("Валидация номера телефона")
    @Test(description = "Проверка ввода номеров украинских операторов - {phone}",  dataProvider = "getPhoneNumbers")
    void phoneUkrNumberValidationTest(String phone) throws JsonProcessingException {

        mobileBackendSteps()
                .startOnboardingFlow();

        flowsSteps()
                .sendWrongPhoneNumber(phone, 200, mobileBackendSteps().getUuid(), mobileBackendSteps().getTaskId());

        verify("счетчик ошибок", flowsSteps().getStep(), "otp_input", true);


    }
}