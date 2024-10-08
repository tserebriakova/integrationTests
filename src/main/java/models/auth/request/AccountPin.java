package models.auth.request;

import lombok.AllArgsConstructor;

import static utils.EncodingUtils.toSha256;


@AllArgsConstructor
public class AccountPin {
    private final String pin_hash;

    // For attaching data to report
    @Override
    public String toString() {
        return "{\n" +
                "            \"pin_hash\" : \"" + toSha256(pin_hash) + "\"\n" +
                "          }";
    }
}
