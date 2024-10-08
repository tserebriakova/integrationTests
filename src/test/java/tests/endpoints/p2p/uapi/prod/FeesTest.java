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
import static enums.TransactionDirections.*;


@Test(groups = {"auth-client"})
public class FeesTest extends ApiSteps {
    String otherMaster = "5375414104989800";
    String otherVisa = "4441114454395391";
    String otherMaster2 = "5375419900909258";
    String otherVisa2 = "4441114101465449";
    String myPan = "5248722559371257";
    String myPanId = "0176000000114636788";
    String otherCountryMaster = "5355850057650002";
    List<String> otherPans = List.of
            ("5248723348113679", "5248723358113676");
    List<String> otherPanIds =  List.of
            ("1101000000101662463", "1101000000101662513");

    int amount = 100;


    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности перевода со своей ОКСИ на свою ОКСИ (panId - panId)")
    public void myOxyPanIdmyOxyPanId() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {

        p2pSteps().setFee(0);
        p2pSteps().setIntent_debit(0);
        p2pSteps().setCredit(0);
        p2pSteps().setDisplay_debit(0);

        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);

        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);


        var intent =
                mobileBackendSteps()
                        .generateIntent(activeCards, FROM_PAN_ID_TO_PAN_ID, flowsSteps().getUserId(), amount);

        intent.setDebit_src_id(myPanId);

        PrivateKey privateKey = authSteps().getPrivateKey();

        p2pSteps()
                .checkFee(intent, authSteps().getAccessToken());

        var feeResult = p2pSteps().getFee();
        var intentDebit = p2pSteps().getIntent_debit();
        var displayDebit = p2pSteps().getDisplay_debit();
        var credit = p2pSteps().getCredit();
        verify("fee", feeResult, 0, true);
        verify("debit", intentDebit, amount, true);
        verify("display debit", displayDebit, amount, true);
        verify("credit", credit, amount, true);

        intent.setDebit_amount(intentDebit);
        intent.setFee(feeResult);

        p2pSteps().makeP2P(intent, privateKey, authSteps().getAccessToken());

    }


    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности перевода со своей ОКСИ на свою ОКСИ (panId - Pan)")
    public void myOxyPanIdOxyPan() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {


        p2pSteps().setFee(0);
        p2pSteps().setIntent_debit(0);
        p2pSteps().setCredit(0);
        p2pSteps().setDisplay_debit(0);

        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);

        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);


        var intent =
                mobileBackendSteps()
                        .generateIntent(activeCards, FROM_PAN_ID_TO_PAN, flowsSteps().getUserId(), amount);
        intent.setCredit_dst_id(myPan);

        PrivateKey privateKey = authSteps().getPrivateKey();

        p2pSteps()
                .checkFee(intent, authSteps().getAccessToken());

        var feeResult = p2pSteps().getFee();
        var intentDebit = p2pSteps().getIntent_debit();
        var displayDebit = p2pSteps().getDisplay_debit();
        var credit = p2pSteps().getCredit();
        verify("fee", feeResult, 0, true);
        verify("debit", intentDebit, amount, true);
        verify("display debit", displayDebit, amount, true);
        verify("credit", credit, amount, true);

        intent.setDebit_amount(intentDebit);
        intent.setFee(feeResult);

        p2pSteps().makeP2P(intent, privateKey, authSteps().getAccessToken());
    }

    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности перевода со своей ОКСИ на свою ОКСИ (Pan - PanId)")
    public void myOxyPanOxyPanId() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {

        p2pSteps().setFee(0);
        p2pSteps().setIntent_debit(0);
        p2pSteps().setCredit(0);
        p2pSteps().setDisplay_debit(0);

        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);

        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);

        var intent =
                mobileBackendSteps()
                        .generateIntent(activeCards, FROM_PAN_TO_PAN_ID, flowsSteps().getUserId(), amount);

        intent.setDebit_src_id(myPan);
        intent.setCredit_dst_id("1101000000101662521");
//        intent.setFee(p2pSteps().getFee());

        PrivateKey privateKey = authSteps().getPrivateKey();

        p2pSteps()
                .checkFee(intent, authSteps().getAccessToken());

        var feeResult = p2pSteps().getFee();
        var intentDebit = p2pSteps().getIntent_debit();
        var displayDebit = p2pSteps().getDisplay_debit();
        var credit = p2pSteps().getCredit();
        verify("fee", feeResult, 0, true);
        verify("debit", intentDebit, amount, true);
        verify("display debit", displayDebit, amount, true);
        verify("credit", credit, amount, true);
        intent.setDebit_amount(intentDebit);
        intent.setFee(feeResult);

        p2pSteps().makeP2P(intent, privateKey, authSteps().getAccessToken());
    }

    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности перевода со своей ОКСИ на свою ОКСИ (Pan - Pan)")
    public void myOxyPanOxyPan() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {

        p2pSteps().setFee(0);
        p2pSteps().setIntent_debit(0);
        p2pSteps().setCredit(0);
        p2pSteps().setDisplay_debit(0);

        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);

        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);



        var intent =
                mobileBackendSteps()
                        .generateIntent(activeCards, FROM_PAN_TO_PAN, flowsSteps().getUserId(), amount);

        intent.setDebit_src_id(myPan);
        intent.setCredit_dst_id("5375551118113677");

//        intent.setFee(p2pSteps().getFee());

        PrivateKey privateKey = authSteps().getPrivateKey();

        p2pSteps()
                .checkFee(intent, authSteps().getAccessToken());
        var feeResult = p2pSteps().getFee();
        var intentDebit = p2pSteps().getIntent_debit();
        var displayDebit = p2pSteps().getDisplay_debit();
        var credit = p2pSteps().getCredit();
        verify("fee", feeResult, 0, true);
        verify("debit", intentDebit, amount, true);
        verify("display debit", displayDebit, amount, true);
        verify("credit", credit, amount, true);
        intent.setDebit_amount(intentDebit);
        intent.setFee(feeResult);

        p2pSteps().makeP2P(intent, privateKey, authSteps().getAccessToken());
    }

    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности перевода со своей ОКСИ на чужую ОКСИ (PanId - Pan)")
    public void myOxyPanIdOtherOxyPan() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {

        p2pSteps().setFee(0);
        p2pSteps().setIntent_debit(0);
        p2pSteps().setCredit(0);
        p2pSteps().setDisplay_debit(0);

        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);

        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);


        var intent =
                mobileBackendSteps()
                        .generateIntent(activeCards, FROM_PAN_ID_TO_PAN, flowsSteps().getUserId(), amount);

        intent.setCredit_dst_id(otherPans.get(0));
