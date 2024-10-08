package models.mbackend.request.card.design;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChangeCardDesign {

    private final String cardDesignId;
    private final String cardName;

    // For attaching data to report
    @Override
    public String toString() {
        return "{\n" +
                "            \"id\" : \"" + cardDesignId + "\",\n" +
                "            \"name\" : \"" + cardName + "\"\n" +
                "          }";
    }

}
