package steps.flows;

import enums.Credentials;
import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import models.flows.response.FlowsResponseModel;

import static enums.InputIdParams.*;
import static utils.EncodingUtils.toSha256;

@AllArgsConstructor
public class FlowsSteps extends FlowsBaseSteps {

    private String baseUrl;
    private String xVersion;


    @Step("Отправка номера телефона {credentials.phoneNumber}")
    public FlowsSteps sendPhoneNumber(Credentials credentials, String uuId, String taskId) {
        var data = getFlowsRequestModel(PHONE.id(), credentials.phoneNumber());
        var response = getFlowsCompleteRequest(mainFlowsRequest(baseUrl), data, uuId, taskId, 200, xVersion).as(FlowsResponseModel.class);
        setUuid(response.getUuid());
        setTaskId(response.getTask_id());
        return this;
    }

    //TODO
    @Step
    public FlowsSteps sendPhoneNumberString(String phoneNumber, String uuId, String taskId) {
        var data = getFlowsRequestModel(PHONE.id(), phoneNumber);
        var response = getFlowsCompleteRequest(mainFlowsRequest(baseUrl), data, uuId, taskId, 200, xVersion).as(FlowsResponseModel.class);
        setUuid(response.getUuid());
        setTaskId(response.getTask_id());

        return this;
    }

    //TODO
    @Step("Отправка невалидного номера телефона {phone}")
    public FlowsSteps sendWrongPhoneNumber(String phone, int statusCode, String uuId, String taskId) {
        var data = getFlowsRequestModel(PHONE.id(), phone);
        var response = getFlowsCompleteRequest(mainFlowsRequest(baseUrl), data, uuId, taskId, statusCode, xVersion)
                .as(FlowsResponseModel.class);
        setUuid(response.getUuid());
        setTaskId(response.getTask_id());
        setErrorsBlock(response.getErrors());
        setStep(response.getStep());

        return this;
    }

    @Step("Отправка ОТП кода {credentials.otpCode}")
    public FlowsSteps sendOtpCode(Credentials credentials) {
        var data = getFlowsRequestModel(OTP.id(), credentials.otpCode());
        var response = getFlowsCompleteRequest(mainFlowsRequest(baseUrl), data, getUuid(), getTaskId(),
                200, xVersion).as(FlowsResponseModel.class);
        setUuid(response.getUuid());
        setTaskId(response.getTask_id());
        setErrorsBlock(response.getErrors());
        return this;
    }

    //TODO
    @Step("Отправка невалидного ОТП кода {otp}")
    public FlowsSteps sendWrongOtpCode(String otp, int statusCode) {
        var data = getFlowsRequestModel(OTP.id(), otp);
        var response = getFlowsCompleteRequest(mainFlowsRequest(baseUrl), data, getUuid(), getTaskId(),
                statusCode, xVersion).as(FlowsResponseModel.class);
        setUuid(response.getUuid());
        setTaskId(response.getTask_id());
        setErrorsBlock(response.getErrors());
        return this;
    }

    //TODO
    @Step("Попытка вызвать невалидный шаг {step}")
    public FlowsSteps sendWrongStep(String step, int statusCode) {
        var data = getFlowsRequestModel(step, step);
        var response = getFlowsCompleteRequest(mainFlowsRequest(baseUrl), data, getUuid(), getTaskId(),
                statusCode, xVersion).as(FlowsResponseModel.class);
        setUuid(response.getUuid());
        setTaskId(response.getTask_id());
        return this;
    }

    //TODO
    @Step("Попытка вызвать невалидный шаг {step}")
    public FlowsSteps sendWrongStepEnterPhone(String step, String uuid, String taskId, int statusCode) {
        var data = getFlowsRequestModel(step, step);
        var response = getFlowsCompleteRequest(mainFlowsRequest(baseUrl), data, uuid, taskId, statusCode, xVersion)
                .as(FlowsResponseModel.class);
        setUuid(response.getUuid());
        setTaskId(response.getTask_id());
        return this;
    }

    //TODO
    @Step("Отправка невалидного пина - {credentials}")
    public FlowsSteps sendWrongPinHash(String credentials) {
        var data = getFlowsRequestModel((PIN.id()), toSha256(credentials));
        var response = getFlowsCompleteRequest(mainFlowsRequest(baseUrl), data, getUuid(), getTaskId(),
                200, xVersion).as(FlowsResponseModel.class);
        try {
            setUuid(response.getUuid());
            setTaskId(response.getTask_id());
            setErrorsBlock(response.getErrors());
            setResultId(response.getResult_id());
            setMessages(response.getMessages());
            setSuccess(response.isSuccess());
            setFinished(response.isFinished());
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }

        return this;
    }

    @Step("Отправка валидного ПИН кода - {credentials.passCode}")
    public FlowsSteps sendPinHash(Credentials credentials) {
        var data = getFlowsRequestModel((PIN.id()), toSha256(credentials.passCode()));
        var response = getFlowsCompleteRequest(mainFlowsRequest(baseUrl), data, getUuid(), getTaskId(),
                200, xVersion).as(FlowsResponseModel.class);
        setAccessToken(response.getVars().getAccess_token());
        setUserId(response.getVars().getUser_id());
        setErrorsBlock(response.getErrors());

        return this;
    }

