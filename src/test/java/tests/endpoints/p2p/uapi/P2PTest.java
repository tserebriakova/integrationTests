package tests.endpoints.p2p.uapi;

import io.qameta.allure.Feature;
import models.mbackend.response.cards.card.CardsResponseModel;
import org.testng.annotations.Test;
import steps.ApiSteps;

import java.io.IOException;
import java.security.*;
import java.util.List;

import static enums.CardStatus.ACTIVE;
import static enums.TransactionDirections.OXI_PAN_ID_OXI_PAN_ID;
@Test(groups={"auth-client"})
public class P2PTest extends ApiSteps {

    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности перевода с ОКСИ - ОКСИ (по panId")
    public void p2pOxiOxiPanIdTest() throws NoSuchAlgorithmException,
           SignatureException, InvalidKeyException, InterruptedException {


        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);

        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);

        var pans = List.of
                ("5248723337140188");

        var intent =
                mobileBackendSteps()
                        .generateIntent(activeCards, OXI_PAN_ID_OXI_PAN_ID, flowsSteps().getUserId(), 182);

        intent.setFee(p2pSteps().getFee());

        PrivateKey privateKey = authSteps().getPrivateKey();

        p2pSteps()
                .checkFee(intent, authSteps().getAccessToken())
                .makeP2P(intent, privateKey, authSteps().getAccessToken())
                .checkStatus(p2pSteps().getId(), authSteps().getAccessToken());

        Thread.sleep(10000);
        p2pSteps()
                .checkStatus(p2pSteps().getId(), authSteps().getAccessToken());

        p2pSteps()
                .checkStatus(p2pSteps().getId(), authSteps().getAccessToken());

        p2pSteps()
                .checkStatus(p2pSteps().getId(), authSteps().getAccessToken());

        p2pSteps()
                .cancelP2P(3, p2pSteps().getId(), authSteps().getAccessToken());

//        while (!Objects.equals(p2pSteps.getState(), "SUCCESS"))
//        {
//                p2pSteps.checkStatus(p2pSteps.getId(), getAuthSteps().getAccessToken());
//        }
//                .cancelP2P(300, p2pSteps.getId(), getAuthSteps().getAccessToken());


//        System.out.println("user id: " + flowsSteps.getUserId() + "\n" +
//                "intent: " + intent + "\n" +
//                "id: " + p2pSteps.getId() + "\n" +
//                "status " + p2pSteps.getState() + "\n" + getAuthSteps().getAccessToken() );
    }

    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности перевода с ОКСИ PANID - ОКСИ PAN")
    public void p2pOxiPanIdOxiPanTest() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException,
            IOException, SignatureException, InvalidKeyException, InterruptedException {

    }
    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности перевода с ОКСИ PAN - ОКСИ PAN")
    public void p2pOxiPanOxiPanTest() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException,
            IOException, SignatureException, InvalidKeyException, InterruptedException {

    }


}



