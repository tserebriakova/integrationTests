package models.flows.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlowsResponseModel {

    public int status;
    public String uuid;
    public String task_id;
    public String step;
    public FlowsResponseElements[] elements;
    public boolean finished;
    public FlowsResponseError[] errors;
    public String result_id;
    public boolean success;
    public FlowsResponseMessage[] messages;
    public FlowsResponseVars vars;

}

