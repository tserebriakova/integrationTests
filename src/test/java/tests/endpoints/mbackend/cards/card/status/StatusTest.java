//package tests.endpoints.mbackend.cards.card.status;
//
//import models.mbackend.request.card.status.ChangeCardStatus;
//import models.mbackend.response.cards.card.CardsResponseModel;
//import models.mbackend.response.cards.card.options.OptionsResponseModel;
//import org.testng.annotations.Test;
//import steps.ApiSteps;
//
//import static enums.CardStatus.*;
//
//
//@Test(groups = {"auth-client"})
//public class StatusTest extends ApiSteps {
//
//    @Test(description = "Проверка возможности блокировки карты клиентом")
//    public void changeCardStatusTest() {
//
////        {
////            "id": "1101000000101662059",
////                "mask": "524872******5776",
////                "status": "ACTIVE",
////                "color": "#000000",
////                "balance": {
////            "personal": 265967,
////                    "credit": 0,
////                    "type": "balance",
////                    "available_amount": 265967,
////                    "currency": "₴"
////        },
//
////        https://api-dev-dgb.ftr.group/mbackend/v3/cards/1101000000101662059/options
////
////            "status": "ACTIVE"
////        }
//        var cards =
//                mobileBackendSteps()
//                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
//                        .as(CardsResponseModel[].class);
//        var activeCards =
//                mobileBackendSteps()
//                        .filterCadsByStatus(cards, ACTIVE);
//        var inactive =
//                mobileBackendSteps()
//                        .filterCadsByStatus(cards, INACTIVE);
//        var blocked =
//                mobileBackendSteps()
//                        .filterCadsByStatus(cards, BLOCKED);
//        var inactiveByPin =
//                mobileBackendSteps()
//                        .filterCadsByStatus(cards, INACTIVE_BY_PIN);
//        var cardToBeBlocked = activeCards.get(0).getId();
//        var blockCard =
//                mobileBackendSteps()
//                        .changeCardStatus("/v2/cards/" + cardToBeBlocked + "/status", authSteps().getAccessToken(),
//                                new ChangeCardStatus("4"), 200)
//                        .as(OptionsResponseModel.class);
//        // GET CARD STATUS FROM CARD METHOD
//        var actualCardStatusAfterBlockingMethodCards = mobileBackendSteps().filterCadsByPanId(mobileBackendSteps().mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
//                .as(CardsResponseModel[].class), cardToBeBlocked).get(0).getStatus();
//
//        //GET CARD STATUS FROM CARD OPTIONS METHOD
//        var actualCardStatusAfterBlockingMethodCardOptions = mobileBackendSteps().
//        var unlockCard =
//                mobileBackendSteps()
//                        .changeCardStatus("/v2/cards/" + activeCards.get(0).getId() + "/status", authSteps().getAccessToken(),
//                                new ChangeCardStatus("1"), 200)
//                        .as(OptionsResponseModel.class);
//
//
//    }
//
////    @Test(description = "Change card status which is available for user")
////    public void test2(){
////    }
////
////    @Test(description = "Try to Change card status which is NOT available for user")
////    public void test3(){
////    }
////
////    @Test(description = "Try to get card status for other users card")
////    public void test4(){
////    }
////
////    @Test(description = "Try to get card status for not real card")
////    public void test5(){
////    }
//}
