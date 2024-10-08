package steps.receipt;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static helpers.StepsUtils.requestSpecsBuilder;

public class ReceiptBaseSteps {


    protected static RequestSpecification mainReceiptRequest(String url) {
        return requestSpecsBuilder(url);
    }


    @Step("Checking method {0}")
    protected Response getReceiptRequest(String methodPath, RequestSpecification requestSpecification,
                                      int statusCode, String xVersion) {
        return (
                requestSpecification
                        .basePath(methodPath)
                        .contentType("application/pdf")
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