//        intent.setFee(p2pSteps().getFee());

        PrivateKey privateKey = authSteps().getPrivateKey();

        p2pSteps()
                .checkFee(intent, authSteps().getAccessToken());
        var feeResult = p2pSteps().getFee();
        var intentDebit = p2pSteps().getIntent_debit();
        var displayDebit = p2pSteps().getDisplay_debit();
        var credit = p2pSteps().getCredit();
        verify("fee", feeResult, 0, true);
        verify("debit", intentDebit, amount, true);
        verify("display debit", displayDebit, amount, true);
        verify("credit", credit, amount, true);
        intent.setDebit_amount(intentDebit);
        intent.setFee(feeResult);

        p2pSteps().makeP2P(intent, privateKey, authSteps().getAccessToken());
    }

    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности перевода со своей ОКСИ на чужую ОКСИ (PanId - PanId)")
    public void myOxyPanIdOtherOxyPanId() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {


        p2pSteps().setFee(0);
        p2pSteps().setIntent_debit(0);
        p2pSteps().setCredit(0);
        p2pSteps().setDisplay_debit(0);

        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);

        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);



        var intent =
                mobileBackendSteps()
                        .generateIntent(activeCards, FROM_PAN_ID_TO_PAN_ID, flowsSteps().getUserId(), amount);

        intent.setDebit_src_id(otherPanIds.get(0));
//        intent.setFee(p2pSteps().getFee());

        PrivateKey privateKey = authSteps().getPrivateKey();

        p2pSteps()
                .checkWrongFee(intent, authSteps().getAccessToken(), 422);

//        var feeResult = p2pSteps().getFee();
//        var intentDebit = p2pSteps().getIntent_debit();
//        var displayDebit = p2pSteps().getDisplay_debit();
//        var credit = p2pSteps().getCredit();
//        verify("fee", feeResult, 0, true);
//        verify("debit", intentDebit, amount, true);
//        verify("display debit", displayDebit, amount, true);
//        verify("credit", credit, amount, true);
//
//        intent.setDebit_amount(intentDebit);
//        intent.setFee(feeResult);
//
//        p2pSteps().makeP2P(intent, privateKey, authSteps().getAccessToken());
    }

    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности перевода со своей ОКСИ на чужую ОКСИ (Pan - Pan)")
    public void myOxyPanOtherOxyPan() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {

        p2pSteps().setFee(0);
        p2pSteps().setIntent_debit(0);
        p2pSteps().setCredit(0);
        p2pSteps().setDisplay_debit(0);

        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);

        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);


        var intent =
                mobileBackendSteps()
                        .generateIntent(activeCards, FROM_PAN_TO_PAN, flowsSteps().getUserId(), amount);

        intent.setCredit_dst_id(myPan);
        intent.setDebit_src_id(otherPans.get(0));

//        intent.setFee(p2pSteps().getFee());

        PrivateKey privateKey = authSteps().getPrivateKey();

        p2pSteps()
                .checkFee(intent, authSteps().getAccessToken());
        var feeResult = p2pSteps().getFee();
        var intentDebit = p2pSteps().getIntent_debit();
        var displayDebit = p2pSteps().getDisplay_debit();
        var credit = p2pSteps().getCredit();
        verify("fee", feeResult, 0, true);
        verify("debit", intentDebit, amount, true);
        verify("display debit", displayDebit, amount, true);
        verify("credit", credit, amount, true);

        intent.setDebit_amount(intentDebit);
        intent.setFee(feeResult);

        p2pSteps().makeP2P(intent, privateKey, authSteps().getAccessToken());
    }

    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности перевода со своей ОКСИ на чужую ОКСИ (Pan - PanId)")
    public void myOxyPanOtherOxyPanId() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {

        p2pSteps().setFee(0);
        p2pSteps().setIntent_debit(0);
        p2pSteps().setCredit(0);
        p2pSteps().setDisplay_debit(0);

        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);

        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);



        var intent =
                mobileBackendSteps()
                        .generateIntent(activeCards, FROM_PAN_TO_PAN_ID, flowsSteps().getUserId(), amount);

        intent.setDebit_src_id(myPan);
        intent.setCredit_dst_id(otherPanIds.get(0));
//        intent.setFee(p2pSteps().getFee());

        PrivateKey privateKey = authSteps().getPrivateKey();

        p2pSteps()
                .checkWrongFee(intent, authSteps().getAccessToken(), 422);
