package tests.endpoints.mbackend.cards.card.limits;

import io.qameta.allure.Feature;
import models.mbackend.request.card.limits.ChangeCardLimit;
import models.mbackend.response.cards.card.CardsResponseModel;
import models.mbackend.response.cards.card.limits.LimitsResponseModel;
import org.testng.annotations.Test;
import steps.ApiSteps;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;

import static enums.CardStatus.*;
import static enums.Limits.CASH_ATM_AMOUNT;
import static enums.Limits.CASH_ATM_OPERATIONS;

@Test(groups={"auth-client"})
public class LimitsTest extends ApiSteps {

    @Feature("Лимиты карты Клиента")
    @Test(description = "Проверка получения доступных лимитов по карте клиента")
    public void getCardLimitsTest()  {

        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);
        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);
        var inactive =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, INACTIVE);
        var blocked =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, BLOCKED);
        var inactiveByPin =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, INACTIVE_BY_PIN);
        var limits =
                mobileBackendSteps()
                        .mobileBackendProcess("/cards/" +activeCards.get(0).getId()+ "/limits", authSteps().getAccessToken(), 200)
                        .as(LimitsResponseModel[].class);
    }

    @Feature("Лимиты карты Клиента")
    @Test(description = "Проверка возможности смены лимитов по карте клиента")
    public void setCardLimitsTest() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, IOException {


        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);
        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);
        var changeAtmLimit =
                mobileBackendSteps().changeCardLimit("/cards/" +activeCards.get(0).getId()+ "/limits", authSteps().getAccessToken(),
                        new ChangeCardLimit(CASH_ATM_AMOUNT.id(),999999), 200);

        var changeAtmOperationsLimit =
                mobileBackendSteps().changeCardLimit("/cards/" +activeCards.get(0).getId()+ "/limits", authSteps().getAccessToken(),
                        new ChangeCardLimit(CASH_ATM_OPERATIONS.id(),99), 200);

    }

//    @Test(description = "Попытка получения лимитов карты чужого клиента")
//    public void test2(){
//    }
//
//    @Test(description = "Попытка получения лимитов для не валидной карты")
//    public void test3(){
//    }
//
//
//    @Test(description = "Попытка смены лимитов для карты чужого клиента")
//    public void test5(){
//    }
//
//    /// TO DO
//    @Test(description = "Попытка получения лимитов для заблокированной карты")
//    public void test8(){
//    }

}
