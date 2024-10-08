package utils;

public class EncodingUtils {

    public static String toSha256(String str) {
        return org.apache.commons.codec.digest.DigestUtils.sha256Hex(str);
    }

}
