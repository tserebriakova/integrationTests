package steps.mbackend;

import com.fasterxml.jackson.core.JsonProcessingException;
import enums.CardStatus;
import enums.TransactionDirections;
import enums.TransactionStatus;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.mbackend.request.card.design.ChangeCardDesign;
import models.mbackend.request.card.limits.ChangeCardLimit;
import models.mbackend.request.card.pin.ChangeCardPin;
import models.mbackend.request.card.security.ChangeCardSecurity;
import models.mbackend.request.card.status.ChangeCardStatus;
import models.mbackend.request.switchsms.SwitchSmsRequestModel;
import models.mbackend.response.cards.card.CardsResponseModel;
import models.mbackend.response.cards.card.design.DesignResponseModel;
import models.mbackend.response.cards.card.transactions.TransactionsResponseModel;
import models.mbackend.response.init.InitResponseModel;
import models.p2p.intent.IntentModel;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MBackendSteps extends MBackendBaseSteps {

    private String baseUrl;
    private String xVersion;

    public MBackendSteps(String baseUrl, String xVersion) {
        this.baseUrl = baseUrl;
        this.xVersion = xVersion;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    private String
            uuid,
            taskId;

    //TODO

    @Step("Инициализация процесса /init")
    public MBackendSteps startOnboardingFlow() throws JsonProcessingException {
        var initOnboarding = initOnboarding(baseUrl, xVersion);
        setUuid(initOnboarding.getUuid());
        setTaskId(initOnboarding.getTask_id());
        return this;
    }

    @Step("Инициализация процесса /init с Токеном и CertificateID")
    public InitResponseModel startOnboardingFlowWithTokenAndCertificate(String certificateId, String refreshToken) throws JsonProcessingException {
        return initReLogin(baseUrl, xVersion, certificateId, refreshToken);
    }

    @Step("Получение {0} пользователя")
    public Response mobileBackendProcess(String url, String accessToken, int statusCode) {
        return
                getMobileBackendRequest(url, mainMobileBackendRequest(baseUrl), accessToken, statusCode, xVersion);
    }

    //TODO
    @Step("Получение {0} пользователя")
    public Response mobileBackendWrongProcess(String url, String accessToken, int statusCode) {
        return
                getMobileBackendRequest(url, mainMobileBackendRequest(baseUrl), accessToken, statusCode, xVersion);
    }

    @Step("Смена метода получения нотификаций SMS/PUSH")
    public Response switchSmsPush(String url, boolean status, String accessToken, int statusCode) throws JsonProcessingException {
        return putSwitchSms(url, mainMobileBackendRequest(baseUrl), new SwitchSmsRequestModel(status), statusCode, accessToken, xVersion);
    }

    @Step("Смена дизайна карты")
    public Response changeCardDesign(String url, String accessToken, ChangeCardDesign body, int statusCode) {
        return
                patchChangeCardDesign(url, mainMobileBackendRequest(baseUrl), body, statusCode, accessToken, xVersion);
    }

    @Step("Смена ПИН кода карты")
    public Response changeCardPin(String url, String accessToken, ChangeCardPin body, int statusCode) {
        return
                putChangeCardPin(url, mainMobileBackendRequest(baseUrl), body, statusCode, accessToken, xVersion);
    }

    @Step("Смена настроек безопастности")
    public Response changeCardSecurity(String url, String accessToken, ChangeCardSecurity body, int statusCode) {
        return
                putChangeCardSecurity(url, mainMobileBackendRequest(baseUrl), body, statusCode, accessToken, xVersion);
    }

    @Step("Смена лимита карты")
    public Response changeCardLimit(String url, String accessToken, ChangeCardLimit body, int statusCode) {
        return
                putChangeCardLimit(url, mainMobileBackendRequest(baseUrl), body, statusCode, accessToken, xVersion);
    }


    @Step("Смена статуса карты")
    public Response changeCardStatus(String url, String accessToken, ChangeCardStatus body, int statusCode) {
        return
                putChangeCardStatus(url, mainMobileBackendRequest(baseUrl), body, statusCode, accessToken, xVersion);
    }


    public List<CardsResponseModel> filterCadsByPanId(CardsResponseModel[] cardsResponseModels, String panId) {
        return Arrays.stream(cardsResponseModels).filter(s -> s.getId().equals(panId)).collect(Collectors.toList());
    }


    public List<CardsResponseModel> filterCadsByStatus(CardsResponseModel[] cardsResponseModels, CardStatus status) {
        return Arrays.stream(cardsResponseModels).filter(s -> s.getStatus().equals(status.status())).collect(Collectors.toList());
    }


    public List<TransactionsResponseModel> filterTransactionsByStatus(TransactionsResponseModel[] transactionsResponseModels, TransactionStatus status) {
        return Arrays.stream(transactionsResponseModels).filter(s -> s.getStatus().equals(status.status())).collect(Collectors.toList());
    }

    public List<TransactionsResponseModel> filterReversalTransactions(TransactionsResponseModel[] transactionsResponseModels, boolean reversal) {
        return Arrays.stream(transactionsResponseModels).filter(s -> s.isReversal() == (reversal)).collect(Collectors.toList());
    }

    public List<DesignResponseModel> filterSelectedCardDesign(DesignResponseModel[] designResponseModel, boolean status) {
        return Arrays.stream(designResponseModel).filter(s -> s.is_selected == (status)).collect(Collectors.toList());
    }

    //TODO
    public IntentModel generateIntent(List<CardsResponseModel> activeCards, TransactionDirections transactionDirections, String userid, int amount) {
        var time = Instant.now().getEpochSecond();

        if (activeCards.size() > 1) {
            System.out.println("ACTIVE CARD CREDIT: " + activeCards.get(1).getId() + "" + " PAN: " + activeCards.get(1).getMask() + "");
            System.out.println("ACTIVE CARD DEBIT: " + activeCards.get(0).getId() + "" + " PAN: " + activeCards.get(0).getMask() + "");

            return new IntentModel("pay", transactionDirections.creditSrc(), activeCards.get(1).getId(),
                    amount, transactionDirections.debitSrc(), amount, time, activeCards.get(0).getId(), 0, userid);

        } else {
            System.out.println("ACTIVE CARD CREDIT: " + activeCards.get(0).getId() + "" + " PAN: " + activeCards.get(0).getMask() + "");
            System.out.println("ACTIVE CARD DEBIT: " + activeCards.get(0).getId() + "" + " PAN: " + activeCards.get(0).getMask() + "");
            return new IntentModel("pay", transactionDirections.creditSrc(), activeCards.get(0).getId(),
                    amount, transactionDirections.debitSrc(), amount, time, activeCards.get(0).getId(), 0, userid);
        }

    }


    //TODO
    public IntentModel generateIntent3Ds(String panNumber, TransactionDirections transactionDirections, String userid, int amount) {
        var time = Instant.now().getEpochSecond();

        return new IntentModel("pay", transactionDirections.creditSrc(), panNumber,
                amount, transactionDirections.debitSrc(), amount, time, panNumber, 0, userid);


    }

}
