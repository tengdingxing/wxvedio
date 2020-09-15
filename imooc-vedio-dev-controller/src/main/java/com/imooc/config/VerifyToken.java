package com.imooc.config;

import com.imooc.pojo.Audience;
import com.imooc.utils.JwtHelper;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class VerifyToken {

    @Autowired
    private Audience audience;

    public Boolean verifyToken(Map<String,String>map){

        String jwtToken = map.get("jwtToken");
        String userId = map.get("userId");

        System.out.println("jwtToken :"+jwtToken);
        System.out.println("userId :"+userId);
        //校验token
        Claims claims = JwtHelper.parseJWT(jwtToken, this.audience.getBase64Secret());

        String cuserId = claims.get("userId").toString();

        System.out.println("CuserId:"+cuserId);


        //如果claims是空则不存在
        if (claims == null){
            return false;
        }

        //验证用户id
        if (!userId.equals(cuserId)){
            return false;
        }

        /**
         *  Reserved claims（保留），它的含义就像是编程语言的保留字一样，属于JWT标准里面规定的一些claim。JWT标准里面定好的claim有：

         iss(Issuser)：代表这个JWT的签发主体；
         sub(Subject)：代表这个JWT的主体，即它的所有人；
         aud(Audience)：代表这个JWT的接收对象；
         exp(Expiration time)：是一个时间戳，代表这个JWT的过期时间；
         nbf(Not Before)：是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的；
         iat(Issued at)：是一个时间戳，代表这个JWT的签发时间；
         jti(JWT ID)：是JWT的唯一标识。
         */

        //判断是否过期
        long stopTime = claims.getExpiration().getTime();//过期时间
        long startTime = claims.getNotBefore().getTime();//生效时间
       // long issusedatTime = claims.getIssuedAt().getTime();//签发时间；
        long nowTime = new Date().getTime();//当前时间


        System.out.println("startTime:"+startTime);
        System.out.println("stopTime:"+stopTime);
       // System.out.println("issusedatTime:"+issusedatTime);
        System.out.println("nowTime:"+nowTime);

        if(nowTime < startTime || nowTime > stopTime){
            return false;
        }

        return true;

    }

}
