package steps.notify;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NotifySteps extends NotifyBaseSteps {
    private String baseUrl;
    private String xVersion;



    @Step("Установка токена для получения в дальнейшем пушей")
    public Response setPushNotificationDeviceToken(String token, String accessToken) throws JsonProcessingException {
        return notifySubscribeRequest(baseUrl, token, accessToken, xVersion);
    }

}