//        var feeResult = p2pSteps().getFee();
//        var intentDebit = p2pSteps().getIntent_debit();
//        var displayDebit = p2pSteps().getDisplay_debit();
//        var credit = p2pSteps().getCredit();
//        verify("fee", feeResult, 0, true);
//        verify("debit", intentDebit, amount, true);
//        verify("display debit", displayDebit, amount, true);
//        verify("credit", credit, amount, true);
//
//        intent.setDebit_amount(intentDebit);
//        intent.setFee(feeResult);
//
//        p2pSteps().makeP2P(intent, privateKey, authSteps().getAccessToken());
    }

    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности перевода со своей ОКСИ на чужой MASTER (panId - PAN)")
    public void myOxyPanIdOtherMaster() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {

        p2pSteps().setFee(0);
        p2pSteps().setIntent_debit(0);
        p2pSteps().setCredit(0);
        p2pSteps().setDisplay_debit(0);

        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);

        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);


        var intent =
                mobileBackendSteps()
                        .generateIntent(activeCards, FROM_PAN_ID_TO_PAN, flowsSteps().getUserId(), amount);

        intent.setDebit_src_id(myPanId);
        intent.setCredit_dst_id(otherMaster);
        PrivateKey privateKey = authSteps().getPrivateKey();

        p2pSteps()
                .checkFee(intent, authSteps().getAccessToken());

        var expectedFee = amount/100 + 500;
        var feeResult = p2pSteps().getFee();
        var intentDebit = p2pSteps().getIntent_debit();
        var displayDebit = p2pSteps().getDisplay_debit();
        var credit = p2pSteps().getCredit();
        verify("fee", feeResult, expectedFee, true);
        verify("debit", intentDebit, amount, true);
        verify("display debit", displayDebit, amount + expectedFee, true);
        verify("credit", credit, amount, true);

        intent.setDebit_amount(intentDebit);
        intent.setFee(feeResult);

        p2pSteps().makeP2P(intent, privateKey, authSteps().getAccessToken());
    }

    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности перевода со своей ОКСИ на чужой MASTER (pan - PAN)")
    public void myOxyPanOtherMaster() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {

        p2pSteps().setFee(0);
        p2pSteps().setIntent_debit(0);
        p2pSteps().setCredit(0);
        p2pSteps().setDisplay_debit(0);

        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);

        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);


        var intent =
                mobileBackendSteps()
                        .generateIntent(activeCards, FROM_PAN_TO_PAN, flowsSteps().getUserId(), amount);

        intent.setDebit_src_id(myPan);
        intent.setCredit_dst_id(otherMaster);
        PrivateKey privateKey = authSteps().getPrivateKey();

        p2pSteps()
                .checkFee(intent, authSteps().getAccessToken());

        var expectedFee = amount/100 + 500;

        var feeResult = p2pSteps().getFee();
        var intentDebit = p2pSteps().getIntent_debit();
        var displayDebit = p2pSteps().getDisplay_debit();
        var credit = p2pSteps().getCredit();
        verify("fee", feeResult, expectedFee, true);
        verify("debit", intentDebit, amount, true);
        verify("display debit", displayDebit, amount + expectedFee, true);
        verify("credit", credit, amount, true);
        intent.setDebit_amount(intentDebit);
        intent.setFee(feeResult);

        p2pSteps().makeP2P(intent, privateKey, authSteps().getAccessToken());
    }

    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности перевода со своей ОКСИ на чужой VISA (pan - PAN)")
    public void myOxyPanOtherVisa() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {

        p2pSteps().setFee(0);
        p2pSteps().setIntent_debit(0);
        p2pSteps().setCredit(0);
        p2pSteps().setDisplay_debit(0);

        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);

        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);


        var intent =
                mobileBackendSteps()
                        .generateIntent(activeCards, FROM_PAN_TO_PAN, flowsSteps().getUserId(), amount);

        intent.setDebit_src_id(myPan);
        intent.setCredit_dst_id(otherVisa);
        PrivateKey privateKey = authSteps().getPrivateKey();

        p2pSteps()
                .checkFee(intent, authSteps().getAccessToken());
        var expectedFee = amount/100 + 500;

        var feeResult = p2pSteps().getFee();
        var intentDebit = p2pSteps().getIntent_debit();
        var displayDebit = p2pSteps().getDisplay_debit();
        var credit = p2pSteps().getCredit();
        verify("fee", feeResult, expectedFee, true);
        verify("debit", intentDebit, amount, true);
        verify("display debit", displayDebit, amount + expectedFee, true);
        verify("credit", credit, amount, true);
        intent.setDebit_amount(intentDebit);
        intent.setFee(feeResult);

        p2pSteps().makeP2P(intent, privateKey, authSteps().getAccessToken());
    }

    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности перевода со своей ОКСИ на чужой VISA (panId - PAN)")
    public void myOxyPanIdOtherVisa() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {

        p2pSteps().setFee(0);
        p2pSteps().setIntent_debit(0);
        p2pSteps().setCredit(0);
        p2pSteps().setDisplay_debit(0);

        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);

        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);


        var intent =
                mobileBackendSteps()
                        .generateIntent(activeCards, FROM_PAN_ID_TO_PAN, flowsSteps().getUserId(), amount);

        var expectedFee = amount/100 + 500;

        intent.setDebit_src_id(myPanId);
        intent.setCredit_dst_id(otherVisa);
        PrivateKey privateKey = authSteps().getPrivateKey();

        p2pSteps()
                .checkFee(intent, authSteps().getAccessToken());

        var feeResult = p2pSteps().getFee();
        var intentDebit = p2pSteps().getIntent_debit();
        var displayDebit = p2pSteps().getDisplay_debit();
        var credit = p2pSteps().getCredit();
        verify("fee", feeResult, expectedFee, true);
        verify("debit", intentDebit, amount, true);
        verify("display debit", displayDebit, amount + expectedFee, true);
        verify("credit", credit, amount, true);
        intent.setDebit_amount(intentDebit);
        intent.setFee(feeResult);

        p2pSteps().makeP2P(intent, privateKey, authSteps().getAccessToken());
    }

    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности перевода с чужой ОКСИ на свою ОКСИ (Pan - PanId)")
    public void otherOxyPanMyOxyPanId() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InterruptedException {


        p2pSteps().setFee(0);
        p2pSteps().setIntent_debit(0);
        p2pSteps().setCredit(0);
        p2pSteps().setDisplay_debit(0);
        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);

        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);

        var pans = List.of
                ("5248723335882609");

        var intent =
                mobileBackendSteps()
                        .generateIntent(activeCards, FROM_PAN_TO_PAN_ID, flowsSteps().getUserId(), amount);

        intent.setDebit_src_id(otherPans.get(0));
        intent.setCredit_dst_id(myPanId);
