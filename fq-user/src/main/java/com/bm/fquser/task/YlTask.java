package com.bm.fquser.task;

import com.alibaba.fastjson.JSONObject;
import com.bm.fqcore.cache.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;

@Component
@Slf4j
public class YlTask {

    public static final String GOODS_TOKEN = "fq_yl_good_token";

//
//    appId：10037e6f66f2d0f901672aa27d690006
//    appKey：47ace12ae3b348fe93ab46cee97c6fde
//    商户号(mid):898310060514001
//    终端号(tid)：88880001
//    机构商户号(instMid)： H5DEFAULT
//    来源编号（msgSrcId）：1017
//    测试md5密钥：impARTxrQcfwmRijpDNCw6hPxaWCddKEpYxjaKXDhCaTCXJ6
//

    private static String APPID = "10037e6f66f2d0f901672aa27d690006";
    private static String APPKEY = "47ace12ae3b348fe93ab46cee97c6fde";

    private static String API_URL = "https://test-api-open.chinaums.com/v1";


    @Value("${spring.profiles.active}")
    private String springProfilesActive;

    @Scheduled(cron = "0 */30 * * * ?")//每五分 测试用`
    @PostConstruct
    public void run() {
        log.info("银联订货获取token任务开始--------------------------------------------------------------------");
        if ("dev".equals(springProfilesActive)) {
            JSONObject jsonObject = new JSONObject();
            String Timestamp = gettime();
            log.info("Timestamp为---------------{}", Timestamp);
            String Nonce = getnonce();
            log.info("Nonce为---------------{}", Nonce);
            String appkey = APPKEY;
            String all = APPID + Timestamp + Nonce + appkey;
            String signature = ccsha1(all);
            log.info("签名为---------------{}", signature);
            jsonObject.put("appId", APPID);
            jsonObject.put("timestamp", Timestamp);
            jsonObject.put("nonce", Nonce);
            jsonObject.put("signature", signature);
            log.info("jsonObject-------------------{}", jsonObject);
            JSONObject response = doPost(API_URL, jsonObject);
            log.info("response---------------{}", response);
            String accessToken = response.get("accessToken").toString();
            log.info("accessToken---------------{}", accessToken);
//            RedisUtil.set(GOODS_TOKEN, accessToken);
        } else {
            JSONObject jsonObject = new JSONObject();
            String Timestamp = gettime();
            log.info("Timestamp为---------------{}", Timestamp);
            String Nonce = getnonce();
            log.info("Nonce为---------------{}", Nonce);
            String appkey = APPKEY;
            String all = APPID + Timestamp + Nonce + appkey;
            String signature = ccsha1(all);
            log.info("签名为---------------{}", signature);
            jsonObject.put("appId", APPID);
            jsonObject.put("timestamp", Timestamp);
            jsonObject.put("nonce", Nonce);
            jsonObject.put("signature", signature);
            log.info("jsonObject-------------------{}", jsonObject);
            JSONObject response = doPost(API_URL, jsonObject);
            log.info("response---------------{}", response);
            String accessToken = response.get("accessToken").toString();
            log.info("accessToken---------------{}", accessToken);
//            RedisUtil.set(GOODS_TOKEN, accessToken);
        }
        log.info("银联订货获取token任任务结束----------------------------------------------------------------");
    }

    /*获取系统当前时间*/
    public static String gettime() {
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = date.format(System.currentTimeMillis());
        return time;
    }

    /*获取随机数*/
    public static String getnonce() {
        int a = (int) (1 + Math.random() * 10000);
        String nonce = String.valueOf(a);
        return nonce;
    }

    public static String ccsha1(String message) {/*加密算法实现*/
        String c = DigestUtils.sha1Hex(message);
        return c;
    }

    /*发送post请求得到返回的数据*/
    public static JSONObject doPost(String url, JSONObject json) {

        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url + "/token/access");
        JSONObject response = null;
        try {
            StringEntity s = new StringEntity(json.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(s);
            CloseableHttpResponse res = httpclient.execute(post);
            HttpEntity entity = res.getEntity();
            String result = EntityUtils.toString(entity);// 返回json格式：
            response = JSONObject.parseObject(result);
            res.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
