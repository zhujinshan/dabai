package com.dabai.proxy.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/9 22:11
 */
@Slf4j
public class JwtTools {
    private static final String HMAC_KEY = "DaBaiAdminWithJWT";

    private static final String ADMIN_JWT = "admin_jwt";

    public static String generateToken(Long userId) {
        Algorithm algorithm = Algorithm.HMAC256(ADMIN_JWT);
        return JWT.create().withAudience(HMAC_KEY)
                .withClaim("userId", userId)
                .sign(algorithm);
    }

    public static String getUserId(String sign) {
        return JWT.decode(sign).getClaim("userId").asString();
    }

    public static boolean checkToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(ADMIN_JWT);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            log.info("token check. token:{}, userId:{}", token,jwt.getClaims().get("userId").asString());
            return true;
        } catch (Exception e) {
            log.info("token check failed. token:{}", token);
            return false;
        }

    }
}
