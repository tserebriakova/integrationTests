package steps.p2p;

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
public class P2pSteps extends P2PBaseSteps {

    private String baseUrl;
    private String xVersion;

    @Step("Получение суммы комиссии для платежа")
    public P2pSteps checkFee(IntentModel intent, String accessToken) {

        var res = postP2pCheck("/check", mainP2PRequest(baseUrl), intent, 200, accessToken, xVersion).as(CheckResponseModel.class);
        setFee(res.getFee());
        setDisplay_debit(res.getDisplay_debit());
        setCredit(res.getCredit());
        setIntent_debit(res.getIntent_debit());

        return this;
    }
    //TODO
    @Step("Получение суммы комиссии для платежа")
    public P2pSteps checkWrongFee(IntentModel intent, String accessToken, int statusCode) {

        var res = postP2pCheck("/check", mainP2PRequest(baseUrl), intent, statusCode, accessToken, xVersion).as(CheckResponseModel.class);
        setFee(res.getFee());
        setDisplay_debit(res.getDisplay_debit());
        setCredit(res.getCredit());
        setIntent_debit(res.getIntent_debit());

        return this;
    }

    @Step("Создание платежа without 3DS")
    public P2pSteps makeP2P(IntentModel intent, PrivateKey privateKey, String accessToken) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {

        var sign = sign(privateKey, encodeBase64(intent.toString().replaceAll("[\\n\\t ]", "")));
//        var sign = "asdasdas";
        var requestModel = new PaymentsRequestModel(sign, intent);

        var res = postMakeP2p("/payments", mainP2PRequest(baseUrl), requestModel, 200, accessToken, xVersion).as(PaymentsResponseModel.class);
        setState(res.getStatus());
        setId(res.getId());
        System.out.println("sign:" + sign);
        System.out.println("intent : " + encodeBase64(intent.toString().replaceAll("[\\n\\t ]", "")));
        System.out.println("P2P RESPONSE: " + res.toString());
        return this;
    }

    @Step("Создание платежа with 3DS")
    public P2pSteps makeP2Pwith3Ds(IntentModel intent, PrivateKey privateKey, String accessToken) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {

        var sign = sign(privateKey, encodeBase64(intent.toString().replaceAll("[\\n\\t ]", "")));
        var requestModel = new PaymentsRequestModel3Ds(sign, intent);

        var res = postMakeP2p3Ds("/payments", mainP2PRequest(baseUrl), requestModel, 200, accessToken, xVersion).as(PaymentsResponseModel.class);
        setState(res.getStatus());
        setId(res.getId());
        setRequired_3ds(res.isRequired_3ds());
        System.out.println("sign:" + sign);
        System.out.println("intent : " + encodeBase64(intent.toString().replaceAll("[\\n\\t ]", "")));
        System.out.println("P2P RESPONSE: " + res.toString());
        return this;
    }

    @Step("Отмена платежа через {0} секунд")
    public P2pSteps cancelP2P(Integer seconds, String orderId, String accessToken) throws InterruptedException {

        var requestModel = new CancelPaymentRequestModel(orderId);
        Thread.sleep(seconds*1000);
        CancelPaymentResponseModel res = patchCancelPayment("/cancel-payment", mainP2PRequest(baseUrl), requestModel,
                200, accessToken, xVersion).as(CancelPaymentResponseModel.class);
        setCancelResponse(res.isWas_canceled());
        System.out.println("CANCEL RESPONSE" + res.toString());

        return this;
    }

    @Step("Проверка статуса платежа {0} раз, через каждые 500мс")
    public P2pSteps checkStatus(String orderId, String accessToken) throws InterruptedException {
            var res = getP2pStatus("/payments/" + orderId + "", mainP2PRequest(baseUrl),
                    200, accessToken, xVersion).as(CheckStatusResponseModel.class);
            setState(res.getStatus());
            System.out.println("CURRENT STATUS " + res.getStatus() + "| ACTION_URL: " + res.getUser_action_url());
            setPaymentStatus( res.getStatus());
            setUserActionUrl(res.getUser_action_url());
            Thread.sleep(500);


        return this;
    }

}
