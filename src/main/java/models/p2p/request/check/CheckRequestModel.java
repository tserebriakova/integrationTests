package models.p2p.request.check;

import lombok.AllArgsConstructor;
import models.p2p.intent.IntentModel;

@AllArgsConstructor
public class CheckRequestModel {

    private final IntentModel intent;

    // For attaching data to report
    @Override
    public String toString() {
        return intent.toString();
    }

}
