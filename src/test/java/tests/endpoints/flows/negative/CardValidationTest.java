package tests.endpoints.flows.negative;

import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import steps.ApiSteps;

import java.io.IOException;


public class CardValidationTest extends ApiSteps {

    @Feature("Валидация данных карты в рекавери: отправка пустых значений по карте")
    @Test(description = "Проверка валидации введенных данных карты")
    void cardValidationEmptyDataTest() throws IOException {


        mobileBackendSteps()
                .startOnboardingFlow();

        flowsSteps()
                .sendPhoneNumber(getMainCreds(), mobileBackendSteps().getUuid(), mobileBackendSteps().getTaskId())
                .sendOtpCode(getMainCreds())
                .requestPinRecovery()
                .sendWrongCardDetailsForRecoveryTest("","","","",
                        202);

        verify("счетчик ошибок", flowsSteps().getErrorsBlock().length, 4, true);



    }

    @Feature("Валидация данных карты в рекавери: отправка не валидного exp месяца карты")
    @Test(description = "Проверка валидации введенных данных карты")
    void cardValidationWrongMonthTest() throws IOException {

        mobileBackendSteps()
                .startOnboardingFlow();

        flowsSteps()
                .sendPhoneNumber(getMainCreds(), mobileBackendSteps().getUuid(), mobileBackendSteps().getTaskId())
                .sendOtpCode(getMainCreds())
                .requestPinRecovery()
                .sendWrongCardDetailsForRecoveryTest("5248723331825776", "24", "14",
                        "333", 202);

        verify("счетчик ошибок", flowsSteps().getErrorsBlock().length, 1, true);


    }

    @Feature("Валидация данных карты в рекавери: отправка не валидного exp года карты")
    @Test(description = "Проверка валидации введенных данных карты")
    void cardValidationWrongYearTest() throws IOException {

        mobileBackendSteps()
                .startOnboardingFlow();

        flowsSteps()
                .sendPhoneNumber(getMainCreds(), mobileBackendSteps().getUuid(), mobileBackendSteps().getTaskId())
                .sendOtpCode(getMainCreds())
                .requestPinRecovery()
                .sendWrongCardDetailsForRecoveryTest("5248723331825776", "20", "12",
                        "333", 202);

        verify("счетчик ошибок", flowsSteps().getErrorsBlock().length, 1, true);


    }

    @Feature("Валидация данных карты в рекавери: отправка 3х неверных данных карт другого пользователя")
    @Test(description = "Проверка валидации введенных данных карты")
    void cardValidationTrialsTest() throws IOException {


        mobileBackendSteps()
                .startOnboardingFlow();

        flowsSteps()
                .sendPhoneNumber(getMainCreds(), mobileBackendSteps().getUuid(), mobileBackendSteps().getTaskId())
                .sendOtpCode(getMainCreds())
                .requestPinRecovery()
                .sendCardDetailsForRecoveryTest(getOtherCreds(), 200)
                .sendCardDetailsForRecoveryTest(getOtherCreds(), 200)
                .sendCardDetailsForRecoveryTest(getOtherCreds(), 200);

        verify("процесс флоу ожидается законченным", flowsSteps().isFinished(), true, true);
        verify("процесс флоу ожидается не успешным", flowsSteps().isSuccess(), false, true);
        verify("результат = invalid_recovery_data", flowsSteps().getResultId(), "invalid_recovery_data", true);


    }

    @Feature("Валидация данных карты в рекавери: отправка заблокированной карты")
    @Test(description = "Проверка валидации введенных данных карты")
    void cardValidationLockedCardTest() throws IOException {


        mobileBackendSteps()
                .startOnboardingFlow();

        flowsSteps()
                .sendPhoneNumber(getMainCreds(), mobileBackendSteps().getUuid(), mobileBackendSteps().getTaskId())
                .sendOtpCode(getMainCreds())
                .requestPinRecovery()
                .sendWrongCardDetailsForRecoveryTest("5248721589371253", "24", "01",
                        "600", 200);

        verify("счетчик ошибок", flowsSteps().getErrorsBlock().length, 1, true);


    }

}
