package models.mbackend.response.cards.card;

import lombok.Data;

@Data
public class CardsBalanceResponseModel {
    public int personal;
    public int credit;
    public String type;
    public int available_amount;
    public String currency;
}