//        intent.setFee(p2pSteps().getFee());

        PrivateKey privateKey = authSteps().getPrivateKey();


        p2pSteps()
                .checkFee(intent, authSteps().getAccessToken());

        var feeResult = p2pSteps().getFee();
        var intentDebit = p2pSteps().getIntent_debit();
        var displayDebit = p2pSteps().getDisplay_debit();
        var credit = p2pSteps().getCredit();
        verify("fee", feeResult, 0, true);
        verify("debit", intentDebit, amount, true);
        verify("display debit", displayDebit, amount, true);
        verify("credit", credit, amount, true);

        intent.setDebit_amount(intentDebit);
        intent.setFee(feeResult);

        p2pSteps().makeP2Pwith3Ds(intent, privateKey, authSteps().getAccessToken());
        verify("Required 3DS = TRUE", p2pSteps().isRequired_3ds(), true, true);

        for (int i = 0; i < 10; i++) {
            if (p2pSteps().getUserActionUrl() == null){
                p2pSteps().checkStatus(p2pSteps().getId(), authSteps().getAccessToken());
            }
            else break;
        }
        verify("3DS ссылка получена", p2pSteps().getUserActionUrl(), null, false);
    }



    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности перевода с чужой ОКСИ на свою ОКСИ (PanId - Pan)")
    public void otherOxyPanIdMyOxyPan() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InterruptedException {

        p2pSteps().setFee(0);
        p2pSteps().setIntent_debit(0);
        p2pSteps().setCredit(0);
        p2pSteps().setDisplay_debit(0);

        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);

        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);

        var pans = List.of
                ("5248723331825776");

        var intent =
                mobileBackendSteps()
                        .generateIntent(activeCards, FROM_PAN_ID_TO_PAN, flowsSteps().getUserId(), amount);
        intent.setDebit_src_id(otherPanIds.get(0));
        intent.setCredit_dst_id(myPan);
//        intent.setFee(p2pSteps().getFee());

        PrivateKey privateKey = authSteps().getPrivateKey();

        p2pSteps()
                .checkWrongFee(intent, authSteps().getAccessToken(), 422);
