package models.flows.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlowsResponseError {

    public String error_code;
    public String message;
    public String input_id;
    public String type;

}
