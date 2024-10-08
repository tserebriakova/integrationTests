package tests.endpoints.mbackend.cards.card.design;

import io.qameta.allure.Feature;
import models.mbackend.request.card.design.ChangeCardDesign;
import models.mbackend.response.cards.card.CardsResponseModel;
import models.mbackend.response.cards.card.design.DesignResponseModel;
import org.testng.annotations.Test;
import steps.ApiSteps;

import static enums.CardStatus.ACTIVE;
@Test(groups={"auth-client"})
public class DesignTest extends ApiSteps {

    @Feature("Дизайн карты Клиента")
    @Test(description = "Проверка возможности получения рубашек для смены дизайна карты")
    public void getCardDesignTest()  {


        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);
        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);

        var design =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH +"/" +activeCards.get(0).getId()+ "/design", authSteps().getAccessToken(), 200)
                        .as(DesignResponseModel[].class);
    }

    @Feature("Дизайн карты Клиента")
    @Test(description = "Проверка возможности смены дизайна карты")
    public void changeCardDesignTest() {

        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);
        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);

        var allCardDesigns =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH +"/" +activeCards.get(0).getId()+ "/design", authSteps().getAccessToken(), 200)
                        .as(DesignResponseModel[].class);

        var oldSelectedDesign = mobileBackendSteps().filterSelectedCardDesign(allCardDesigns, true);
        var currentAvailableDesigns = mobileBackendSteps().filterSelectedCardDesign(allCardDesigns, false);
        var designToBeApplied = currentAvailableDesigns.get(2);
        var changeCardDesign =
                mobileBackendSteps()
                        .changeCardDesign(CARDS_METHOD_PATH  +"/"+activeCards.get(0).getId()+ "/design", authSteps().getAccessToken(),
                                new ChangeCardDesign(String.valueOf(designToBeApplied.id) ,"white card"), 200);


        var cardDesignsAfterChanges =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH  +"/"+activeCards.get(0).getId()+ "/design", authSteps().getAccessToken(), 200)
                        .as(DesignResponseModel[].class);

        var selectedDesignAfterChanges = mobileBackendSteps().filterSelectedCardDesign(cardDesignsAfterChanges, true).get(0);

        var getCardDetails =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH  +"/" + activeCards.get(0).getId(), authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel.class);

        verify("дизайн карты успешно поменялся в разделе Design", selectedDesignAfterChanges, designToBeApplied, true);
        verify("дизайн карты успешно поменялся в разделе Card Info", getCardDetails.getImage_url(), designToBeApplied.getImage_url() + "?hash=v1", true);

    }

//    @Test(description = "Проверка возможности смены названия карты")
//    public void test3(){
//    }
//
//
//    @Test(description = "Попытка смены дизайна карты на несуществующий")
//    public void test4(){
//    }
//
//    @Test(description = "Проверка смены названия карты - Проверка максимальной длины")
//    public void test5(){
//    }
//
//    @Test(description = "Попытка смена дизайна карты чужого пользователя")
//    public void test6(){
//    }
//
//    @Test(description = "Проверка смены названия карты чужого пользователя")
//    public void test7(){
//    }
//
//
//    /// TODO
//    @Test(description = "Провека возможности смены дизайна у заблокированной карты")
//    public void test8(){
//    }

}