//        var feeResult = p2pSteps().getFee();
//        var intentDebit = p2pSteps().getIntent_debit();
//        var displayDebit = p2pSteps().getDisplay_debit();
//        var credit = p2pSteps().getCredit();
//        verify("fee", feeResult, 0, true);
//        verify("debit", intentDebit, amount, true);
//        verify("display debit", displayDebit, amount, true);
//        verify("credit", credit, amount, true);
//        intent.setDebit_amount(intentDebit);
//        intent.setFee(feeResult);
//
//        p2pSteps().makeP2Pwith3Ds(intent, privateKey, authSteps().getAccessToken());
//        verify("Required 3DS = TRUE", p2pSteps().isRequired_3ds(), true, true);
//
//        for (int i = 0; i < 10; i++) {
//            if (p2pSteps().getUserActionUrl() == null){
//                p2pSteps().checkStatus(p2pSteps().getId(), authSteps().getAccessToken());
//            }
//            else break;
//        }
//        verify("3DS ссылка получена", p2pSteps().getUserActionUrl(), null, false);
    }

    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности перевода с чужой ОКСИ на свою ОКСИ (PanId - PanId)")
    public void otherOxyPanIdMyOxyPanId() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InterruptedException {

        p2pSteps().setFee(0);
        p2pSteps().setIntent_debit(0);
        p2pSteps().setCredit(0);
        p2pSteps().setDisplay_debit(0);

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
                        .generateIntent(activeCards, FROM_PAN_ID_TO_PAN_ID, flowsSteps().getUserId(), amount);

        intent.setDebit_src_id(otherPanIds.get(0));
        intent.setCredit_dst_id(myPanId);

        PrivateKey privateKey = authSteps().getPrivateKey();

        p2pSteps()
                .checkWrongFee(intent, authSteps().getAccessToken(), 422);

//        var feeResult = p2pSteps().getFee();
//        var intentDebit = p2pSteps().getIntent_debit();
//        var displayDebit = p2pSteps().getDisplay_debit();
//        var credit = p2pSteps().getCredit();
//        verify("fee", feeResult, 0, true);
//        verify("debit", intentDebit, amount, true);
//        verify("display debit", displayDebit, amount, true);
//        verify("credit", credit, amount, true);
//        intent.setDebit_amount(intentDebit);
//        intent.setFee(feeResult);
//
//        p2pSteps().makeP2Pwith3Ds(intent, privateKey, authSteps().getAccessToken());
//        verify("Required 3DS = TRUE", p2pSteps().isRequired_3ds(), true, true);
//
//        for (int i = 0; i < 10; i++) {
//            if (p2pSteps().getUserActionUrl() == null){
//                p2pSteps().checkStatus(p2pSteps().getId(), authSteps().getAccessToken());
//            }
//            else break;
//        }
//        verify("3DS ссылка получена", p2pSteps().getUserActionUrl(), null, false);
    }

    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности перевода с чужой ОКСИ на свою ОКСИ (Pan - Pan)")
    public void otherOxyPanMyOxyPan() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InterruptedException {

        p2pSteps().setFee(0);
        p2pSteps().setIntent_debit(0);
        p2pSteps().setCredit(0);
        p2pSteps().setDisplay_debit(0);

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
                        .generateIntent(activeCards, FROM_PAN_TO_PAN, flowsSteps().getUserId(), amount);

        intent.setDebit_src_id(otherPans.get(0));
        intent.setCredit_dst_id(myPan);

        PrivateKey privateKey = authSteps().getPrivateKey();

        p2pSteps()
                .checkFee(intent, authSteps().getAccessToken());
        var feeResult = p2pSteps().getFee();
        var intentDebit = p2pSteps().getIntent_debit();
        var displayDebit = p2pSteps().getDisplay_debit();
        var credit = p2pSteps().getCredit();
        verify("fee", feeResult, 0, true);
        verify("debit", intentDebit, amount, true);
        verify("display debit", displayDebit, amount, true);
        verify("credit", credit, amount, true);
        intent.setDebit_amount(intentDebit);
        intent.setFee(feeResult);

        p2pSteps().makeP2Pwith3Ds(intent, privateKey, authSteps().getAccessToken());
        verify("Required 3DS = TRUE", p2pSteps().isRequired_3ds(), true, true);

        for (int i = 0; i < 10; i++) {
            if (p2pSteps().getUserActionUrl() == null){
                p2pSteps().checkStatus(p2pSteps().getId(), authSteps().getAccessToken());
            }
            else break;
        }
        verify("3DS ссылка получена", p2pSteps().getUserActionUrl(), null, false);
    }

    /////////////////////// FROM NOT OXY

    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности перевода с чужой VISA на чужую VISA (Pan - Pan)")
    public void otherVisaOtherVisa() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InterruptedException {

        p2pSteps().setFee(0);
        p2pSteps().setIntent_debit(0);
        p2pSteps().setCredit(0);
        p2pSteps().setDisplay_debit(0);

        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);

        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);


        var intent =
                mobileBackendSteps()
                        .generateIntent(activeCards, FROM_PAN_TO_PAN, flowsSteps().getUserId(), amount);

        intent.setDebit_src_id(otherVisa);
        intent.setCredit_dst_id(otherVisa2);

        PrivateKey privateKey = authSteps().getPrivateKey();

        p2pSteps()
                .checkFee(intent, authSteps().getAccessToken());
        var expectedFee = amount/100 + 500;


        var feeResult = p2pSteps().getFee();
        var intentDebit = p2pSteps().getIntent_debit();
        var displayDebit = p2pSteps().getDisplay_debit();
        var credit = p2pSteps().getCredit();
        verify("fee", feeResult, expectedFee, true);
        verify("debit", intentDebit, amount + expectedFee, true);
        verify("display debit", displayDebit, amount + expectedFee, true);
        verify("credit", credit, amount, true);
        intent.setDebit_amount(intentDebit);
        intent.setFee(feeResult);

        p2pSteps().makeP2Pwith3Ds(intent, privateKey, authSteps().getAccessToken());
        verify("Required 3DS = TRUE", p2pSteps().isRequired_3ds(), true, true);

        for (int i = 0; i < 10; i++) {
            if (p2pSteps().getUserActionUrl() == null){
                p2pSteps().checkStatus(p2pSteps().getId(), authSteps().getAccessToken());
            }
            else break;
        }
        verify("3DS ссылка получена", p2pSteps().getUserActionUrl(), null, false);
    }

    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности перевода  с чужой Visa на чужую Master (Pan - Pan)")
    public void otherVisaOtherMaster() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InterruptedException {

        p2pSteps().setFee(0);
        p2pSteps().setIntent_debit(0);
        p2pSteps().setCredit(0);
        p2pSteps().setDisplay_debit(0);

        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);

        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);

        var intent =
                mobileBackendSteps()
                        .generateIntent(activeCards, FROM_PAN_TO_PAN, flowsSteps().getUserId(), amount);

        intent.setDebit_src_id(otherVisa);
        intent.setCredit_dst_id(otherMaster2);

        PrivateKey privateKey = authSteps().getPrivateKey();

        p2pSteps()
                .checkFee(intent, authSteps().getAccessToken());
        var expectedFee = amount/100 + 500;


        var feeResult = p2pSteps().getFee();
        var intentDebit = p2pSteps().getIntent_debit();
        var displayDebit = p2pSteps().getDisplay_debit();
        var credit = p2pSteps().getCredit();
        verify("fee", feeResult, expectedFee, true);
        verify("debit", intentDebit, amount + expectedFee, true);
        verify("display debit", displayDebit, amount + expectedFee, true);
        verify("credit", credit, amount, true);
        intent.setDebit_amount(intentDebit);
        intent.setFee(feeResult);

        p2pSteps().makeP2Pwith3Ds(intent, privateKey, authSteps().getAccessToken());
        verify("Required 3DS = TRUE", p2pSteps().isRequired_3ds(), true, true);

        for (int i = 0; i < 10; i++) {
            if (p2pSteps().getUserActionUrl() == null){
                p2pSteps().checkStatus(p2pSteps().getId(), authSteps().getAccessToken());
            }
            else break;
        }
        verify("3DS ссылка получена", p2pSteps().getUserActionUrl(), null, false);
    }

    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности перевода  с чужой MASTER на чужую MASTER (Pan - Pan)")
    public void otherMasterOtherMaster() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InterruptedException {


        p2pSteps().setFee(0);
        p2pSteps().setIntent_debit(0);
        p2pSteps().setCredit(0);
        p2pSteps().setDisplay_debit(0);
        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);

        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);

        var intent =
                mobileBackendSteps()
                        .generateIntent(activeCards, FROM_PAN_TO_PAN, flowsSteps().getUserId(), amount);

        intent.setDebit_src_id(otherMaster);
        intent.setCredit_dst_id(otherMaster2);

        PrivateKey privateKey = authSteps().getPrivateKey();

        p2pSteps()
                .checkFee(intent, authSteps().getAccessToken());
        var expectedFee = amount/100 + 500;


        var feeResult = p2pSteps().getFee();
        var intentDebit = p2pSteps().getIntent_debit();
        var displayDebit = p2pSteps().getDisplay_debit();
        var credit = p2pSteps().getCredit();
        verify("fee", feeResult, expectedFee, true);
        verify("debit", intentDebit, amount + expectedFee, true);
        verify("display debit", displayDebit, amount + expectedFee, true);
        verify("credit", credit, amount, true);
        intent.setDebit_amount(intentDebit);
        intent.setFee(feeResult);

        p2pSteps().makeP2Pwith3Ds(intent, privateKey, authSteps().getAccessToken());
        verify("Required 3DS = TRUE", p2pSteps().isRequired_3ds(), true, true);

        for (int i = 0; i < 10; i++) {
            if (p2pSteps().getUserActionUrl() == null){
                p2pSteps().checkStatus(p2pSteps().getId(), authSteps().getAccessToken());
            }
            else break;
        }
        verify("3DS ссылка получена", p2pSteps().getUserActionUrl(), null, false);
    }

    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности перевода с чужой MASTER на чужую VISA (Pan - Pan)")
    public void otherMasterOtherVisa() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InterruptedException {

        p2pSteps().setFee(0);
        p2pSteps().setIntent_debit(0);
        p2pSteps().setCredit(0);
        p2pSteps().setDisplay_debit(0);
        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);

        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);

        var intent =
                mobileBackendSteps()
                        .generateIntent(activeCards, FROM_PAN_TO_PAN, flowsSteps().getUserId(), amount);

        intent.setDebit_src_id(otherMaster);
        intent.setCredit_dst_id(otherVisa);

        PrivateKey privateKey = authSteps().getPrivateKey();

        p2pSteps()
                .checkFee(intent, authSteps().getAccessToken());
        var expectedFee = amount/100 + 500;


        var feeResult = p2pSteps().getFee();
        var intentDebit = p2pSteps().getIntent_debit();
        var displayDebit = p2pSteps().getDisplay_debit();
        var credit = p2pSteps().getCredit();
        verify("fee", feeResult, expectedFee, true);
        verify("debit", intentDebit, amount + expectedFee, true);
        verify("display debit", displayDebit, amount + expectedFee, true);
        verify("credit", credit, amount, true);
        intent.setDebit_amount(intentDebit);
        intent.setFee(feeResult);

        p2pSteps().makeP2Pwith3Ds(intent, privateKey, authSteps().getAccessToken());
        verify("Required 3DS = TRUE", p2pSteps().isRequired_3ds(), true, true);

        for (int i = 0; i < 10; i++) {
            if (p2pSteps().getUserActionUrl() == null){
                p2pSteps().checkStatus(p2pSteps().getId(), authSteps().getAccessToken());
            }
            else break;
        }
        verify("3DS ссылка получена", p2pSteps().getUserActionUrl(), null, false);
    }

    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности перевода с чужой MASTER на свою OXY (Pan - PanId)")
    public void otherMasterMyPanId() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InterruptedException {

        p2pSteps().setFee(0);
        p2pSteps().setIntent_debit(0);
        p2pSteps().setCredit(0);
        p2pSteps().setDisplay_debit(0);

        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);

        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);

        var intent =
                mobileBackendSteps()
                        .generateIntent(activeCards, FROM_PAN_TO_PAN_ID, flowsSteps().getUserId(), amount);

        intent.setDebit_src_id(otherMaster);
        intent.setCredit_dst_id(myPanId);

        PrivateKey privateKey = authSteps().getPrivateKey();

        p2pSteps()
                .checkFee(intent, authSteps().getAccessToken());
        var expectedFee = amount/100 + 500;

        var feeResult = p2pSteps().getFee();
        var intentDebit = p2pSteps().getIntent_debit();
        var displayDebit = p2pSteps().getDisplay_debit();
        var credit = p2pSteps().getCredit();
        verify("fee", feeResult, expectedFee, true);
        verify("debit", intentDebit, amount + expectedFee, true);
        verify("display debit", displayDebit, amount + expectedFee, true);
        verify("credit", credit, amount, true);
        intent.setDebit_amount(intentDebit);
        intent.setFee(feeResult);

        p2pSteps().makeP2Pwith3Ds(intent, privateKey, authSteps().getAccessToken());
        verify("Required 3DS = TRUE", p2pSteps().isRequired_3ds(), true, true);

        for (int i = 0; i < 10; i++) {
            if (p2pSteps().getUserActionUrl() == null){
                p2pSteps().checkStatus(p2pSteps().getId(), authSteps().getAccessToken());
            }
            else break;
        }
        verify("3DS ссылка получена", p2pSteps().getUserActionUrl(), null, false);
    }

    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности перевода с чужой MASTER на свою OXY (Pan - Pan)")
    public void otherMasterMyPan() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InterruptedException {

        p2pSteps().setFee(0);
        p2pSteps().setIntent_debit(0);
        p2pSteps().setCredit(0);
        p2pSteps().setDisplay_debit(0);

        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);

        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);


        var intent =
                mobileBackendSteps()
                        .generateIntent(activeCards, FROM_PAN_TO_PAN, flowsSteps().getUserId(), amount);

        intent.setDebit_src_id(otherMaster);
        intent.setCredit_dst_id(myPan);

        PrivateKey privateKey = authSteps().getPrivateKey();

        p2pSteps()
                .checkFee(intent, authSteps().getAccessToken());
        var expectedFee = amount/100 + 500;

        var feeResult = p2pSteps().getFee();
        var intentDebit = p2pSteps().getIntent_debit();
        var displayDebit = p2pSteps().getDisplay_debit();
        var credit = p2pSteps().getCredit();
        verify("fee", feeResult, expectedFee, true);
        verify("debit", intentDebit, amount + expectedFee, true);
        verify("display debit", displayDebit, amount + expectedFee, true);
        verify("credit", credit, amount, true);
        intent.setDebit_amount(intentDebit);
        intent.setFee(feeResult);

        p2pSteps().makeP2Pwith3Ds(intent, privateKey, authSteps().getAccessToken());
        verify("Required 3DS = TRUE", p2pSteps().isRequired_3ds(), true, true);

        for (int i = 0; i < 10; i++) {
            if (p2pSteps().getUserActionUrl() == null){
                p2pSteps().checkStatus(p2pSteps().getId(), authSteps().getAccessToken());
            }
            else break;
        }
        verify("3DS ссылка получена", p2pSteps().getUserActionUrl(), null, false);
    }


    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности перевода с чужой MASTER на чужую OXY (Pan - Pan")
    public void otherMasterOtherPan() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InterruptedException {

        p2pSteps().setFee(0);
        p2pSteps().setIntent_debit(0);
        p2pSteps().setCredit(0);
        p2pSteps().setDisplay_debit(0);

        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);

        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);


        var intent =
                mobileBackendSteps()
                        .generateIntent(activeCards, FROM_PAN_TO_PAN, flowsSteps().getUserId(), amount);

        intent.setDebit_src_id(otherMaster);
        intent.setCredit_dst_id(otherPans.get(0));

        PrivateKey privateKey = authSteps().getPrivateKey();

        p2pSteps()
                .checkFee(intent, authSteps().getAccessToken());
        var feeResult = p2pSteps().getFee();
        var intentDebit = p2pSteps().getIntent_debit();
        var displayDebit = p2pSteps().getDisplay_debit();
        var credit = p2pSteps().getCredit();
        var expectedFee = amount/100 + 500;

        verify("fee", feeResult, expectedFee, true);
        verify("debit", intentDebit, amount + expectedFee, true);
        verify("display debit", displayDebit, amount + expectedFee, true);
        verify("credit", credit, amount, true);
        intent.setDebit_amount(intentDebit);
        intent.setFee(feeResult);

        p2pSteps().makeP2Pwith3Ds(intent, privateKey, authSteps().getAccessToken());
        verify("Required 3DS = TRUE", p2pSteps().isRequired_3ds(), true, true);

        for (int i = 0; i < 10; i++) {
            if (p2pSteps().getUserActionUrl() == null){
                p2pSteps().checkStatus(p2pSteps().getId(), authSteps().getAccessToken());
            }
            else break;
        }
        verify("3DS ссылка получена", p2pSteps().getUserActionUrl(), null, false);
    }

    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности перевода с чужой MASTER на чужую OXY (Pan - PanId")
    public void otherMasterOtherPanId() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InterruptedException {


        p2pSteps().setFee(0);
        p2pSteps().setIntent_debit(0);
        p2pSteps().setCredit(0);
        p2pSteps().setDisplay_debit(0);
        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);

        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);

        var intent =
                mobileBackendSteps()
                        .generateIntent(activeCards, FROM_PAN_TO_PAN_ID, flowsSteps().getUserId(), amount);

        intent.setDebit_src_id(otherMaster);
        intent.setCredit_dst_id(otherPanIds.get(0));

        PrivateKey privateKey = authSteps().getPrivateKey();

        p2pSteps()
                .checkWrongFee(intent, authSteps().getAccessToken(), 422);
