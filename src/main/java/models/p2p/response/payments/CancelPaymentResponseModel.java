package models.p2p.response.payments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CancelPaymentResponseModel {

    boolean was_canceled;

}
