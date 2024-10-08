package models.mbackend.request.card.limits;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChangeCardLimit {

    private final String limitId;
    private final int limitAmount;

    // For attaching data to report
    @Override
    public String toString() {
        return "{\n" +
                "            \"id\" : \"" + limitId + "\",\n" +
                "            \"value\" : " + limitAmount + "\n" +
                "          }";
    }

}
