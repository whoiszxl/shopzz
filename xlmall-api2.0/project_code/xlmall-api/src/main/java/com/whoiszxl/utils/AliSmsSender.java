package com.whoiszxl.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.google.common.collect.Maps;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class AliSmsSender {
	//产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = PropertiesUtil.getProperty("alisms.accessKeyId");
    static final String accessKeySecret = PropertiesUtil.getProperty("alisms.accessKeySecret");

    public static SendSmsResponse sendSms(String phoneNum, String signName, String templateCode,HashMap<String, String> templateParams) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phoneNum);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        System.out.println(JsonUtil.obj2String(templateParams));
        request.setTemplateParam(JsonUtil.obj2String(templateParams));

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
    }


    public static QuerySendDetailsResponse querySendDetails(String bizId) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber("15000000000");
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

        return querySendDetailsResponse;
    }

    
    public static SendSmsResponse sendVerifyCode(String phoneNum, String code) throws ClientException {
    	HashMap<String, String> templateParams = new HashMap<>();
    	templateParams.put("code", code);
    	return sendSms(
    			phoneNum,
    			PropertiesUtil.getProperty("alisms.verifyCode.signName"), 
    			PropertiesUtil.getProperty("alisms.verifyCode.templateCode"), 
    			templateParams);
    }
    
    public static SendSmsResponse sendVerifyCode(String phoneNum, int verifyCodeLength) throws ClientException {
    	HashMap<String, String> templateParams = new HashMap<>();
    	templateParams.put("code", getRandomNumCode(verifyCodeLength));
    	return sendSms(
    			phoneNum,
    			PropertiesUtil.getProperty("alisms.verifyCode.signName"), 
    			PropertiesUtil.getProperty("alisms.verifyCode.templateCode"), 
    			templateParams);
    }
    
    public static SendSmsResponse sendNoTemplateSMS(String phoneNum) throws ClientException {
    	HashMap<String, String> templateParams = new HashMap<>();
    	return sendSms(
    			phoneNum,
    			PropertiesUtil.getProperty("alisms.verifyCode.signName"), 
    			PropertiesUtil.getProperty("alisms.verifyCode.templateCode"), 
    			templateParams);
    }
    
    /**
     * 生成随机验证码.
     *
     * @return 随机数
     */
    public static String getRandomNumCode(int number){
    	String codeNum = "";
    		int [] numbers = {0,1,2,3,4,5,6,7,8,9};
    		Random random = new Random();
    		for (int i = 0; i < number; i++) {
    			int next = random.nextInt(10000);//目的是产生足够随机的数，避免产生的数字重复率高的问题
//    			System.out.println(next);
				codeNum+=numbers[next%10];
			}
    		System.out.println("--------");
    	System.out.println(codeNum);
    	
    	return codeNum; 
    }

}
