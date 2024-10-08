package models.mbackend.request.profile.password;

import lombok.AllArgsConstructor;

import static utils.EncodingUtils.toSha256;

@AllArgsConstructor
public class ChangeProfilePassword {

    public final String pin_hash;

    // For attaching data to report
    @Override
    public String toString() {
        return "{\n" +
                "            \"pin_hash\" : \"" + toSha256(pin_hash) + "\"\n" +
                "          }";
    }

}
