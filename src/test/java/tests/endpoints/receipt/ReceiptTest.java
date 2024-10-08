package tests.endpoints.receipt;

import enums.TransactionStatus;
import io.qameta.allure.Feature;
import models.mbackend.response.cards.card.CardsResponseModel;
import models.mbackend.response.cards.card.transactions.TransactionsResponseModel;
import org.testng.annotations.Test;
import steps.ApiSteps;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static enums.CardStatus.ACTIVE;
import static utils.PdfUtils.extractPdfContent;

@Test(groups = {"auth-client"})
public class ReceiptTest extends ApiSteps {

    List<TransactionsResponseModel> successTransactions;
    List<TransactionsResponseModel> failedTransactions;
    List<TransactionsResponseModel> reverseTransactions;

    @Feature("Квитанции")
    @Test(description = "Проверка возможности получения квитанции по исходящей валидной транзакции")
    public void receiptValidOutgoingTransactionTest() throws IOException, InvalidAlgorithmParameterException, NoSuchAlgorithmException {

        var cards =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH, authSteps().getAccessToken(), 200)
                        .as(CardsResponseModel[].class);
        var activeCards =
                mobileBackendSteps()
                        .filterCadsByStatus(cards, ACTIVE);

        var transactions =
                mobileBackendSteps()
                        .mobileBackendProcess(CARDS_METHOD_PATH + "/" + activeCards.get(0).getId() + "/transactions?limit=200", authSteps().getAccessToken(), 200)
                        .as(TransactionsResponseModel[].class);

        successTransactions = mobileBackendSteps().filterTransactionsByStatus(transactions, TransactionStatus.SUCCESS);
        failedTransactions = mobileBackendSteps().filterTransactionsByStatus(transactions, TransactionStatus.FAIL);
        reverseTransactions = mobileBackendSteps().filterReversalTransactions(transactions, true);

        var pdfReceipt =
                receiptSteps().getReceiptByTransactionId(successTransactions.get(0).getId()).asByteArray();

        var text = (extractPdfContent(pdfReceipt));
        var xxx = (text.split("\n"));


    }

    @Feature("Квитанции")
    @Test(description = "Проверка возможности получения квитанции по входящей валидной транзакции", dependsOnMethods = "receiptValidOutgoingTransactionTest")
    public void receiptValidIncomingTransactionTest() throws IOException, InvalidAlgorithmParameterException, NoSuchAlgorithmException {
        var pdfReceipt =
                receiptSteps().getReceiptByTransactionId(successTransactions.get(0).getId()).asByteArray();
    }

    @Feature("Квитанции")
    @Test(description = "Проверка возможности получения квитанции по реверс транзакции", dependsOnMethods = "receiptValidOutgoingTransactionTest")
    public void receiptReverseTransactionTest() throws IOException, InvalidAlgorithmParameterException, NoSuchAlgorithmException {

        var pdfReceipt =
                receiptSteps().getReceiptByTransactionId(reverseTransactions.get(0).getId()).asByteArray();

    }

    @Feature("Квитанции")
    @Test(description = "Проверка возможности получения квитанции по невалидной транзакции", dependsOnMethods = "receiptValidOutgoingTransactionTest")
    public void receiptInvalidTransactionTest() throws IOException, InvalidAlgorithmParameterException, NoSuchAlgorithmException {
        var tr = failedTransactions.get(0).getId().split("R");
        var pdfReceipt =
                receiptSteps().getReceiptByTransactionId(tr[0]).asByteArray();

    }
}
