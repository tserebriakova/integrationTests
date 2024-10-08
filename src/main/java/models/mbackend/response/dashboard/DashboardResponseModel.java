package models.mbackend.response.dashboard;

import lombok.Data;
import models.mbackend.response.cards.card.transactions.TransactionsResponseModel;
import models.mbackend.response.errors.ErrorsResponseModel;
import models.mbackend.response.messages.MessagesResponseModel;
@Data
public class DashboardResponseModel {

    public MessagesResponseModel[] messages;
    public ErrorsResponseModel[] errors;
    public TransactionsResponseModel[] elements;

}

