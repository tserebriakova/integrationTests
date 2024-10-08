package steps.notify;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.notify.request.SubscribeRequestModel;

import static helpers.StepsUtils.requestSpecsBuilder;

public class NotifyBaseSteps{

    protected static RequestSpecification mainNotifyRequest(String url) {
        return requestSpecsBuilder(url);
    }

    public Response notifySubscribeRequest(String url, String token, String accessToken, String xVersion) throws JsonProcessingException {
        var data = new SubscribeRequestModel(token);
        return postSubscribeRequest(mainNotifyRequest(url), data, accessToken, 200, xVersion);
    }

    public Response postSubscribeRequest(RequestSpecification requestSpecification, SubscribeRequestModel body, String accessToken,
                                   int statusCode, String xVersion) throws JsonProcessingException {
        return (
                requestSpecification
                        .basePath("/subscribe")
                        .header("X-Version", xVersion)
                        .header("user-agent", "OxiBank_Release")                        .header("Authorization", "Bearer " + accessToken)
                        .body(new ObjectMapper().writeValueAsString(body))
                        .post()
                        .then()
                        .assertThat()
                        .statusCode(statusCode)
                        .extract()
                        .response());
    }

}
