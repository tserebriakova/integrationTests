package steps.smpay;

import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import models.p2p.intent.IntentModel;
import models.p2p.request.payments.CancelPaymentRequestModel;
import models.p2p.request.payments.PaymentsRequestModel;
import models.p2p.request.payments.PaymentsRequestModel3Ds;
import models.p2p.response.check.CheckResponseModel;
import models.p2p.response.payments.CancelPaymentResponseModel;
import models.p2p.response.payments.PaymentsResponseModel;
import models.p2p.response.status.CheckStatusResponseModel;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;

import static utils.GenerateCertificates.encodeBase64;
import static utils.GenerateCertificates.sign;

@AllArgsConstructor
public class SmPaySteps extends SmPayBaseSteps {

    private String baseUrl;
    private String xVersion;


    @Step("Получение суммы комиссии для платежа")
    public SmPaySteps checkFee(IntentModel intent, String accessToken) {
        var res = postSmPayCheck("/check", mainSmPayRequest(baseUrl), intent, 200, accessToken, xVersion).as(CheckResponseModel.class);
        setFee(res.getFee());
        setDisplay_debit(res.getDisplay_debit());
        setCredit(res.getCredit());
        setIntent_debit(res.getIntent_debit());
        return this;
    }
    //ToDO
    @Step("Получение суммы комиссии для платежа")
    public SmPaySteps checkWrongFee(IntentModel intent, String accessToken, int statusCode) {
        var res = postSmPayCheck("/check", mainSmPayRequest(baseUrl), intent, statusCode, accessToken, xVersion).as(CheckResponseModel.class);
        setFee(res.getFee());
        setDisplay_debit(res.getDisplay_debit());
        setCredit(res.getCredit());
        setIntent_debit(res.getIntent_debit());
        return this;
    }

    @Step("Создание платежа p2mobile")
    public SmPaySteps makeSmpay(IntentModel intent, PrivateKey privateKey, String accessToken) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {

        var sign = sign(privateKey, encodeBase64(intent.toString().replaceAll("[\\n\\t ]", "")));
        var requestModel = new PaymentsRequestModel(sign, intent);

        var res = postSmPay("/payments", mainSmPayRequest(baseUrl), requestModel, 200, accessToken, xVersion)
                .as(PaymentsResponseModel.class);
        setState(res.getStatus());
        setId(res.getId());
        setRequired_3ds(res.isRequired_3ds());
        System.out.println("sign: " + sign);
        System.out.println("intent: " + encodeBase64(intent.toString().replaceAll("[\\n\\t ]", "")));
        System.out.println("P2P RESPONSE: " + res.toString());
        return this;
    }

    @Step("Создание платежа с 3DS")
    public SmPaySteps makeP2Pwith3Ds(IntentModel intent, PrivateKey privateKey, String accessToken) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {

        var sign = sign(privateKey, encodeBase64(intent.toString().replaceAll("[\\n\\t ]", "")));
        var requestModel = new PaymentsRequestModel3Ds(sign, intent);

        var res = postSmPay3Ds("/payments", mainSmPayRequest(baseUrl), requestModel, 200, accessToken, xVersion).as(PaymentsResponseModel.class);
        setState(res.getStatus());
        setId(res.getId());
        setRequired_3ds(res.isRequired_3ds());
        System.out.println("sign:" + sign);
        System.out.println("intent : " + encodeBase64(intent.toString().replaceAll("[\\n\\t ]", "")));
        System.out.println("P2P RESPONSE: " + res.toString());
        return this;
    }

    @Step("Отмена платежа через {0} секунд")
    public SmPaySteps cancelP2P(int seconds, String orderId, String accessToken) throws InterruptedException {

        var requestModel = new CancelPaymentRequestModel(orderId);
        Thread.sleep(seconds);
        CancelPaymentResponseModel res = patchCancelSmPay("/cancel-payment", mainSmPayRequest(baseUrl), requestModel,
                200, accessToken, xVersion).as(CancelPaymentResponseModel.class);
        System.out.println("CANCEL RESPONSE" + res.toString());

        return this;
    }

    @Step("Проверка статуса платежа {0} раз, через каждые 500мс")
    public SmPaySteps checkStatus(String orderId, String accessToken) throws InterruptedException {
            var res = getSmPayStatus("/payments/" + orderId + "", mainSmPayRequest(baseUrl),
                    200, accessToken, xVersion).as(CheckStatusResponseModel.class);
            setState(res.getStatus());
        System.out.println("CURRENT STATUS " + res.getStatus() + "| ACTION_URL: " + res.getUser_action_url());
            setUserActionUrl(res.getUser_action_url());
            Thread.sleep(500);


        return this;
    }

}
