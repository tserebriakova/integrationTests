package tests.endpoints.mbackend.cards.card.pan;

import io.qameta.allure.Feature;
import models.mbackend.response.cards.card.CardsResponseModel;
import models.mbackend.response.cards.card.pancvv.PanCvvResponseModel;
import org.testng.annotations.Test;
import steps.ApiSteps;

import static enums.CardStatus.*;
@Test(groups={"auth-client"})
public class PanTest extends ApiSteps {

    @Feature("PAN карты Клиента")
    @Test(description = "Проверка получения PAN карты клиента")
    public void panTest() {

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
        var pan =
                mobileBackendSteps()
                        .mobileBackendProcess("/cards/" +activeCards.get(0).getId()+ "/pan", authSteps().getAccessToken(), 200)
                        .as(PanCvvResponseModel.class);

    }

//    @Test(description = "Request pan of other user card")
//    public void test2(){
//        Response res = dashBoardStep("cards/"+ getAccessToken.cardId +"/pan", 200, getAccessToken.token);
//        PanCvvResponse response = res.as(PanCvvResponse.class);
//        verify("pan should contain 16 digits", checkString(response.getPan(),16),true,true);
//
//    }
//
//    @Test(description = "Request pan of not real card")
//    public void test3(){
//        Response res = dashBoardStep("cards/"+ getAccessToken.cardId +"/pan", 200, getAccessToken.token);
//        PanCvvResponse response = res.as(PanCvvResponse.class);
//        verify("pan should contain 16 digits", checkString(response.getPan(),16),true,true);
//
//    }
//
//    /// TO DO
//    @Test(description = "Try to get this option for blocked / inactive card etc")
//    public void test8(){
//        Response res = dashBoardStep("cards/"+ getAccessToken.cardId +"/pan", 200, getAccessToken.token);
//        PanCvvResponse response = res.as(PanCvvResponse.class);
//        verify("pan should contain 16 digits", checkString(response.getPan(),16),true,true);
//
//    }


}
