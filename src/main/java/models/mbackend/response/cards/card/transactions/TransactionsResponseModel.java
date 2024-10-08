package models.mbackend.response.cards.card.transactions;

import lombok.Data;
import models.mbackend.response.action.ActionResponseModel;
import models.mbackend.response.cards.card.transactions.balance.TransactionBalanceResponseModel;
import models.mbackend.response.cards.card.transactions.currency.AccountCurrencyAmountResponseModel;
import models.mbackend.response.cards.card.transactions.currency.TransactionCurrencyAmountResponseModel;
import models.mbackend.response.cards.card.transactions.merchant.TransactionMerchantResponseModel;

import java.util.Date;

@Data
public class TransactionsResponseModel {
    public ActionResponseModel action;
    public String id;
    public String title;
    public String description;
    public int mcc;
    public int amount;
    public boolean hold;
    public int time;
    public String type;
    public String status;
    public TransactionBalanceResponseModel balance;
    public TransactionMerchantResponseModel merchant;
    public boolean reversal;
    public String image_url;
    public int operation_amount;
    public String currency;
    public String card_id;
    public String category_color;
    public boolean unknown_merchant;
    public String terminal_type;
    public TransactionCurrencyAmountResponseModel transaction_currency_amount;
    public AccountCurrencyAmountResponseModel account_currency_amount;
    public String transaction_type;
    public String transaction_type_id;
    public String merchant_name;
    public Date reversal_date;
    public String error_message;
}
