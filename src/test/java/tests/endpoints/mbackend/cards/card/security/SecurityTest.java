package tests.endpoints.mbackend.cards.card.security;

import io.qameta.allure.Feature;
import models.mbackend.request.card.security.ChangeCardSecurity;
import models.mbackend.response.cards.card.CardsResponseModel;
import models.mbackend.response.cards.card.security.SecurityResponseModel;
import org.testng.annotations.Test;
import steps.ApiSteps;

import static enums.CardStatus.*;
import static enums.SecurityOptions.WIRELESSPAYMENT;

@Test(groups={"auth-client"})
public class SecurityTest extends ApiSteps {

    @Feature("Безопасность карты Клиента")
    @Test(description = "Проверка возможности получения опций безопасности для карты")
    public void cardSecurityTest(){

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
        var security =
                mobileBackendSteps()
                        .mobileBackendProcess("/cards/" + activeCards.get(0).getId() + "/security", authSteps().getAccessToken(), 200)
                        .as(SecurityResponseModel[].class);
    }

    @Feature("Безопасность карты Клиента")
    @Test(description = "Проверка возможности смены опций безопасности для карты")
    public void changeCardSecurityTest() {
        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);
        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);
        var changeSecurity =
                mobileBackendSteps()
                        .changeCardSecurity("/cards/" + activeCards.get(0).getId() + "/security", authSteps().getAccessToken(),
                                new ChangeCardSecurity(WIRELESSPAYMENT.id(), false), 200);
    }

//    /// TO DO
//    @Test(description = "Try to get this option for blocked / inactive card etc")
//    public void test8(){
//    }
//    @Test(description = "Try to change card security for other users card")
//    public void test4(){
//    }
//
//    @Test(description = "Try to change card security for not real card")
//    public void test5(){
//    }
}
