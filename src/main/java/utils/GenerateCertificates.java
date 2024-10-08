package utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;

import javax.security.auth.x500.X500Principal;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class GenerateCertificates {

    private static final String SIGNATURE_ALGORITHM = "SHA256withECDSA";

    private static final String EC_ALGORITHM = "EC";

    private static final String STD_NAME = "secp256r1";

    private static final String BEGIN_CSR = "-----BEGIN CERTIFICATE REQUEST-----\n";

    private static final String END_CSR = "\n-----END CERTIFICATE REQUEST-----\n";

    public static String encodeBase64(String value) {
        return encodeBase64(value.getBytes(StandardCharsets.UTF_8));
    }

    public static String encodeBase64(byte[] value) {
        return Base64.encodeBase64String(value);
    }


    public static ImmutablePair<String, PrivateKey> generateCSR(String cn) {
        try{
            var keyGen = KeyPairGenerator.getInstance(EC_ALGORITHM);
            keyGen.initialize(new ECGenParameterSpec(STD_NAME), new SecureRandom());
            var keyPair = keyGen.generateKeyPair();
            var csBuilder = new JcaContentSignerBuilder(SIGNATURE_ALGORITHM);
            ContentSigner signer;
            try {
                signer = csBuilder.build(keyPair.getPrivate());
            } catch (OperatorCreationException e) {
                throw new RuntimeException(e);
            }
            var p10Builder = new JcaPKCS10CertificationRequestBuilder(new X500Principal("C=UA, L=OD, O=AQA, CN=" + cn),
                    keyPair.getPublic());
            var csr = p10Builder.build(signer);
            var csrStr = BEGIN_CSR + Base64.encodeBase64String(csr.getEncoded()) + END_CSR;
            return new ImmutablePair<>(csrStr, keyPair.getPrivate());
        }
        catch (Exception e) {
            return null;
        }

    }

    public static String sign(PrivateKey privateKey, String data)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        try{
        var signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(data.getBytes(StandardCharsets.UTF_8));
        var realSig = signature.sign();
        return encodeBase64(realSig);}
        catch (Exception e){
            return null;
        }
    }

    public static String hmacSha256Hex(String key, String value) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_256, key.getBytes()).hmacHex(value);
    }


}
