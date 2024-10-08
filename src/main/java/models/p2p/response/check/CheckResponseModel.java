package models.p2p.response.check;

import lombok.Data;
import models.p2p.response.errors.ErrorsBlockResponse;

@Data
public class CheckResponseModel {
    public int fee;
    public int intent_debit;
    public int display_debit;
    public int credit;
    public String status;
    ErrorsBlockResponse[] errors;

}
