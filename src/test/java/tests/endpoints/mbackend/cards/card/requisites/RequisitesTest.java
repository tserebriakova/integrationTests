package tests.endpoints.mbackend.cards.card.requisites;

import io.qameta.allure.Feature;
import models.mbackend.response.cards.card.CardsResponseModel;
import models.mbackend.response.cards.card.requisites.RequisitesResponseModel;
import org.testng.annotations.Test;
import steps.ApiSteps;

import static enums.CardStatus.*;

@Test(groups={"auth-client"})
public class RequisitesTest extends ApiSteps {

    @Feature("Реквизиты карты клиента")
    @Test(description = "Проверка возможности получения реквизитов по карте клиента")
        public void cardRequisitesTest() {


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
        var requisites =
                mobileBackendSteps()
                        .mobileBackendProcess("/cards/" +activeCards.get(0).getId()+ "/requisites", authSteps().getAccessToken(), 200)
                        .as(RequisitesResponseModel[].class);
    }

}
