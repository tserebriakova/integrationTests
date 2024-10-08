package steps.mbackend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.flows.response.FlowsResponseModel;
import models.mbackend.request.card.design.ChangeCardDesign;
import models.mbackend.request.card.limits.ChangeCardLimit;
import models.mbackend.request.card.pin.ChangeCardPin;
import models.mbackend.request.card.security.ChangeCardSecurity;
import models.mbackend.request.card.status.ChangeCardStatus;
import models.mbackend.request.init.InitReloginRequestModel;
import models.mbackend.request.switchsms.SwitchSmsRequestModel;
import models.mbackend.response.init.InitResponseModel;

import static helpers.StepsUtils.requestSpecsBuilder;

public class MBackendBaseSteps  {


    protected static RequestSpecification mainMobileBackendRequest(String url) {
        return requestSpecsBuilder(url);
    }

    public FlowsResponseModel initOnboarding(String url, String xVersion) throws JsonProcessingException {
        var data = new InitReloginRequestModel("uapi_onboard_dgb_v3");
        return getInitRequest(mainMobileBackendRequest(url), data, 200, xVersion).as(FlowsResponseModel.class);
    }

    public InitResponseModel initReLogin(String url, String xVersion, String certificateId, String refreshToken) throws JsonProcessingException {
        var data = new InitReloginRequestModel(certificateId, refreshToken,"uapi_onboard_oksi_v3");
        return getInitRequest(mainMobileBackendRequest(url), data, 200, xVersion).as(InitResponseModel.class);
    }

    public Response putSwitchSms(String methodPath, RequestSpecification requestSpecification,
                                 SwitchSmsRequestModel body, int statusCode, String accessToken, String xVersion) throws JsonProcessingException {
        return (
                requestSpecification
                        .basePath(methodPath)
                        .header("Authorization", "Bearer " + accessToken)
                        .header("Accept-language","uk")
                        .header("X-Version", xVersion)
                        .header("user-agent", "OxiBank_Release")                        .body(new ObjectMapper().writeValueAsString(body))
                        .put()
                        .then()
                        .assertThat()
                        .statusCode(statusCode)
                        .extract()
                        .response());
    }


    public Response getInitRequest(RequestSpecification requestSpecification, InitReloginRequestModel body,
                                        int statusCode, String xVersion) throws JsonProcessingException {
        return (
                requestSpecification
                        .basePath("/init")
                        .header("X-Version", xVersion)
                        .header("user-agent", "OxiBank_Release")                        .body(new ObjectMapper().writeValueAsString(body))
                        .post()
                        .then()
                        .assertThat()
                        .statusCode(statusCode)
                        .extract()
                        .response());
    }


    // Request builders

    @Step("Checking method {0}")
    public Response getMobileBackendRequest(String methodPath, RequestSpecification requestSpecification,
                                            String accessToken, int statusCode, String xVersion) {
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



    public Response patchChangeCardDesign(String methodPath, RequestSpecification requestSpecification,
                                          ChangeCardDesign body, int statusCode,String accessToken, String xVersion) {
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

    public Response putChangeCardStatus(String methodPath, RequestSpecification requestSpecification,
                                        ChangeCardStatus body, int statusCode,String accessToken, String xVersion) {
        return (
                requestSpecification
                        .basePath(methodPath)
                        .header("Authorization", "Bearer " + accessToken)
                        .header("Accept-language","uk")
                        .header("X-Version", xVersion)
                        .header("user-agent", "OxiBank_Release")                        .body(body.toString())
                        .put()
                        .then()
                        .assertThat()
                        .statusCode(statusCode)
                        .extract()
                        .response());
    }

    public Response putChangeCardLimit(String methodPath, RequestSpecification requestSpecification,
                                       ChangeCardLimit body, int statusCode,String accessToken, String xVersion) {
        return (
                requestSpecification
                        .basePath(methodPath)
                        .header("Authorization", "Bearer " + accessToken)
                        .header("Accept-language","uk")
                        .header("X-Version", xVersion)
                        .header("user-agent", "OxiBank_Release")                        .body(body.toString())
                        .put()
                        .then()
                        .assertThat()
                        .statusCode(statusCode)
                        .extract()
                        .response());
    }

    public Response putChangeCardSecurity(String methodPath, RequestSpecification requestSpecification,
                                          ChangeCardSecurity body, int statusCode,String accessToken, String xVersion) {
        return (
                requestSpecification
                        .basePath(methodPath)
                        .header("Authorization", "Bearer " + accessToken)
                        .header("Accept-language","uk")
                        .header("X-Version", xVersion)
                        .header("user-agent", "OxiBank_Release")                        .body(body.toString())
                        .put()
                        .then()
                        .assertThat()
                        .statusCode(statusCode)
                        .extract()
                        .response());
    }

    public Response putChangeCardPin(String methodPath, RequestSpecification requestSpecification,
                                     ChangeCardPin body, int statusCode,String accessToken, String xVersion) {
        return (
                requestSpecification
                        .basePath(methodPath)
                        .header("Authorization", "Bearer " + accessToken)
                        .header("Accept-language","uk")
                        .header("X-Version", xVersion)
                        .header("user-agent", "OxiBank_Release")                        .body(body.toString())
                        .put()
                        .then()
                        .assertThat()
                        .statusCode(statusCode)
                        .extract()
                        .response());
    }



}
