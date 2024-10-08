package models.mbackend.response.cards.card.transactions.currency;

import lombok.Data;

@Data
public class AccountCurrencyAmountResponseModel {
    public int total;
    public int fees;
    public String currency;
}
