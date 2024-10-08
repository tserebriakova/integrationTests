package tests.endpoints.p2p.uapi;

import io.qameta.allure.Feature;
import models.mbackend.response.cards.card.CardsResponseModel;
import org.testng.annotations.Test;
import steps.ApiSteps;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import static enums.CardStatus.ACTIVE;
import static enums.TransactionDirections.OXI_PAN_ID_OXI_PAN;
@Test(groups={"auth-client"})
public class P2PProdTest extends ApiSteps {

    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности перевода с ОКСИ - ОКСИ (по PAN)")
    public void p2pOxiOxiProdTest() throws NoSuchAlgorithmException,
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
                        .generateIntent(activeCards, OXI_PAN_ID_OXI_PAN, flowsSteps().getUserId(), 1);

        intent.setFee(p2pSteps().getFee());
        intent.setCredit_dst_id(getPanNumber());

        p2pSteps()
                .checkFee(intent, authSteps().getAccessToken())
                .makeP2P(intent, authSteps().getPrivateKey(), authSteps().getAccessToken())
                .checkStatus(p2pSteps().getId(), authSteps().getAccessToken());

        Thread.sleep(10000);
        p2pSteps().checkStatus(p2pSteps().getId(), authSteps().getAccessToken());

        p2pSteps().checkStatus(p2pSteps().getId(), authSteps().getAccessToken());

        p2pSteps().checkStatus(p2pSteps().getId(), authSteps().getAccessToken());

// NEED TO WAIT 10 SEC AND IF status != CREATED == ASSERT FAIL
//        while (!Objects.equals(p2pSteps.getState(), "SUCCESS") || !Objects.equals(p2pSteps.getState(), "FAILED"))
//        {
//            p2pSteps.checkStatus(p2pSteps.getId(), getAuthSteps().getAccessToken());
//        }
//                .cancelP2P(300, p2pSteps.getId(), getAuthSteps().getAccessToken());


//        System.out.println("user id: " + flowsSteps.getUserId() + "\n" +
//                "intent: " + intent + "\n" +
//                "id: " + p2pSteps.getId() + "\n" +
//                "status " + p2pSteps.getState() + "\n" + getAuthSteps().getAccessToken() );
    }
}
