package tests.endpoints.mbackend.cards.card.cvv;

import models.mbackend.response.cards.card.CardsResponseModel;
import models.mbackend.response.cards.card.pancvv.PanCvvResponseModel;
import org.testng.annotations.Test;
import steps.ApiSteps;

import static enums.CardStatus.ACTIVE;

@Test(groups = {"auth-client"})
public class CvvTest extends ApiSteps {

    @Test(description = "Проверка возможности получения cvv активной карты")
    public void activeCardCvvTest() {


        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);
        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);

        var cvv =
                mobileBackendSteps()
                        .mobileBackendProcess("/cards/" + activeCards.get(0).getId() + "/cvv", authSteps().getAccessToken(), 200)
                        .as(PanCvvResponseModel.class);

        checkString("cvv содержит 3 символа", cvv.getCvv(), 3);
    }


    @Test(description = "Проверка возможности получения cvv чужой карты")
    public void otherUserCardCvvTest() {

        var otherUserPanId = "1101000000101662141"; // user 0506546333
        mobileBackendSteps()
                .mobileBackendWrongProcess("/cards/" + otherUserPanId + "/cvv", authSteps().getAccessToken(), 404);
    }

    //
    @Test(description = "Попытка отправки невалидного PanId для получения CVV")
    public void invalidPanIdToGetCvvTest() {

        var otherUserPanId = "123213213sjdcd"; // user 0506546333
        mobileBackendSteps()
                .mobileBackendWrongProcess("/cards/" + otherUserPanId + "/cvv", authSteps().getAccessToken(), 404);
    }

//    // TO DO
//    @Test(description = "Try to get this option for blocked / inactive card etc")
//    public void test8(){
//        Response response = mobileBackendProcess("/cards/12345678/cvv", accessToken, 404);
//    }

}
