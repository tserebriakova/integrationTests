package tests.endpoints.p2p.uapi;

import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import steps.ApiSteps;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;

import static enums.TransactionDirections.OTHER_PAN_OTHER_PAN;
@Test(groups={"auth-client"})
public class P2p3DsTest extends ApiSteps {

    @Feature("ПЛАТЕЖИ P2P")
    @Test(description = "Проверка возможности перевода с 3DS (получение 3DS ссылки)")
    public void n3dsTest() throws NoSuchAlgorithmException,
            SignatureException, InvalidKeyException, InterruptedException {

        var intent =
                mobileBackendSteps()
                        .generateIntent3Ds(getPanFor3Ds(), OTHER_PAN_OTHER_PAN, flowsSteps().getUserId(), 1000);

        intent
                .setCredit_dst_id(getPanNumber());

        PrivateKey privateKey = authSteps().getPrivateKey();

        p2pSteps()
                .checkFee(intent, authSteps().getAccessToken());
        intent
                .setFee(p2pSteps().getFee());
        intent
                .setDebit_amount(10000+ p2pSteps().getFee());

        p2pSteps()
                .makeP2Pwith3Ds(intent, privateKey, authSteps().getAccessToken());

        for (int i = 0; i < 10; i++) {
            if (p2pSteps().getUserActionUrl() == null){
                p2pSteps().checkStatus(p2pSteps().getId(), authSteps().getAccessToken());
            }
            else break;
        }
        verify("3DS ссылка получена", p2pSteps().getUserActionUrl(), null, false);
        }
    }