//        var expectedFee = amount/100 + 500;
//
//        var feeResult = p2pSteps().getFee();
//        var intentDebit = p2pSteps().getIntent_debit();
//        var displayDebit = p2pSteps().getDisplay_debit();
//        var credit = p2pSteps().getCredit();
//        verify("fee", feeResult, expectedFee, true);
//        verify("debit", intentDebit, amount + expectedFee, true);
//        verify("display debit", displayDebit, amount + expectedFee, true);
//        verify("credit", credit, amount, true);
//        intent.setDebit_amount(intentDebit);
//        intent.setFee(feeResult);
//
//        p2pSteps().makeP2Pwith3Ds(intent, privateKey, authSteps().getAccessToken());
//        verify("Required 3DS = TRUE", p2pSteps().isRequired_3ds(), true, true);
//
//        for (int i = 0; i < 10; i++) {
//            if (p2pSteps().getUserActionUrl() == null){
//                p2pSteps().checkStatus(p2pSteps().getId(), authSteps().getAccessToken());
//            }
//            else break;
//        }
//        verify("3DS ссылка получена", p2pSteps().getUserActionUrl(), null, false);
    }

    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности перевода с чужой Visa на свою OXY (Pan - PanId)")
    public void otherVisaMyPanId() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InterruptedException {


        p2pSteps().setFee(0);
        p2pSteps().setIntent_debit(0);
        p2pSteps().setCredit(0);
        p2pSteps().setDisplay_debit(0);
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
                        .generateIntent(activeCards, FROM_PAN_TO_PAN_ID, flowsSteps().getUserId(), amount);
        var expectedFee = amount/100 + 500;

        intent.setDebit_src_id(otherVisa);
        intent.setCredit_dst_id(myPanId);

        PrivateKey privateKey = authSteps().getPrivateKey();

        p2pSteps()
                .checkFee(intent, authSteps().getAccessToken());
        var feeResult = p2pSteps().getFee();
        var intentDebit = p2pSteps().getIntent_debit();
        var displayDebit = p2pSteps().getDisplay_debit();
        var credit = p2pSteps().getCredit();
        verify("fee", feeResult, expectedFee, true);
        verify("debit", intentDebit, amount + expectedFee, true);
        verify("display debit", displayDebit, amount + expectedFee, true);
        verify("credit", credit, amount, true);
        intent.setDebit_amount(intentDebit);
        intent.setFee(feeResult);

        p2pSteps().makeP2Pwith3Ds(intent, privateKey, authSteps().getAccessToken());
        verify("Required 3DS = TRUE", p2pSteps().isRequired_3ds(), true, true);

        for (int i = 0; i < 10; i++) {
            if (p2pSteps().getUserActionUrl() == null){
                p2pSteps().checkStatus(p2pSteps().getId(), authSteps().getAccessToken());
            }
            else break;
        }
        verify("3DS ссылка получена", p2pSteps().getUserActionUrl(), null, false);
    }

    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности перевода с чужой Visa на свою OXY (Pan - Pan)")
    public void otherVisaMyPan() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InterruptedException {


        p2pSteps().setFee(0);
        p2pSteps().setIntent_debit(0);
        p2pSteps().setCredit(0);
        p2pSteps().setDisplay_debit(0);
        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);

        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);


        var intent =
                mobileBackendSteps()
                        .generateIntent(activeCards, FROM_PAN_TO_PAN, flowsSteps().getUserId(), amount);

        intent.setDebit_src_id(otherVisa);
        intent.setCredit_dst_id(myPan);

        PrivateKey privateKey = authSteps().getPrivateKey();
        var expectedFee = amount/100 + 500;
        p2pSteps()
                .checkFee(intent, authSteps().getAccessToken());
        var feeResult = p2pSteps().getFee();
        var intentDebit = p2pSteps().getIntent_debit();
        var displayDebit = p2pSteps().getDisplay_debit();
        var credit = p2pSteps().getCredit();
        verify("fee", feeResult, expectedFee, true);
        verify("debit", intentDebit, amount + expectedFee, true);
        verify("display debit", displayDebit, amount + expectedFee, true);
        verify("credit", credit, amount, true);
        intent.setDebit_amount(intentDebit);
        intent.setFee(feeResult);

        p2pSteps().makeP2Pwith3Ds(intent, privateKey, authSteps().getAccessToken());
        verify("Required 3DS = TRUE", p2pSteps().isRequired_3ds(), true, true);

        for (int i = 0; i < 10; i++) {
            if (p2pSteps().getUserActionUrl() == null){
                p2pSteps().checkStatus(p2pSteps().getId(), authSteps().getAccessToken());
            }
            else break;
        }
        verify("3DS ссылка получена", p2pSteps().getUserActionUrl(), null, false);
    }


    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности перевода с чужой Visa на чужую OXY (Pan - Pan")
    public void otherVisaOtherPan() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InterruptedException {


        p2pSteps().setFee(0);
        p2pSteps().setIntent_debit(0);
        p2pSteps().setCredit(0);
        p2pSteps().setDisplay_debit(0);
        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);

        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);

        var intent =
                mobileBackendSteps()
                        .generateIntent(activeCards, FROM_PAN_TO_PAN, flowsSteps().getUserId(), amount);

        intent.setDebit_src_id(otherVisa);
        intent.setCredit_dst_id(otherPans.get(0));

        PrivateKey privateKey = authSteps().getPrivateKey();

        p2pSteps()
                .checkFee(intent, authSteps().getAccessToken());
        var expectedFee = amount/100 + 500;

        var feeResult = p2pSteps().getFee();
        var intentDebit = p2pSteps().getIntent_debit();
        var displayDebit = p2pSteps().getDisplay_debit();
        var credit = p2pSteps().getCredit();
        verify("fee", feeResult, expectedFee, true);
        verify("debit", intentDebit, amount + expectedFee, true);
        verify("display debit", displayDebit, amount + expectedFee, true);
        verify("credit", credit, amount, true);
        intent.setDebit_amount(intentDebit);
        intent.setFee(feeResult);

        p2pSteps().makeP2Pwith3Ds(intent, privateKey, authSteps().getAccessToken());
        verify("Required 3DS = TRUE", p2pSteps().isRequired_3ds(), true, true);

        for (int i = 0; i < 10; i++) {
            if (p2pSteps().getUserActionUrl() == null){
                p2pSteps().checkStatus(p2pSteps().getId(), authSteps().getAccessToken());
            }
            else break;
        }
        verify("3DS ссылка получена", p2pSteps().getUserActionUrl(), null, false);
    }

    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности перевода с чужой Visa на чужую OXY (Pan - PanId")
    public void otherVisaOtherPanId() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InterruptedException {

        p2pSteps().setFee(0);
        p2pSteps().setIntent_debit(0);
        p2pSteps().setCredit(0);
        p2pSteps().setDisplay_debit(0);

        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);

        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);


        var intent =
                mobileBackendSteps()
                        .generateIntent(activeCards, FROM_PAN_TO_PAN_ID, flowsSteps().getUserId(), amount);

        intent.setDebit_src_id(otherVisa);
        intent.setCredit_dst_id(otherPanIds.get(0));

        PrivateKey privateKey = authSteps().getPrivateKey();

        p2pSteps()
                .checkWrongFee(intent, authSteps().getAccessToken(), 422);
