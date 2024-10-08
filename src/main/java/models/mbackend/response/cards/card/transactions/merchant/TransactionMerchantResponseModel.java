package models.mbackend.response.cards.card.transactions.merchant;

import lombok.Data;

@Data
public class TransactionMerchantResponseModel {
    public String id;
    public String title;
    public String address;
    public String type;
    public String image_url;
}