    @Step("Отправка данных карты - {credentials.cardNumber} / {credentials.cardExpYear} / {credentials.cardExpMonth} / {credentials.cardCvv}")
    public FlowsSteps sendCardDetailsForRecoveryTest(Credentials credentials, int statusCode) {
        var data = getFlowsRequestModel(
                RECOVERY_CARD_NUM.id(), credentials.cardNumber(),
                RECOVERY_CARD_EXP_YEAR.id(), credentials.cardExpYear(),
                RECOVERY_CARD_EXP_MONTH.id(), credentials.cardExpMonth(),
                RECOVERY_CARD_CVV.id(), credentials.cardCvv());
        var response = getFlowsCompleteRequest(mainFlowsRequest(baseUrl), data, getUuid(), getTaskId(),
                statusCode, xVersion).as(FlowsResponseModel.class);
        setUuid(response.getUuid());
        setTaskId(response.getTask_id());
        setErrorsBlock(response.getErrors());
        try {
            setFinished(response.isFinished());
            setSuccess(response.isSuccess());
            setMessages(response.getMessages());
            setResultId(response.getResult_id());

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return this;
    }

    @Step("Отправка не валидных данных карты - {cardNumber} / {cardExpYear} / {cardExpMonth} / {cardCvv}")
    public FlowsSteps sendWrongCardDetailsForRecoveryTest(String cardNumber, String cardExpYear, String cardExpMonth,
                                                          String cardCvv, int statusCode) {
        var data = getFlowsRequestModel(
                RECOVERY_CARD_NUM.id(), cardNumber,
                RECOVERY_CARD_EXP_YEAR.id(), cardExpYear,
                RECOVERY_CARD_EXP_MONTH.id(), cardExpMonth,
                RECOVERY_CARD_CVV.id(), cardCvv);
        var response = getFlowsCompleteRequest(mainFlowsRequest(baseUrl), data, getUuid(), getTaskId(),
                statusCode, xVersion).as(FlowsResponseModel.class);
        setUuid(response.getUuid());
        setTaskId(response.getTask_id());
        setErrorsBlock(response.getErrors());
        try {
            setFinished(response.isFinished());
            setSuccess(response.isSuccess());
            setMessages(response.getMessages());
            setResultId(response.getResult_id());

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return this;
    }


    @Step("Установка нового ПИН кода приложения")
    public FlowsSteps setNewPinHash(Credentials pin) {
        var data = getFlowsRequestModel(PIN_INIT.id(), toSha256(pin.passCode()));
        var response = getFlowsCompleteRequest(mainFlowsRequest(baseUrl), data, getUuid(), getTaskId(),
                200, xVersion).as(FlowsResponseModel.class);
        setAccessToken(response.getVars().getAccess_token());

        return this;
    }

    @Step("Вернуться с шага 'Ввод ОТП'")
    public FlowsSteps goBackFromOtp() {
        var data = getFlowsRequestModel(BACK_FROM_OTP.id(), BACK_FROM_OTP.value());
        var response = getFlowsCompleteRequest(mainFlowsRequest(baseUrl), data, getUuid(), getTaskId(),
                200, xVersion).as(FlowsResponseModel.class);
        setUuid(response.getUuid());
        setTaskId(response.getTask_id());
        return this;
    }

    @Step("Запрос восстановления пароля")
    public FlowsSteps requestPinRecovery() {
        var data = getFlowsRequestModel(PIN_RECOVERY.id(), PIN_RECOVERY.value());
        var response = getFlowsCompleteRequest(mainFlowsRequest(baseUrl), data, getUuid(), getTaskId(),
                200, xVersion).as(FlowsResponseModel.class);
        setUuid(response.getUuid());
        setTaskId(response.getTask_id());
        return this;
    }

    @Step("Возврат с шага Рекавери на ввод ПИНа приложения")
    public FlowsSteps goBackFromRecovery() {
        var data = getFlowsRequestModel(BACK_TO_ENTER_PIN.id(),
                BACK_TO_ENTER_PIN.value());
        var response = getFlowsCompleteRequest(mainFlowsRequest(baseUrl), data, getUuid(), getTaskId(),
                200, xVersion).as(FlowsResponseModel.class);
        setUuid(response.getUuid());
        setTaskId(response.getTask_id());
        return this;

    }

    @Step("Возврат с шага Ввод ОТП кода на ввод данных карты")
    public FlowsSteps goBackFromOtpToRecovery() {
        var data = getFlowsRequestModel(BACK_FROM_OTP.id(), BACK_FROM_OTP.value());
        var response = getFlowsCompleteRequest(mainFlowsRequest(baseUrl), data, getUuid(), getTaskId(),
                200, xVersion).as(FlowsResponseModel.class);
        setUuid(response.getUuid());
        setTaskId(response.getTask_id());
        setErrorsBlock(response.getErrors());
        return this;
    }

}
