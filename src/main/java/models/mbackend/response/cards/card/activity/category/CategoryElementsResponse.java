package models.mbackend.response.cards.card.activity.category;

import lombok.Data;

@Data
public class CategoryElementsResponse {
    public String id;
    public int amount;
    public String color;
    public String title;
    public String image_url;
    public int payment_count;
    public String currency;
}
