package models.mbackend.response.cards.card.activity.category;

import lombok.Data;

@Data
public class CategoryResponseModel {
    public String id;
    public String type;
    public int amount;
    public CategoryElementsResponse[] elements;
    public int date_from;
    public int date_to;
    public String currency;
}
