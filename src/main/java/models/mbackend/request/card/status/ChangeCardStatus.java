package models.mbackend.request.card.status;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChangeCardStatus {

    private final String cardStatus;

    // For attaching data to report
    @Override
    public String toString() {
        return "{\n" +
                "            \"status\" : \"" + cardStatus + "\"\n" +
                "          }";
    }

}
