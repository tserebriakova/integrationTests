package models.flows.request;


import lombok.*;

@Data
public class FlowsRequestInputs {

    private final String id;
    private final String value;


    @Override
    public String toString() {
        return "\n\n        {\n" +
                "           \"id\": \"" + id + "\",\n" +
                "           \"value\": \"" + value + "\"\n" +
                "        }\n";
    }
}


