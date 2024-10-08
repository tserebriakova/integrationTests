package models.mbackend.response.cards.card.transactions.balance;

import lombok.Data;

@Data
public class TransactionBalanceResponseModel {
    public int personal;
    public int available_amount;
    public String currency;
}
