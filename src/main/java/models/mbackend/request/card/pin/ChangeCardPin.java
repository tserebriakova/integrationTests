package models.mbackend.request.card.pin;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChangeCardPin {

    private final String pin;

    // For attaching data to report
    @Override
    public String toString() {
        return "{\n" +
                "            \"pin\" : \"" + pin + "\"\n" +
                "          }";
    }

}
