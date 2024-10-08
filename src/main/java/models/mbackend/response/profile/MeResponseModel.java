package models.mbackend.response.profile;

import lombok.Data;

@Data
public class MeResponseModel {

    public String phone,
            type,
            name,
            inn,
            public_key,
            full_name,
            customer;
    public Settings settings;

}