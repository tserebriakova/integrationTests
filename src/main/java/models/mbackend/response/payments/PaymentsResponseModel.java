package models.mbackend.response.payments;

import lombok.Data;
import models.mbackend.response.action.ActionResponseModel;

@Data
public class PaymentsResponseModel {
    public String image_url;
    public ActionResponseModel action;
    public String id;
    public String title;
}
