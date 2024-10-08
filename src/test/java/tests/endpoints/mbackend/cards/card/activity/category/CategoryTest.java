package tests.endpoints.mbackend.cards.card.activity.category;

import io.qameta.allure.Feature;
import models.mbackend.response.cards.card.CardsResponseModel;
import models.mbackend.response.cards.card.activity.category.CategoryResponseModel;
import models.mbackend.response.cards.card.transactions.TransactionsResponseModel;
import org.testng.annotations.Test;
import steps.ApiSteps;

import static enums.CardStatus.ACTIVE;
@Test(groups={"auth-client"})
public class CategoryTest extends ApiSteps {

    @Feature("Статистика по КАТЕГОРИЯМ карты Клиента")
    @Test(description = "Проверка возможности получения статистики трат по категориям по карте пользователя")
    public void cardActivityCategoryTest(){


        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);

        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);

        var activityCategoryCurrentMonth =
                mobileBackendSteps()
                        .mobileBackendProcess("/cards/" +activeCards.get(0).getId()+ "/activity?type=CATEGORY", authSteps().getAccessToken(), 200)
                        .as(CategoryResponseModel.class);

        var activityCategorySelectedMonth =
                mobileBackendSteps()
                        .mobileBackendProcess("/cards/" +activeCards.get(0).getId()+ "/activity?type=CATEGORY&from=1630454400&to=1632960000", authSteps().getAccessToken(), 200)
                        .as(CategoryResponseModel.class);

        var activityCategoryDetails =
                mobileBackendSteps()
                        .mobileBackendProcess("/cards/" +activeCards.get(0).getId()+ "/transactions?limit=50&offset=0&type_id="+activityCategorySelectedMonth.getElements()[0].getId()+"&from=1627776000&to=1632960000", authSteps().getAccessToken(), 200)
                        .as(TransactionsResponseModel[].class);
    }


}
