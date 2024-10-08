package tests.endpoints.mbackend.cards.card.options;

import io.qameta.allure.Feature;
import models.mbackend.response.cards.card.CardsResponseModel;
import models.mbackend.response.cards.card.options.OptionsResponseModel;
import org.testng.annotations.Test;
import steps.ApiSteps;

import static enums.CardStatus.*;

@Test(groups={"auth-client"})
public class OptionsTest extends ApiSteps {

    @Feature("Опции карты Клиента")
    @Test(description = "Проверка получения опций по карте клиента для активной карты")
    public void cardOptionTest() {


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
        var options =
                mobileBackendSteps()
                        .mobileBackendProcess("/v2/cards/" +blocked.get(0).getId()+ "/options", authSteps().getAccessToken(), 200)
                        .as(OptionsResponseModel.class);

    }

//    @Test(description = "Проверка получения опций по карте клиента для заблокированной карты")
//    public void test6(){
//    }
//
//    @Test(description = "Попытка получения опций карты чужого клиента")
//    public void test7(){
//    }
}
