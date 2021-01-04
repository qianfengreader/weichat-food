package com.qf.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 54110 on 2020/12/24.
 */
@Component
public class JwtUtils {

    // 签发人
    String issuer = "qf-live";

    /**
     * 1. 加密生成token
     * @param map
     * @return
     */
    public String token(Map map){
        //加密算法
        Algorithm algorithmHS = Algorithm.HMAC256("qianfengjava");
        //计算过期时间
        long l = System.currentTimeMillis();

        l+=60*60*1000;

        Date date = new Date(l);
        //自定义头部
        Map headMap = new HashMap<>();
        headMap.put("alg","HS256");
        headMap.put("typ","jwt");

        //签发人
        //头部部分
        String sign = JWT.create().withHeader(headMap)

                //主体 载荷
                .withSubject("token")  //创建的主题
                .withIssuer(issuer)   //创建者

                //自定义的加密信息
                .withClaim("body", map)
                .withIssuedAt(new Date())  //签发的时间
                .withExpiresAt(date)   //过期时间
                .sign(algorithmHS);   //签名

        return sign;
    }


    /**
     * 1.解密token进行验证
     * @param token
     * @return
     */
    public Map Verify(String token){
        Algorithm algorithm = Algorithm.HMAC256("qianfengjava");

        JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();

        // 解析token
        try{
            // 解析token
            DecodedJWT verify = verifier.verify(token);

            //获取你所需要的内容
            Claim body = verify.getClaim("body");

            // 获取到解析完成之后的解密结果
            Map<String, Object> stringObjectMap = body.asMap();
            return stringObjectMap;
        }catch (Exception e){

            return null;
        }


    }
}