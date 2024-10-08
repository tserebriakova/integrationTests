package models.p2p.response.payments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentsResponseModel {

    public String id;
    public String status;
    public boolean required_3ds;
    public CancelConfigResponseModel cancel_config;
    public String operation_id;


}
