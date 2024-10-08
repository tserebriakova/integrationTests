package models.mbackend.request.card.security;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChangeCardSecurity {

    private final String securityType;
    private final boolean activate;

    // For attaching data to report
    @Override
    public String toString() {
        return "{\n" +
                "            \"id\" : \"" + securityType + "\",\n" +
                "            \"is_on\" : " + activate + "\n" +
                "          }";
    }

}
