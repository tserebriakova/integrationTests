package models.p2p.intent;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class IntentModel {

    private String intent;
    private String credit_dst_type;
    private String credit_dst_id;
    private int debit_amount;
    private String debit_src_type;
    private int credit_amount;
    private Long date_time;
    private String debit_src_id;
    private int fee;
    private String user_id;

    @Override
    public String toString() {
        return "{\n" +
                "            \"credit_amount\" : " + credit_amount + ",\n" +
                "            \"credit_dst_id\" : \"" + credit_dst_id + "\",\n" +
                "            \"credit_dst_type\" : \"" + credit_dst_type + "\",\n" +
                "            \"date_time\" : " + date_time + ",\n" +
                "            \"debit_amount\" : " + debit_amount + ",\n" +
                "            \"debit_src_id\" : \"" + debit_src_id + "\",\n" +
                "            \"debit_src_type\" : \"" + debit_src_type + "\",\n" +
                "            \"fee\" : " + fee + ",\n" +
                "            \"intent\" : \"" + intent + "\",\n" +
                "            \"user_id\" : \"" + user_id + "\"\n" +
                "          }";
    }

}
