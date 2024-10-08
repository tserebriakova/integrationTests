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

@Test(groups = {"auth-client"})
public class SmProdCycleTest extends ApiSteps {

    int expectedRegularFee = 200;
    int amount = 100;

    @Feature("Проверка п2м на проде")
    @Test(description = "Пополнение мобильного со СВОЕЙ OXY (panId)")
    public void smOxyPanIdMobile() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {

        smPaySteps().setFee(0);
        smPaySteps().setIntent_debit(0);
        smPaySteps().setCredit(0);
        smPaySteps().setDisplay_debit(0);

        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);

        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);

        var intent =
                mobileBackendSteps()
                        .generateIntent(activeCards, OXI_PAN_ID_MOBILE, flowsSteps().getUserId(), amount);

        intent.setCredit_dst_id("380671234455");
        PrivateKey privateKey = authSteps().getPrivateKey();

        smPaySteps()
                .checkFee(intent, authSteps().getAccessToken());

        var feeResult = smPaySteps().getFee();
        var intentDebit = smPaySteps().getIntent_debit();
        var displayDebit = smPaySteps().getDisplay_debit();
        var credit = smPaySteps().getCredit();
        verify("fee", feeResult, expectedRegularFee, true);
        verify("debit", intentDebit, amount, true);
        verify("display debit", displayDebit, amount+expectedRegularFee, true);
        verify("credit", credit, amount, true);
        intent.setFee(feeResult);
        intent.setDebit_amount(intentDebit);
        smPaySteps()
                .makeSmpay(intent, privateKey,authSteps().getAccessToken());
    }

}
