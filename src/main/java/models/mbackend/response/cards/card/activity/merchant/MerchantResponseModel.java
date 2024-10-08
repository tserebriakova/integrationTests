package models.mbackend.response.cards.card.activity.merchant;

import lombok.Data;

@Data
public class MerchantResponseModel {
    public String id;
    public String type;
    public int amount;
    public MerchantElementsResponse[] elements;
    public int date_from;
    public int date_to;
    public String currency;
}
