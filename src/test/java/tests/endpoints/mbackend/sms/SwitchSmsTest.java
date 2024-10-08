package tests.endpoints.mbackend.sms;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import steps.ApiSteps;
@Test(groups={"auth-client"})
public class SwitchSmsTest extends ApiSteps {

    @Feature("SMS/PUSH SWITCHER")
    @Test(description = "Проверка возможности переключения между смс и пуш уведомлениями")
    public void smsSwitcherTest() throws JsonProcessingException {

        var response1 =
                mobileBackendSteps().switchSmsPush("/switch-sms", true, authSteps().getAccessToken(),200);
        var response11 =
                mobileBackendSteps().switchSmsPush("/switch-sms", true, authSteps().getAccessToken(),200);
        var response2 =
                mobileBackendSteps().switchSmsPush("/switch-sms", false, authSteps().getAccessToken(),200);
        var response22 =
                mobileBackendSteps().switchSmsPush("/switch-sms", false, authSteps().getAccessToken(),200);

    }

}
