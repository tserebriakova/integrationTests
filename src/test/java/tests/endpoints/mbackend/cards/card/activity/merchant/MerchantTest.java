package tests.endpoints.mbackend.cards.card.activity.merchant;

import io.qameta.allure.Feature;
import models.mbackend.response.cards.card.CardsResponseModel;
import models.mbackend.response.cards.card.activity.merchant.MerchantResponseModel;
import models.mbackend.response.cards.card.transactions.TransactionsResponseModel;
import org.testng.annotations.Test;
import steps.ApiSteps;

import static enums.CardStatus.ACTIVE;
@Test(groups={"auth-client"})
public class MerchantTest extends ApiSteps {

    @Feature("Статистика по ТОРГОВЦАМ карты Клиента")
    @Test(description = "Проверка возможности получения статистики трат по торговцам по карте пользователя")
    public void cardActivityMerchantTest() {

        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);
        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);

        var activityCategoryCurrentMonth =
                mobileBackendSteps()
                        .mobileBackendProcess("/cards/" +activeCards.get(0).getId()+ "/activity?type=MERCHANT", authSteps().getAccessToken(), 200)
                        .as(MerchantResponseModel.class);

        var activityCategorySelectedMonth =
                mobileBackendSteps()
                        .mobileBackendProcess("/cards/" +activeCards.get(0).getId()+ "/activity?type=MERCHANT&from=1630454400&to=1632960000", authSteps().getAccessToken(), 200)
                        .as(MerchantResponseModel.class);

        var activityCategoryDetails =
                mobileBackendSteps()
                        .mobileBackendProcess("/cards/" +activeCards.get(0).getId()+ "/transactions?limit=50&offset=0&type_id="+activityCategorySelectedMonth.getElements()[0].getId()+"&from=1627776000&to=1632960000&title="+activityCategorySelectedMonth.getElements()[0].getTitle()+"", authSteps().getAccessToken(), 200)
                        .as(TransactionsResponseModel[].class);


    }
}
