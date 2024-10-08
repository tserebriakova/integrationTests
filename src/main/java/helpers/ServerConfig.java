package helpers;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

@Config.Sources({"classpath:${env}.properties"})
public interface ServerConfig extends Config {

    ServerConfig BASE_CONFIG = ConfigFactory.create(ServerConfig.class, System.getenv(), System.getProperties());

    @Key("FLOWS_URL")
    @DefaultValue("https://api-dev-qa.ftr.group")
    String getFlowsUrl();

    @Key("MOBILE_BACKEND_URL")
    @DefaultValue("https://api-dev-qa.ftr.group/mbackend")
    String getMobBackendUrl();

    @Key("NOTIFY_URL")
    @DefaultValue("https://api-dev-qa.ftr.group/notify")
    String getNotifyUrl();

    @Key("AUTH_URL")
    @DefaultValue("https://api-dev-qa.ftr.group/auth")
    String getAuthUrl();


    @Key("SMPAY_URL")
    @DefaultValue("https://api-dev-qa.ftr.group/smpay")
    String getSmPayUrl();

    @Key("RECEIPT_URL")
    @DefaultValue("https://api-dev-qa.ftr.group/bank")
    String getReceptUrl();


    @Key("P2P_URL")
    @DefaultValue("https://api-dev-qa.ftr.group/p2p")
    String getP2PUrl();

    @Key("X_VERSION")
    @DefaultValue("V120122")
    String getXVersion();
}
