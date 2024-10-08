package steps.receipt;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class ReceiptSteps extends ReceiptBaseSteps{
    private String baseUrl;
    private String xVersion;

    public ReceiptSteps(String baseUrl, String xVersion) {
        this.baseUrl = baseUrl;
        this.xVersion = xVersion;
    }

@Step("Попытка получать квитанцию по номеру транзакции {0}")
public Response getReceiptByTransactionId(String trId) {
    return
            getReceiptRequest("/receipt/"+ trId, mainReceiptRequest(baseUrl), 200, xVersion);
}

}
