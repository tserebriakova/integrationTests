package tests.endpoints.flows.negative;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.testng.annotations.Test;
import steps.ApiSteps;


public class OpenInvalidOrderStepTest extends ApiSteps {
    

    @Test(description = "Попытка открыть невалидный шаг, находясь на шаге ввода номера телефона",
            dataProvider = "getStepEnterPhoneValues")
    public void skipEnterPhone(String steps) throws JsonProcessingException {

        mobileBackendSteps()
                .startOnboardingFlow();

        flowsSteps()
                .setUuid(mobileBackendSteps().getUuid());

        flowsSteps()
                .setTaskId(mobileBackendSteps().getTaskId());

        flowsSteps()
                .sendWrongStep(steps, 202);


    }

    @Test(description = "Попытка невалидный открыть шаг, находясь на шаге ввода ОТП кода",
            dataProvider = "getStepOtpValues")
    public void skipEnterOtp(String steps) throws JsonProcessingException {


        mobileBackendSteps()
                .startOnboardingFlow();

        flowsSteps()
                .sendPhoneNumber(getMainCreds(), mobileBackendSteps().getUuid(), mobileBackendSteps().getTaskId())
                .sendWrongStep(steps, 202);

    }


    @Test(description = "Попытка открыть невалидный шаг, находясь на шаге ввода ПИН кода приложения",
            dataProvider = "getStepPinValues")
    public void skipEnterPin(String steps) throws JsonProcessingException {


        mobileBackendSteps()
                .startOnboardingFlow();

        flowsSteps()
                .sendPhoneNumber(getMainCreds(), mobileBackendSteps().getUuid(), mobileBackendSteps().getTaskId())
                .sendOtpCode(getMainCreds())
                .sendWrongStep(steps, 202);

    }

    @Test(description = "Попытка открыть невалидный шаг, находясь на шаге ввода данных карты для восстановления",
            dataProvider = "getStepRecoveryValues")
    public void skipRecovery(String steps) throws JsonProcessingException {

        mobileBackendSteps()
                .startOnboardingFlow();

        flowsSteps()
                .sendPhoneNumber(getMainCreds(), mobileBackendSteps().getUuid(), mobileBackendSteps().getTaskId())
                .sendOtpCode(getMainCreds())
                .requestPinRecovery()
                .sendWrongStep(steps, 202);
    }

    @Test(description = "Попытка открыть невалидный шаг, находясь на шаге установки ПИН кода приложения",
            dataProvider = "getStepInitPinValues")
    public void skipInitPin(String steps) throws JsonProcessingException {

        mobileBackendSteps()
                .startOnboardingFlow();

        flowsSteps()
                .sendPhoneNumber(getMainCreds(), mobileBackendSteps().getUuid(), mobileBackendSteps().getTaskId())
                .sendOtpCode(getMainCreds())
                .requestPinRecovery()
                .sendCardDetailsForRecoveryTest(getMainCreds(), 200)
                .sendOtpCode(getMainCreds())
                .sendWrongStep(steps, 202);
    }

}
