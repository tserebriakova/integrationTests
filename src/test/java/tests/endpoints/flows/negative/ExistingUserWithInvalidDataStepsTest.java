package tests.endpoints.flows.negative;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import steps.ApiSteps;


public class ExistingUserWithInvalidDataStepsTest extends ApiSteps {


    @Feature("Флоу прохождения онбординга")
    @Test(description = "Прохождение онбординга с попыткой ввода невалидных и валидных данных в шагах")
    public void existingUserWithInvalidDataEntryTest() throws JsonProcessingException {

                mobileBackendSteps()
                .startOnboardingFlow();

        flowsSteps()
                .sendWrongPhoneNumber("0123333333", 202, mobileBackendSteps().getUuid(), mobileBackendSteps().getTaskId())
                .sendPhoneNumber(getMainCreds(), flowsSteps().getUuid(), flowsSteps().getTaskId())
                .goBackFromOtp()
                .sendPhoneNumber(getMainCreds(), flowsSteps().getUuid(), flowsSteps().getTaskId())
                .sendWrongOtpCode("123123", 200)
                .goBackFromOtp()
                .sendPhoneNumber(getMainCreds(), flowsSteps().getUuid(), flowsSteps().getTaskId())
                .sendOtpCode(getMainCreds())
                .sendWrongPinHash("2222")
                .requestPinRecovery()
                .sendCardDetailsForRecoveryTest(getWrongCreds(),202)
                .goBackFromRecovery()
                .sendWrongPinHash("4444")
                .requestPinRecovery()
                .sendCardDetailsForRecoveryTest(getMainCreds(),200)
                .sendWrongOtpCode("555555", 200)
                .goBackFromOtpToRecovery()
                .sendCardDetailsForRecoveryTest(getWrongCreds(), 202)
                .sendCardDetailsForRecoveryTest(getMainCreds(), 200)
                .sendWrongOtpCode("333333", 200)
                .goBackFromOtpToRecovery()
                .goBackFromRecovery()
                .sendPinHash(getMainCreds());
    }
}
