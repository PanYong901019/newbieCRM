package com.newbie.service.Impl;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.newbie.service.BaseService;
import com.newbie.service.CommonService;
import org.springframework.stereotype.Service;

@Service(value = "commonService")
public class CommonServiceImpl extends BaseService implements CommonService {

    @Override
    public Integer sendSms(String phone) {
        int i = (int) ((Math.random() * 9 + 1) * 100000);

        try {
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");
            final String product = "Dysmsapi";
            final String domain = "dysmsapi.aliyuncs.com";
            final String accessKeyId = "LTAIVD886TKXFoI2";
            final String accessKeySecret = "kmYUlvKSH6hPS34Peib4YYb76EKWau";
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);
            SendSmsRequest request = new SendSmsRequest();
            request.setMethod(MethodType.POST);
            request.setPhoneNumbers(phone);
            request.setSignName("菜鸟在线");
            request.setTemplateCode("SMS_135295187");
            request.setTemplateParam("{ \"code\":\"" + i + "\"}");
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                baseDaoImpl.executeInsertSqlQuery("INSERT INTO cainiao_sms ( phone, `code`, send_time ) VALUES ( " + phone + "," + i + "," + String.valueOf(System.currentTimeMillis()).substring(0, 10) + ")");
            }
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public Boolean verifyCode(String phone, String code) {
        return (Long) baseDaoImpl.executeSelectSqlQuery("SELECT count( * ) as rowCount FROM cainiao_sms WHERE phone = " + phone + "  AND `code` = " + code).get(0).get("rowCount") > 0;
    }
}
