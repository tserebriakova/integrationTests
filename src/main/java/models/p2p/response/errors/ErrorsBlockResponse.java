package models.p2p.response.errors;

import lombok.Data;

@Data
public class ErrorsBlockResponse {

    String message;
    String error_code;
    String type;
    String input_id;

}
