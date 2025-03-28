package models.p2p.request.payments;

import lombok.AllArgsConstructor;
import models.p2p.intent.IntentModel;

import static utils.GenerateCertificates.encodeBase64;

@AllArgsConstructor
public class PaymentsRequestModel3Ds {

    private final String sign;
    private final IntentModel intent;

    // For attaching data to report
    @Override
    public String toString() {
        return "{\n" +
                "            \"intent\" : \"" + encodeBase64(intent.toString().replaceAll("[\\n\\t ]", "")) + "\",\n" +
                "            \"sign\" : \"" + sign + "\",\n" +
                "          \"card_info\":{\"card_cvv\":\"926\",\"exp_month\":\"10\",\"exp_year\":\"24\"}}";
    }

}