//        var expectedFee = amount/100 + 500;
//
//        var feeResult = p2pSteps().getFee();
//        var intentDebit = p2pSteps().getIntent_debit();
//        var displayDebit = p2pSteps().getDisplay_debit();
//        var credit = p2pSteps().getCredit();
//        verify("fee", feeResult, expectedFee, true);
//        verify("debit", intentDebit, amount + expectedFee, true);
//        verify("display debit", displayDebit, amount + expectedFee, true);
//        verify("credit", credit, amount, true);
//        intent.setDebit_amount(intentDebit);
//        intent.setFee(feeResult);
//
//        p2pSteps().makeP2Pwith3Ds(intent, privateKey, authSteps().getAccessToken());
//        verify("Required 3DS = TRUE", p2pSteps().isRequired_3ds(), true, true);
//
//        for (int i = 0; i < 10; i++) {
//            if (p2pSteps().getUserActionUrl() == null){
//                p2pSteps().checkStatus(p2pSteps().getId(), authSteps().getAccessToken());
//            }
//            else break;
//        }
//        verify("3DS ссылка получена", p2pSteps().getUserActionUrl(), null, false);
    }

//    @Feature("ПЛАТЕЖИ P2P")
//    @Test(description = "Максимальная сумма перевода вместе с комиссией = 29 999")
//    public void makeOverPayment() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
//
//        p2pSteps().setFee(0);
//        p2pSteps().setIntent_debit(0);
//        p2pSteps().setCredit(0);
//        p2pSteps().setDisplay_debit(0);
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
//
//        var intent =
//                mobileBackendSteps()
//                        .generateIntent(activeCards, FROM_PAN_ID_TO_PAN_ID, flowsSteps().getUserId(), amount);
//
//        intent.setDebit_src_id(myPanId);
//
//        PrivateKey privateKey = authSteps().getPrivateKey();
//
//        p2pSteps()
//                .checkFee(intent, authSteps().getAccessToken());
//
//        var feeResult = p2pSteps().getFee();
//        var intentDebit = p2pSteps().getIntent_debit();
//        var displayDebit = p2pSteps().getDisplay_debit();
//        var credit = p2pSteps().getCredit();
//        verify("fee", feeResult, 0, true);
//        verify("debit", intentDebit, amount, true);
//        verify("display debit", displayDebit, amount, true);
//        verify("credit", credit, amount, true);
//
//        intent.setDebit_amount(intentDebit);
//        intent.setFee(feeResult);
//
//        p2pSteps().makeP2P(intent, privateKey, authSteps().getAccessToken());
//
//    }
}