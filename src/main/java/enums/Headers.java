package enums;

public enum Headers {

    IOS_DEVICE("iOS",
             "E701B167-BB0D-485C-B891-B01DF13B1CBC", "1.0.1", "14.4.2"),
    ANDROID_DEVICE("android",
             "e977098d1f232a62", "0.0.16-V290921", "30");

    private final String platform;
    private final String deviceUid;
    private final String versionApp;
    private final String versionDeviceSdk;


    Headers(String platform,  String deviceUid, String versionApp, String versionDeviceSdk) {
        this.platform = platform;
        this.deviceUid = deviceUid;
        this.versionApp = versionApp;
        this.versionDeviceSdk = versionDeviceSdk;
    }

    public String getPlatform() {
        return platform;
    }

    public String getDeviceUid() {
        return deviceUid;
    }

    public String getVersionApp() {
        return versionApp;
    }

    public String getVersionDeviceSdk() {
        return versionDeviceSdk;
    }

}
