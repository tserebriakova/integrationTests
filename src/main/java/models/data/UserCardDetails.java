package models.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@AllArgsConstructor
@Data
@Setter
public class UserCardDetails {

    private String
                    panId,
                    pan,
                    status,
                    balance,
                    limitAmount,
                    limitCount,
                    security,
                    activityAmount;

}
