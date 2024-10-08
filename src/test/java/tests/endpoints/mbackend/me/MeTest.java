package tests.endpoints.mbackend.me;

import io.qameta.allure.Feature;
import models.mbackend.response.profile.MeResponseModel;
import org.testng.annotations.Test;
import steps.ApiSteps;

@Test(groups={"auth-client"})
public class MeTest extends ApiSteps {

    @Feature("Профиль Клиента")
    @Test(description = "Проверка получения данных профиля пользователя")
    public void verifyProfileInformationTest() {

        var me =
                mobileBackendSteps()
                        .mobileBackendProcess("/me", authSteps().getAccessToken(), 200)
                        .as(MeResponseModel.class);
    }


}
