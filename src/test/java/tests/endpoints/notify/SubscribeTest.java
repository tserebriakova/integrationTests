package tests.endpoints.notify;


import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import steps.ApiSteps;

import java.io.IOException;

@Test(groups={"auth-client"})
public class SubscribeTest extends ApiSteps {

    @Feature("PUSH Notification Subscription")
    @Test(description = "Проверка возможности установки токена устройства для получения пуш уведомлений")
    public void subscribePushNotificationTest() throws IOException {

        var response =
                notifySteps().setPushNotificationDeviceToken
                        ("cxXQNVlq2EA6lwA9Fr89Nn:APA91bG19gNUYkkSIwiX4EvQkEql_On983pq-OqIwf48_cHnjxzO98npVCuOBm0w3noY5VJMxdpC130eKun_NqurzavsMvgFSaHG7X5OzFV-VIXcQ8FyqdRUrIuSx44CbWCebvH_WNFW",
                                authSteps().getAccessToken()).asString();
        verify("Ответ должен содержать Subscribed successfully!", response,"Subscribed successfully!", true );
    }
}
