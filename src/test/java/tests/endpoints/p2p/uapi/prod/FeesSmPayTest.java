package tests.endpoints.p2p.uapi.prod;

import io.qameta.allure.Feature;
import models.mbackend.response.cards.card.CardsResponseModel;
import org.testng.annotations.Test;
import steps.ApiSteps;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.util.List;

import static enums.CardStatus.ACTIVE;
import static enums.TransactionDirections.OXI_PAN_ID_MOBILE;
import static enums.TransactionDirections.OXI_PAN_MOBILE;

@Test(groups = {"auth-client"})
public class FeesSmPayTest extends ApiSteps {
    String otherMaster = "5375414104989800";
    String otherVisa = "4441114454395391";
    String myPan = "5248722559371257";
    List<String> otherPans = List.of
            ("5248723348113679", "5248723358113676");
    List<String> otherPanIds =  List.of
            ("1101000000101662463", "1101000000101662513");
    int expectedRegularFee = 200;
    int amount = 100;
    String phone = "380507222161";

    @Feature("ПЛАТЕЖИ Simple Payments")
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

        intent.setCredit_dst_id(phone);
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

    @Feature("ПЛАТЕЖИ Simple Payments")
    @Test(description = "Пополнение мобильного со СВОЕЙ OXY (PAN)")
    public void smOxyPanMobile() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {

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
                        .generateIntent(activeCards, OXI_PAN_MOBILE, flowsSteps().getUserId(), amount);

        intent.setCredit_dst_id(phone);
        intent.setDebit_src_id(myPan);
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

    @Feature("ПЛАТЕЖИ Simple Payments")
    @Test(description = "Пополнение мобильного с чужой OXY (PAN)")
    public void otherOxyPanMobile() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InterruptedException {

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
                        .generateIntent(activeCards, OXI_PAN_MOBILE, flowsSteps().getUserId(), amount);

        intent.setCredit_dst_id(phone);
        intent.setDebit_src_id(otherPans.get(0));
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
        smPaySteps().makeP2Pwith3Ds(intent, privateKey, authSteps().getAccessToken());
        verify("Required 3DS = TRUE", smPaySteps().isRequired_3ds(), true, true);
        for (int i = 0; i < 10; i++) {
            if (smPaySteps().getUserActionUrl() == null){
                smPaySteps().checkStatus(smPaySteps().getId(), authSteps().getAccessToken());
            }
            else break;
        }
        verify("3DS ссылка получена", smPaySteps().getUserActionUrl(), null, false);
    }

    @Feature("ПЛАТЕЖИ Simple Payments")
    @Test(description = "Пополнение мобильного с чужой OXY (PanId)")
    public void otherOxyPanIdMobile() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InterruptedException {

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

        intent.setCredit_dst_id(phone);
        intent.setDebit_src_id(otherPanIds.get(0));
        PrivateKey privateKey = authSteps().getPrivateKey();

        smPaySteps()
                .checkWrongFee(intent, authSteps().getAccessToken(), 422);

//        var feeResult = smPaySteps().getFee();
//        var intentDebit = smPaySteps().getIntent_debit();
//        var displayDebit = smPaySteps().getDisplay_debit();
//        var credit = smPaySteps().getCredit();
//        verify("fee", feeResult, expectedRegularFee, true);
//        verify("debit", intentDebit, amount, true);
//        verify("display debit", displayDebit, amount+expectedRegularFee, true);
//        verify("credit", credit, amount, true);
//        intent.setFee(feeResult);
//        intent.setDebit_amount(intentDebit);
//        smPaySteps().makeP2Pwith3Ds(intent, privateKey, authSteps().getAccessToken());
//        verify("Required 3DS = TRUE", smPaySteps().isRequired_3ds(), true, true);
//        for (int i = 0; i < 10; i++) {
//            if (smPaySteps().getUserActionUrl() == null){
//                smPaySteps().checkStatus(smPaySteps().getId(), authSteps().getAccessToken());
//            }
//            else break;
//        }
//        verify("3DS ссылка получена", smPaySteps().getUserActionUrl(), null, false);
    }

    @Feature("ПЛАТЕЖИ Simple Payments")
    @Test(description = "Пополнение мобильного с чужой VISA")
    public void otherVisa() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InterruptedException {

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
                        .generateIntent(activeCards, OXI_PAN_MOBILE, flowsSteps().getUserId(), amount);

        intent.setCredit_dst_id(phone);
        intent.setDebit_src_id(otherVisa);
        PrivateKey privateKey = authSteps().getPrivateKey();

        smPaySteps()
                .checkFee(intent, authSteps().getAccessToken());

        var feeResult = smPaySteps().getFee();
        var intentDebit = smPaySteps().getIntent_debit();
        var displayDebit = smPaySteps().getDisplay_debit();
        var credit = smPaySteps().getCredit();
        verify("fee", feeResult, expectedRegularFee, true);
        verify("debit", intentDebit, amount + expectedRegularFee, true);
        verify("display debit", displayDebit, amount+expectedRegularFee, true);
        verify("credit", credit, amount, true);
        intent.setDebit_amount(intentDebit);
        intent.setFee(feeResult);

        smPaySteps().makeP2Pwith3Ds(intent, privateKey, authSteps().getAccessToken());
        verify("Required 3DS = TRUE", smPaySteps().isRequired_3ds(), true, true);
        for (int i = 0; i < 10; i++) {
            if (smPaySteps().getUserActionUrl() == null){
                smPaySteps().checkStatus(smPaySteps().getId(), authSteps().getAccessToken());
            }
            else break;
        }
        verify("3DS ссылка получена", smPaySteps().getUserActionUrl(), null, false);
    }
    @Feature("ПЛАТЕЖИ Simple Payments")
    @Test(description = "Пополнение мобильного с чужой MASTER")
    public void otherMaster() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InterruptedException {

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
                        .generateIntent(activeCards, OXI_PAN_MOBILE, flowsSteps().getUserId(), amount);

        intent.setCredit_dst_id(phone);
        intent.setDebit_src_id(otherMaster);
        PrivateKey privateKey = authSteps().getPrivateKey();

        smPaySteps()
                .checkFee(intent, authSteps().getAccessToken());

        var feeResult = smPaySteps().getFee();
        var intentDebit = smPaySteps().getIntent_debit();
        var displayDebit = smPaySteps().getDisplay_debit();
        var credit = smPaySteps().getCredit();
        verify("fee", feeResult, expectedRegularFee, true);
        verify("debit", intentDebit, amount + expectedRegularFee, true);
        verify("display debit", displayDebit, amount+expectedRegularFee, true);
        verify("credit", credit, amount, true);

        intent.setDebit_amount(intentDebit);
        intent.setFee(feeResult);
        smPaySteps().makeP2Pwith3Ds(intent, privateKey, authSteps().getAccessToken());
        verify("Required 3DS = TRUE", smPaySteps().isRequired_3ds(), true, true);
        for (int i = 0; i < 10; i++) {
            if (smPaySteps().getUserActionUrl() == null){
                smPaySteps().checkStatus(smPaySteps().getId(), authSteps().getAccessToken());
            }
            else break;
        }
        verify("3DS ссылка получена", smPaySteps().getUserActionUrl(), null, false);

    }
}