package com.bm.fqcore.utils;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.aliyun.teaopenapi.models.Config;

public class AlySmsUtil {



    private static String accessKeyId = "LTAI5tRRXYwekCMadzQ1Qb7Y";
    private static String accessKeySecret = "GHcEkRuTGGdoc5Y4ZMyqC1ZMdjJref";


    public static com.aliyun.dysmsapi20170525.Client createClient() throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.dysmsapi20170525.Client(config);
    }

    public static SendSmsResponseBody sms(String phone, String code) throws Exception {
        com.aliyun.dysmsapi20170525.Client client = AlySmsUtil.createClient();
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        sendSmsRequest.setPhoneNumbers(phone);
        sendSmsRequest.setSignName("班狗科技");
        sendSmsRequest.setTemplateCode("SMS_234225449");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        sendSmsRequest.setTemplateParam(jsonObject.toJSONString());
        // 复制代码运行请自行打印 API 的返回值
        SendSmsResponse sendSmsResponse = client.sendSms(sendSmsRequest);
        return sendSmsResponse.getBody();
    }
    public static void main(String[] args_) throws Exception {
        sms("13093783517", "98768");
    }
}