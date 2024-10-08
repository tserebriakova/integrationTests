package tests.endpoints.mbackend.cards.card.transactions;

import io.qameta.allure.Feature;
import models.mbackend.response.cards.card.CardsResponseModel;
import models.mbackend.response.cards.card.transactions.TransactionsResponseModel;
import org.testng.annotations.Test;
import steps.ApiSteps;

import static enums.CardStatus.ACTIVE;

@Test(groups={"auth-client"})
public class TransactionsTest extends ApiSteps {

    @Feature("Транзакции по карте клиента")
    @Test(description = "Проверка возможности получения транзакций по карте клиента")
    public void getCardTransactionsTest() {


        var cards =
                    mobileBackendSteps()
                            .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                            .as(CardsResponseModel[].class);
            var activeCards =
                    mobileBackendSteps()
                            .filterCadsByStatus(cards, ACTIVE);

        var transactions =
                mobileBackendSteps()
                        .mobileBackendProcess("/cards/" + activeCards.get(0).getId() + "/transactions", authSteps().getAccessToken(), 200)
                        .as(TransactionsResponseModel[].class);

        var transactionsOffset =
                mobileBackendSteps()
                        .mobileBackendProcess("/cards/" +activeCards.get(0).getId()+ "/transactions?limit=50&offset=0", authSteps().getAccessToken(), 200)
                        .as(TransactionsResponseModel[].class);
           }

    @Test(description = "Получение транзакции по id")
    public void transactionByIdTest() {

        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);
        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);
        var transactions =
                mobileBackendSteps()
                        .mobileBackendProcess("/cards/" + activeCards.get(0).getId() + "/transactions", authSteps().getAccessToken(), 200)
                        .as(TransactionsResponseModel[].class);
        var trById = mobileBackendSteps()
                .mobileBackendProcess("/transactions/" + transactions[0].getId(), authSteps().getAccessToken(), 200)
                .as(TransactionsResponseModel.class);

    }

//    @Test(description = "Find transaction ")
//    public void test4(){
//    }
//
//    @Test(description = "Try to get transactions for other users card")
//    public void test5(){
//    }
//
//    @Test(description = "Try to get transactions for not real card")
//    public void test6(){
//    }
//
//    /// TO DO
//    @Test(description = "Try to get this option for blocked / inactive card etc")
//    public void test7(){
//    }

}
