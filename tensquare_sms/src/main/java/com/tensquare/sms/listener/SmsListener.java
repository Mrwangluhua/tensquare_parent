package com.tensquare.sms.listener;

import com.aliyuncs.exceptions.ClientException;
import com.tensquare.sms.util.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "sms")
public class SmsListener {
    @Autowired
    private SmsUtil smsUtil;
    @Value("${aliyun.sms.template_code}")
    private String template_code;
    @Value("${aliyun.sms.sign_name}")
    private String sign_name;


    @RabbitHandler
    public void getMessage(Map<String,Object> map)  {

        String mobile = map.get("mobile").toString();
        String code = map.get("code").toString();
        System.out.println("电话号码："+mobile);
        System.out.println("验证码："+code);

        try {
            smsUtil.sendSms(mobile,template_code,sign_name,"{\"code\":\""+code+"\"}");
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }
}
