package steps.flows;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import models.flows.request.FlowsRequestInputs;
import models.flows.request.FlowsRequestModel;
import models.flows.response.FlowsResponseElements;
import models.flows.response.FlowsResponseError;
import models.flows.response.FlowsResponseMessage;
import models.flows.response.FlowsResponseVars;

import java.util.ArrayList;

import static helpers.StepsUtils.requestSpecsBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlowsBaseSteps {

    protected static final String newPin = "1212";

    protected static RequestSpecification mainFlowsRequest(String uri) {
        return requestSpecsBuilder(uri);
    }


    private String
                    uuid,
                    taskId,
                    step,
                    accessToken,
                    userId,
                    resultId;

    private boolean success,
                    finished;

    private FlowsResponseError[] errorsBlock;
    private FlowsResponseMessage[] messages;
    private FlowsResponseElements[] elements;
    private FlowsResponseVars vars;



    public FlowsRequestModel getFlowsRequestModel(String id, String value) {
        var inputs = new FlowsRequestInputs(id, value);
        var listOfInputs = new ArrayList<FlowsRequestInputs>();
        listOfInputs.add(inputs);
        return new FlowsRequestModel(listOfInputs);
    }

    public FlowsRequestModel getFlowsRequestModel(String id, String value, String id2, String value2,
                                                  String id3, String value3, String id4, String value4) {
        var inputsFirst = new FlowsRequestInputs(id, value);
        var inputsSecond = new FlowsRequestInputs(id2, value2);
        var inputsThird = new FlowsRequestInputs(id3, value3);
        var inputsFourth = new FlowsRequestInputs(id4, value4);
        var listOfInputs = new ArrayList<FlowsRequestInputs>();
        listOfInputs.add(inputsFirst);
        listOfInputs.add(inputsSecond);
        listOfInputs.add(inputsThird);
        listOfInputs.add(inputsFourth);
        return new FlowsRequestModel(listOfInputs);
    }


    public Response getFlowsCompleteRequest(RequestSpecification requestSpecification, FlowsRequestModel body,
                                            String uuid, String taskId, int statusCode, String xVersion) {
        Sleeper.sleep(1000L);
        return (
                requestSpecification
                        .basePath("/api/onboarding/v3/applications/" + uuid + "/" + taskId)
                        .header("Accept-language","uk")
                        .header("X-Version", xVersion)
                        .header("user-agent", "OxiBank_Release")                        .header("user-agent", "OxiBank_Release")
                        .body(body.toString())
                        .put()
                        .then()
                        .assertThat()
                        .statusCode(statusCode)
                        .extract()
                        .response());
    }


}
