package models.mbackend.response.cards.card;

import lombok.Data;
import models.mbackend.response.action.ActionResponseModel;
@Data
public class CardsResponseModel {
    public String id;
    public String mask;
    public String status;
    public String color;
    public CardsBalanceResponseModel balance;
    public String type;
    public ActionResponseModel action;
    public String title;
    public String expiration_date;
    public String image_url;
}
