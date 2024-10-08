package steps.p2p;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import models.p2p.intent.IntentModel;
import models.p2p.request.payments.CancelPaymentRequestModel;
import models.p2p.request.payments.PaymentsRequestModel;
import models.p2p.request.payments.PaymentsRequestModel3Ds;

import static helpers.StepsUtils.requestSpecsBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class P2PBaseSteps{

    private int
            fee,
            intent_debit,
            display_debit,
            credit;
    private String  id,
                    state,
            paymentStatus;
    private boolean cancelResponse;
    private String userActionUrl;
    public boolean required_3ds;

    protected static RequestSpecification mainP2PRequest(String url) {
        return requestSpecsBuilder(url);
    }
    public Response postP2pCheck(String methodPath, RequestSpecification requestSpecification,
                                 IntentModel body, int statusCode, String accessToken, String xVersion) {
        return (
                requestSpecification
                        .basePath(methodPath)
                        .header("Authorization", "Bearer " + accessToken)
                        .header("Accept-language","uk")
                        .header("X-Version", xVersion)
                        .header("user-agent", "OxiBank_Release")                        .body(body.toString())
                        .post()
                        .then()
                        .assertThat()
                        .statusCode(statusCode)
                        .extract()
                        .response());
    }

    public Response postMakeP2p(String methodPath, RequestSpecification requestSpecification,
                                PaymentsRequestModel body, int statusCode, String accessToken, String xVersion) {
        return (
                requestSpecification
                        .basePath(methodPath)
                        .header("Authorization", "Bearer " + accessToken)
                        .header("Accept-language","uk")
                        .header("X-Version", xVersion)
                        .header("user-agent", "OxiBank_Release")                        .body(body.toString())
                        .post()
                        .then()
                        .assertThat()
                        .statusCode(statusCode)
                        .extract()
                        .response());
    }

    public Response postMakeP2p3Ds(String methodPath, RequestSpecification requestSpecification,
                                   PaymentsRequestModel3Ds body, int statusCode, String accessToken, String xVersion) {
        return (
                requestSpecification
                        .basePath(methodPath)
                        .header("Authorization", "Bearer " + accessToken)
                        .header("Accept-language","uk")
                        .header("X-Version", xVersion)
                        .header("user-agent", "OxiBank_Release")                        .body(body.toString())
                        .post()
                        .then()
                        .assertThat()
                        .statusCode(statusCode)
                        .extract()
                        .response());
    }

    public Response patchCancelPayment(String methodPath, RequestSpecification requestSpecification,
                                       CancelPaymentRequestModel body, int statusCode, String accessToken, String xVersion) {
        return (
                requestSpecification
                        .basePath(methodPath)
                        .header("Authorization", "Bearer " + accessToken)
                        .header("Accept-language","uk")
                        .header("X-Version", xVersion)
                        .header("user-agent", "OxiBank_Release")                        .body(body.toString())
                        .patch()
                        .then()
                        .assertThat()
                        .statusCode(statusCode)
                        .extract()
                        .response());
    }

    public Response getP2pStatus(String methodPath, RequestSpecification requestSpecification,
                                  int statusCode, String accessToken, String xVersion) {
        return (
                requestSpecification
                        .basePath(methodPath)
                        .header("Authorization", "Bearer " + accessToken)
                        .header("Accept-language","uk")
                        .header("X-Version", xVersion)
                        .header("user-agent", "OxiBank_Release")                        .get()
                        .then()
                        .assertThat()
                        .statusCode(statusCode)
                        .extract()
                        .response());
    }

}
