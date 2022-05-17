package com.dabai.proxy.config.token;

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
    private static final String HMAC_KEY = "DaBaiAuthWithJWT";

    public static String generateToken(String sessionKey, String openId) {
        Algorithm algorithm = Algorithm.HMAC256("auth_jwt");
        return JWT.create().withAudience(HMAC_KEY)
                .withClaim("sessionKey", sessionKey)
                .withClaim("openId", openId)
                .sign(algorithm);
    }

    public static String getOpenId(String sign) {
        return JWT.decode(sign).getClaim("openId").asString();
    }

    public static String getSessionKey(String token) {
        return JWT.decode(token).getClaim("sessionKey").asString();
    }

    public static boolean checkToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("auth_jwt");
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            log.info("token check. token:{}, openId:{}", token,jwt.getClaims().get("openId").asString());
            return true;
        } catch (Exception e) {
            log.info("token check failed. token:{}", token);
            return false;
        }

    }
    public static void main(String[] args) {
        String token = "test";
        String sessionKey = "test";

        System.out.println(generateToken(sessionKey, token));
        String key = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJzYXNhc3dlZGFlZmVmZiIsInNlc3Npb25LZXkiOiJmZmZmZnNhY2FzZiJ9.Vut7Tu9u908gVR1nqVc_h1HyCOXqeUJRZJvbNRe2QIE";
        System.out.println(getOpenId(key));
        System.out.println(getSessionKey(key));
        System.out.println(checkToken(key));
        String kss = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJEYUJhaUF1dGhXaXRoSldUIiwic2Vzc2lvbktleSI6ImZmZmZmc2FjYXNmIiwib3BlbklkIjoic2FzYXN3ZWRhZWZlZmYifQ.YjgFTriBWFkd3PjlZCZFIvXJA079mXqZ3kgOd3qzSv4";
        System.out.println(checkToken(kss));

    }
}
