package models.flows.request;


import lombok.*;

import java.util.List;


@AllArgsConstructor
public class FlowsRequestModel {

    public List<FlowsRequestInputs> inputs;


    // For attaching data to report
    @Override
    public String toString() {
        return "{\n" +
                "    \"inputs\": " + inputs + "\n" +
                "}";
    }


}
