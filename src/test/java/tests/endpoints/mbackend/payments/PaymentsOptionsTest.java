package tests.endpoints.mbackend.payments;

import io.qameta.allure.Feature;
import models.mbackend.response.payments.PaymentsResponseModel;
import org.testng.annotations.Test;
import steps.ApiSteps;

@Test(groups={"auth-client"})
public class PaymentsOptionsTest extends ApiSteps {
    @Feature("Платежи")
    @Test(description = "Проверка возможности получения платежей")
    public void getPaymentsTest() {

        var payments =
                mobileBackendSteps()
                        .mobileBackendProcess("/payments", authSteps().getAccessToken(), 200)
                        .as(PaymentsResponseModel[].class);
    }

}
