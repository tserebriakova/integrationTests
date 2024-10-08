package steps;

import enums.Credentials;
import helpers.DataProviders;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import steps.auth.AuthSteps;
import steps.flows.FlowsSteps;
import steps.mbackend.MBackendSteps;
import steps.notify.NotifySteps;
import steps.p2p.P2pSteps;
import steps.receipt.ReceiptSteps;
import steps.smpay.SmPaySteps;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Random;

import static enums.Credentials.*;
import static enums.StaticMethodsPath.CARDS;
import static enums.StaticMethodsPath.ME;
import static helpers.ServerConfig.BASE_CONFIG;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class ApiSteps extends DataProviders {

    //Steps objects
    private static FlowsSteps flowsSteps;
    private static MBackendSteps mBackendSteps;
    private static P2pSteps p2pSteps;
    private static AuthSteps authSteps;
    private static NotifySteps notifySteps;
    private static SmPaySteps smPaySteps;
    private static ReceiptSteps receiptSteps;

    //Prepare tests.endpoints URI
    private static final String
            flowsUri = BASE_CONFIG.getFlowsUrl();
    private static final String mobileBackendUri = BASE_CONFIG.getMobBackendUrl();
    private static final String notifyUri = BASE_CONFIG.getNotifyUrl();
    private static final String p2pUri = BASE_CONFIG.getP2PUrl();
    private static final String authUri = BASE_CONFIG.getAuthUrl();
    private static final String receiptUri = BASE_CONFIG.getReceptUrl();
    private static final String smPayUri = BASE_CONFIG.getSmPayUrl();
    public static final String xVersion = BASE_CONFIG.getXVersion();
    public static final String env = System.getProperty("env");
    private static Credentials creds;
    private static String panNumber;
    private static String panFor3Ds;
    private static String euCard;
    private static Credentials wrongCreds;
    private static Credentials otherCred;
    private static Credentials onBoardingCreds;
    private static Credentials mobileBackendCreds;
    private static Credentials recoveryCreds;
    private static Credentials emptyCreds;


    //Static methods path:
    public String PROFILE_METHOD_PATH = ME.getPath();
    public String CARDS_METHOD_PATH = CARDS.getPath();



    public Credentials getMainCreds(){return creds;}
    public Credentials getWrongCreds(){return wrongCreds;}
    public Credentials getOtherCreds(){return otherCred;}
    public Credentials getRecoveryCreds(){return recoveryCreds;}
    public Credentials getEmptyCreds(){return emptyCreds;}
    public String getPanNumber(){return panNumber;}
    public String getPanFor3Ds(){return panFor3Ds;}
    public String getEuCard(){return euCard;}


    @BeforeClass()
    public void setUp() {
        RestAssured.defaultParser = Parser.JSON;
        if (Objects.equals(env, "prod3")) {
            creds = PROD_CREDS;
            panNumber = "5406371513530315";
            wrongCreds = WRONG_CARD_DETAILS;
            euCard = "5355850057650002";
            panFor3Ds = "5167803304101434";
        } else if(Objects.equals(env, "prod_p2m")){
            creds = PROD_CREDS;
        }else {
            creds = PROD_CREDS;
            wrongCreds = WRONG_CARD_DETAILS;
            otherCred = VALID_CARD_333;
            recoveryCreds = CARD_7777_RECOVERY;
            emptyCreds = EMPTY_CARD_DETAILS;
            panNumber = "5406371513530315";
            euCard = "5355850057650002";
            panFor3Ds = "5167803304101434";
        }
        createStepObjects();

    }

    @BeforeGroups("auth-client")
    @Step("Настройка Rest Assured. Выполнение входа клиентом.")
    public void beforeAuthGroups() throws IOException, InvalidAlgorithmParameterException, NoSuchAlgorithmException {
        RestAssured.defaultParser = Parser.JSON;
        if (Objects.equals(env, "prod3")) {
            creds = PROD_CREDS;
            panNumber = "5406371513530315";
            euCard = "5355850057650002";
            panFor3Ds = "5167803304101434";
        } else {
            creds = VALID_0950961515;
            panNumber = "5406371513530315";
            euCard = "5355850057650002";
            panFor3Ds = "5167803304101434";
        }
        createStepObjects();
        logIn(creds);
    }

    public void createStepObjects() {
        flowsSteps = new FlowsSteps(flowsUri, xVersion);
        mBackendSteps = new MBackendSteps(mobileBackendUri, xVersion);
        p2pSteps = new P2pSteps(p2pUri, xVersion);
        authSteps = new AuthSteps(authUri, xVersion);
        notifySteps = new NotifySteps(notifyUri, xVersion);
        smPaySteps = new SmPaySteps(smPayUri, xVersion);
        receiptSteps = new ReceiptSteps(receiptUri, xVersion);
    }

    public void logIn(Credentials credentials) throws IOException,
            InvalidAlgorithmParameterException, NoSuchAlgorithmException {
        mBackendSteps
                .startOnboardingFlow();
        flowsSteps
                .sendPhoneNumber(creds, mBackendSteps.getUuid(), mBackendSteps.getTaskId())
                .sendOtpCode(creds)
                .sendPinHash(creds);
        authSteps
                .generateAndPublishCertificate(creds, flowsSteps.getAccessToken());
    }

    public void changeCardLimits(){}

    public void changeCardSecurity(){

    }


    public FlowsSteps flowsSteps() {
        return flowsSteps;
    }

    public MBackendSteps mobileBackendSteps() {
        return mBackendSteps;
    }

    public P2pSteps p2pSteps() {
        return p2pSteps;
    }

    public AuthSteps authSteps() {
        return authSteps;
    }

    public NotifySteps notifySteps() {
        return notifySteps;
    }

    public SmPaySteps smPaySteps() {
        return smPaySteps;
    }

    public ReceiptSteps receiptSteps() {
        return receiptSteps;
    }

    // ASSERTS

    @Step("ПРОВЕРКА: {0}")
    public void verify(String assertDescription, Object actual, Object expected, boolean equals) {
        if (equals)
            assertEquals(actual, expected);
        else
            assertNotEquals(actual, expected);
    }


    @Step("ПРОВЕРКА: {0}")
    public Boolean verifyStringIsNotEmptyNotNull(String str1, String str) {
        return str != null && !str.trim().isEmpty();
    }


    @Step("ПРОВЕРКА: {0}")
    public static boolean checkString(String str1, String actual, int digitsAmount) {
        return actual.matches("[0-9]+") && actual.length() == digitsAmount;
    }



    public static int gen() {
        Random r = new Random( System.currentTimeMillis() );
        return ((1 + r.nextInt(2)) * 1000000 + r.nextInt(10000));
    }

    void wait(Integer seconds) throws InterruptedException {
        Thread.sleep(seconds * 1000);
    }

}
