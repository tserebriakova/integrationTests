package models.mbackend.response.cards.card.limits;

import models.mbackend.response.action.ActionResponseModel;

public class LimitElementResponseModel {

    public int amount;
    public boolean right_arrow;
    public int spent;
    public ActionResponseModel action;
    public String style;
    public String id;
    public String currency;
    public String type;
    public String limit_type;

}
