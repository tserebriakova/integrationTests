package models.mbackend.response.cards.card.design;

import lombok.Data;

@Data
public class DesignResponseModel {

    public boolean is_default;
    public int id;
    public String image_url;
    public String type;
    public boolean is_selected;
    public String color;

}
