package tests.endpoints.mbackend.cards.card.pancvv;

import io.qameta.allure.Feature;
import models.mbackend.response.cards.card.CardsResponseModel;
import models.mbackend.response.cards.card.pancvv.PanCvvResponseModel;
import org.testng.annotations.Test;
import steps.ApiSteps;

import static enums.CardStatus.*;

@Test(groups={"auth-client"})
public class PanCvvTest extends ApiSteps {

    @Feature("PAN и CVV карты Клиента")
    @Test(description = "Проверка получения PAN и CVV карты клиента")
    public void panCvvTest() {

                var GET_CARDS = "/cards";
        var amount = "123";

        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(GET_CARDS, authSteps().getAccessToken(), 200)
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
        var panCvv =
                mobileBackendSteps()
                        .mobileBackendProcess("/cards/" +activeCards.get(0).getId()+ "/pan-cvv", authSteps().getAccessToken(), 200)
                        .as(PanCvvResponseModel.class);
    }

//    @Test(description = "Request pan + cvv of other user card")
//    public void test2(){
//    }
//
//    @Test(description = "Request pan + cvv of not real card")
//    public void test3(){
//    }
//
//    /// TO DO
//    @Test(description = "Try to get this option for blocked / inactive card etc")
//    public void test8(){
//    }


}
