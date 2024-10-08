package tests.endpoints.mbackend.cards.card.pin;

import io.qameta.allure.Feature;
import models.mbackend.request.card.pin.ChangeCardPin;
import models.mbackend.response.cards.card.CardsResponseModel;
import org.testng.annotations.Test;
import steps.ApiSteps;

import static enums.CardStatus.ACTIVE;

@Test(groups={"auth-client"})
public class PinTest extends ApiSteps {

    @Feature("ПИН код карты Клиента")
    @Test(description = "Проверка возможности смены ПИНа карты клиента для активной карты")
    public void pinCodeTest() {
                var GET_CARDS = "/cards";
        var amount = "123";

        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(GET_CARDS, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);
        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);
        var changePin =
                mobileBackendSteps()
                        .changeCardPin("/cards/" +activeCards.get(0).getId()+ "/pin", authSteps().getAccessToken(),
                                new ChangeCardPin("1111"), 200);

    }

//    @Test(description = "Try to setup easy pin")
//    public void test2(){
//    }
//
//    @Test(description = "Try to setup wrong pin values")
//    public void test3(){
//    }
//
//    @Test(description = "Try to setup pin for other users card")
//    public void test4(){
//    }
//
//    @Test(description = "Try to setup pin for not real card")
//    public void test5(){
//    }
//
//    /// TO DO
//    @Test(description = "Try to get this option for blocked / inactive card etc")
//    public void test8(){
//    }


}
