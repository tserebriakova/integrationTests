package steps.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.auth.request.AccountPin;
import models.auth.request.AddCertificate;
import models.auth.request.GetAccessToken;
import steps.ApiSteps;

import static helpers.StepsUtils.requestSpecsBuilder;

public class AuthBaseSteps extends ApiSteps {


    protected static RequestSpecification mainAuthRequest(String url) {
        return requestSpecsBuilder(url);
    }


    public Response postCertificate(
            RequestSpecification requestSpecification, String accessToken, AddCertificate body, int statusCode, String xVersion) {
        try {
            return (
                    requestSpecification
                            .basePath("/client/certificate")
                            .header("Authorization", "Bearer " + accessToken)
                            .header("X-Version", xVersion)
                        .header("user-agent", "OxiBank_Release")                            .body(new ObjectMapper().writeValueAsString(body))
                            .post()
                            .then()
                            .assertThat()
                            .statusCode(statusCode)
                            .extract()
                            .response());
        } catch (Exception e) {
            return null;
        }
    }


    public Response putChangeAccountPin(RequestSpecification requestSpecification,
                                        String accessToken, AccountPin body, int statusCode, String xVersion) {
        return (
                requestSpecification
                        .basePath("/client/pin/reset")
                        .header("Authorization", "Bearer " + accessToken)
                        .header("Accept-language", "uk")
                        .header("X-Version", xVersion)
                        .header("user-agent", "OxiBank_Release")                        .body(body.toString())
                        .put()
                        .then()
                        .assertThat()
                        .statusCode(statusCode)
                        .extract()
                        .response());
    }

    public Response postClientToken(RequestSpecification requestSpecification,
                                    GetAccessToken body, int statusCode, String xVersion) throws JsonProcessingException {
        return (
                requestSpecification
                        .basePath("/client/token")
                        .header("Accept-language", "uk")
                        .header("X-Version", xVersion)
                        .header("user-agent", "OxiBank_Release")                        .body(new ObjectMapper().writeValueAsString(body))
                        .post()
                        .then()
                        .assertThat()
                        .statusCode(statusCode)
                        .extract()
                        .response());
    }


}
