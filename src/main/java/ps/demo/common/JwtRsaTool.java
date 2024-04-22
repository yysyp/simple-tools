package ps.demo.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
public class JwtRsaTool {

    public final static String RSA = "RSA";

    public static List<String> generatePrivatePublicKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        String privKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        String pubKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        return List.of(privKey, pubKey);
    }

    public static RSAPrivateKey getRSAPrivateKeyObj(String privateKeyStr) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] bytes = Base64.getMimeDecoder().decode(privateKeyStr);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return (RSAPrivateKey) keyFactory.generatePrivate(spec);
    }

    public static RSAPublicKey getRSAPublicKeyObj(String publicKeyStr) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String replaced = StringUtils.replace(StringUtils.replace(publicKeyStr, "-----BEGIN PUBLIC KEY-----", "")
                , "-----END PUBLIC KEY-----", "");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(replaced));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    public static String generateJwtTokenWithRSA(List<String> privateAndPublicKeys, String sub, Map<String, Object> claims, String issuer, Instant expireAt) throws NoSuchAlgorithmException, InvalidKeySpecException {

        Algorithm algorithm = Algorithm.RSA256(getRSAPublicKeyObj(privateAndPublicKeys.get(1))
                , getRSAPrivateKeyObj(privateAndPublicKeys.get(0)));
        JWTCreator.Builder builder = JWT.create();
        if (StringUtils.isNotBlank(sub)) {
            builder.withSubject(sub);
        }
        if (!CollectionUtils.isEmpty(claims)) {
            for (Map.Entry<String, Object> entry : claims.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof Boolean) {
                    builder.withClaim(key, (Boolean) value);
                } else if (value instanceof Integer) {
                    builder.withClaim(key, (Integer) value);
                } else if (value instanceof Long) {
                    builder.withClaim(key, (Long) value);
                } else if (value instanceof Double) {
                    builder.withClaim(key, (Double) value);
                } else if (value instanceof Date) {
                    builder.withClaim(key, (Date) value);
                } else if (value instanceof Instant) {
                    builder.withClaim(key, (Instant) value);
                } else if (value instanceof List) {
                    builder.withClaim(key, (List) value);
                } else if (value instanceof Map) {
                    builder.withClaim(key, (Map) value);
                } else {
                    builder.withClaim(key, value.toString());
                }
            }
        }
        if (StringUtils.isNotBlank(issuer)) {
            builder.withIssuer(issuer);
        }
        if (expireAt != null) {
            builder.withExpiresAt(expireAt);
        }
        String jwtToken = JWT.create().withSubject(sub).withIssuer(issuer).withExpiresAt(expireAt).sign(algorithm);
        return jwtToken;
    }

    public static boolean verifyJwtToken(String jwtToken, String publicKey) {
        if (StringUtils.isBlank(jwtToken) || StringUtils.isBlank(publicKey)) {
            return false;
        }

        try {
            Algorithm algorithm = Algorithm.RSA256(getRSAPublicKeyObj(publicKey), null);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            jwtVerifier.verify(jwtToken);
        } catch (JWTVerificationException e) {
            log.error(e.getMessage(), e);
            return false;
        } catch (InvalidKeySpecException e) {
            log.error(e.getMessage(), e);
            return false;
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;

    }


}
