package models.p2p.request.payments;

import lombok.AllArgsConstructor;

@AllArgsConstructor

public class CancelPaymentRequestModel {

    String payment_id;

    @Override
    public String toString() {
        return "{\n" +
                "  \"payment_id\": \"" + payment_id + "\"\n" +
                "}";
    }

}
