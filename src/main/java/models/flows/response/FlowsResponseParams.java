package models.flows.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FlowsResponseParams {

    public String title;
    public FlowsResponseAction action;
    public String style;

}
