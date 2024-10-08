package models.notify.response;

import lombok.Data;
import models.mbackend.response.switchsms.SwitchSmsErrorsResponseModel;

@Data
public class SubscribeErrorResponseModel {
        public int status;
        public SwitchSmsErrorsResponseModel errors;
    }

