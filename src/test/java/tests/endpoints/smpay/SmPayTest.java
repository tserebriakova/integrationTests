package tests.endpoints.smpay;

import io.qameta.allure.Feature;
import models.mbackend.response.cards.card.CardsResponseModel;
import org.testng.annotations.Test;
import steps.ApiSteps;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;

import static enums.CardStatus.ACTIVE;
import static enums.TransactionDirections.OXI_PAN_ID_MOBILE;

@Test(groups={"auth-client"})
public class SmPayTest extends ApiSteps {

    @Feature("ПЛАТЕЖИ Simple Payments")
    @Test(description = "Проверка возможности пополнения мобильного используя PAN_ID ")
    public void simplePaymentsPanIdTest() throws NoSuchAlgorithmException,
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
                        .generateIntent(activeCards, OXI_PAN_ID_MOBILE, flowsSteps().getUserId(), 100);

        intent.setCredit_dst_id("444507222161");
//        intent.setDebit_src_id("5406371576856094");
        PrivateKey privateKey = authSteps().getPrivateKey();

        smPaySteps()
                .checkFee(intent, authSteps().getAccessToken());
        intent.setFee(53);
        intent.setDebit_amount(100+ 53);

        smPaySteps()
                .makeSmpay(intent, privateKey, authSteps().getAccessToken())
                .checkStatus(smPaySteps().getId(), authSteps().getAccessToken())
                .cancelP2P(2, smPaySteps().getId(), authSteps().getAccessToken());;

        Thread.sleep(10000);
//        smPaySteps().checkStatus(smPaySteps().getId(), authSteps().getAccessToken());
//        smPaySteps().checkStatus(smPaySteps().getId(), authSteps().getAccessToken());
//        smPaySteps().checkStatus(smPaySteps().getId(), authSteps().getAccessToken());
//        while (!Objects.equals(p2pSteps.getState(), "SUCCESS"))
//        {
//                p2pSteps.checkStatus(p2pSteps.getId(), getAuthSteps().getAccessToken());
//        }
//                .cancelP2P(300, p2pSteps.getId(), getAuthSteps().getAccessToken());
//
//        System.out.println("user id: " + flowsSteps.getUserId() + "\n" +
//                "intent: " + intent + "\n" +
//                "id: " + p2pSteps.getId() + "\n" +
//                "status " + p2pSteps.getState() + "\n" + getAuthSteps().getAccessToken() );
    }


//    @Feature("ПЛАТЕЖИ Simple Payments")
//    @Test(description = "Проверка возможности пополнения мобильного используя PAN ")
//    public void simplePaymentsPanTest() throws NoSuchAlgorithmException,
//            SignatureException, InvalidKeyException, InterruptedException {
//
//
//        var cards =
//                mobileBackendSteps()
//                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
//                        .as(CardsResponseModel[].class);
//
//        var activeCards =
//                mobileBackendSteps()
//                        .filterCadsByStatus(cards, ACTIVE);
//
//        var intent =
//                mobileBackendSteps()
//                        .generateIntent(activeCards, OXI_PAN_MOBILE, flowsSteps().getUserId(), 100);
//
//        intent.setCredit_dst_id("380507222161");
//        intent.setDebit_src_id("5406371576856094");
//        PrivateKey privateKey = authSteps().getPrivateKey();
//
////        smPaySteps()
////                .checkFee(intent, authSteps().getAccessToken());
//        intent.setFee(53);
//        intent.setDebit_amount(100+ 53);
//
//        smPaySteps()
//                .makeSmpay(intent, privateKey, authSteps().getAccessToken())
//                .checkStatus(smPaySteps().getId(), authSteps().getAccessToken());
//
//        Thread.sleep(10000);
//        smPaySteps().checkStatus(smPaySteps().getId(), authSteps().getAccessToken());
//
//        smPaySteps().checkStatus(smPaySteps().getId(), authSteps().getAccessToken());
//
//        smPaySteps().checkStatus(smPaySteps().getId(), authSteps().getAccessToken());
//
//    }
}
