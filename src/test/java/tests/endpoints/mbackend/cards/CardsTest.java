package tests.endpoints.mbackend.cards;

import io.qameta.allure.Feature;
import models.mbackend.response.cards.card.CardsResponseModel;
import org.testng.annotations.Test;
import steps.ApiSteps;

@Test(groups = {"auth-client"})
public class CardsTest extends ApiSteps {


    @Feature("Карты клиента")
    @Test(description = "Проверка возможности получения данных по картам пользователя")
    public void cardsTest(){

        var GET_CARDS = "/cards";

        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(GET_CARDS, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);

        
    }


}
