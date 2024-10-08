package models.p2p.response.payments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CancelConfigResponseModel {

    public boolean enable;
    public int time;

}
