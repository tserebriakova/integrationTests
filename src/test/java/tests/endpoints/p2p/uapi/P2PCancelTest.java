package tests.endpoints.p2p.uapi;

import io.qameta.allure.Feature;
import models.mbackend.response.cards.card.CardsResponseModel;
import org.testng.annotations.Test;
import steps.ApiSteps;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;

import static enums.CardStatus.ACTIVE;
import static enums.TransactionDirections.OXI_PAN_ID_OXI_PAN_ID;

@Test(groups = {"auth-client"})
public class P2PCancelTest extends ApiSteps {

    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности отмены платежа через 9 секунд")
    public void p2pCancelBeforeTest() throws  NoSuchAlgorithmException,
             SignatureException, InvalidKeyException, InterruptedException {


        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);

        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);


        var intent =
                mobileBackendSteps()
                        .generateIntent(activeCards, OXI_PAN_ID_OXI_PAN_ID, flowsSteps().getUserId(), 182);


        PrivateKey privateKey = authSteps().getPrivateKey();

        p2pSteps()
                .checkFee(intent, authSteps().getAccessToken());
        intent
                .setFee(p2pSteps().getFee());

        p2pSteps().makeP2P(intent, privateKey, authSteps().getAccessToken())
                .checkStatus(p2pSteps().getId(), authSteps().getAccessToken());

        var cancelStatus =
                p2pSteps()
                        .cancelP2P(9, p2pSteps().getId(), authSteps().getAccessToken());

        verify("Статус выполнения запроса на отмену", cancelStatus.isCancelResponse(), true, true );

        var paymentStatus =
                p2pSteps()
                        .checkStatus(p2pSteps().getId(), authSteps().getAccessToken());

        verify("Статус платежа = отменен", paymentStatus.getPaymentStatus(), "CANCELED", true );

    }

    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности отмены платежа после 10 секунд")
    public void p2pCancelAfterTest() throws NoSuchAlgorithmException,
           SignatureException, InvalidKeyException, InterruptedException {


        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);

        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);


        var intent =
                mobileBackendSteps()
                        .generateIntent(activeCards, OXI_PAN_ID_OXI_PAN_ID, flowsSteps().getUserId(), 182);


        PrivateKey privateKey = authSteps().getPrivateKey();

        p2pSteps()
                .checkFee(intent, authSteps().getAccessToken());
        intent
                .setFee(p2pSteps().getFee());

        p2pSteps()
                .makeP2P(intent, privateKey, authSteps().getAccessToken())
                .checkStatus(p2pSteps().getId(), authSteps().getAccessToken());

        var cancelStatus = p2pSteps().cancelP2P(12, p2pSteps().getId(), authSteps().getAccessToken());

        verify("Статус выполнения запроса на отмену", cancelStatus.isCancelResponse(), false, true );

        var paymentStatus = p2pSteps()
                .checkStatus(p2pSteps().getId(), authSteps().getAccessToken());

        verify("Статус платежа = отменен", paymentStatus.getPaymentStatus(), "CANCELED", false );

    }
}
