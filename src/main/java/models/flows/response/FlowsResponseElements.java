package models.flows.response;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlowsResponseElements {

    public String id;
    public String name;
    public String type;
    public boolean required;
    public boolean read_only;
    public boolean override_id;
    public FlowsResponseParams params;
    public String value;

}
