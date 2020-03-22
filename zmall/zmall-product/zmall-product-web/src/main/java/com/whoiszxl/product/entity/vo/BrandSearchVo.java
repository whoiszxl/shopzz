package com.whoiszxl.product.entity.vo;

import com.aliyun.mq.http.MQClient;
import com.aliyun.mq.http.MQProducer;
import com.aliyun.mq.http.model.TopicMessage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 品牌搜索Vo
 * @author: whoiszxl
 * @create: 2020-03-20
 **/
@Data
@ApiModel(value="BrandSearchVo", description="品牌搜索Vo")
public class BrandSearchVo implements Serializable {

    @ApiModelProperty(value = "品牌名称")
    private String name;

    @ApiModelProperty(value = "品牌首字母")
    private String letter;

    @ApiModelProperty(value = "页码")
    private Long page;

    @ApiModelProperty(value = "页数")
    private Long size;


    public static void main(String[] args) {
        MQClient mqClient = new MQClient(
                // 设置HTTP接入域名（此处以公共云生产环境为例）
                "http://1726933647430481.mqrest.cn-hangzhou.aliyuncs.com",
                // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
                "LTAI4FjvPLTd6FSVk71vuerE",
                // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
                "0cR5omZgZV6YZZTOksRbQHZdzsj1WF"
        );

        // 所属的 Topic
        final String topic = "search-topic";
        // Topic所属实例ID，默认实例为空
        final String instanceId = "MQ_INST_1726933647430481_BcNn3BiA";

        // 获取Topic的生产者
        MQProducer producer;
        if (instanceId != null && instanceId != "") {
            producer = mqClient.getProducer(instanceId, topic);
        } else {
            producer = mqClient.getProducer(topic);
        }

        try {
            // 普通消息
            TopicMessage pubMsg = new TopicMessage(
                    // 消息内容
                    "hello mdsadsadasdsdsa!".getBytes(),
                    // 消息标签
                    "A"
            );
            // 设置KEY
            pubMsg.setMessageKey("MessageKey");
            // 同步发送消息，只要不抛异常就是成功
            TopicMessage pubResultMsg = producer.publishMessage(pubMsg);

            // 同步发送消息，只要不抛异常就是成功
            System.out.println(new Date() + " Send mq message success. Topic is:" + topic + ", msgId is: " + pubResultMsg.getMessageId()
                    + ", bodyMD5 is: " + pubResultMsg.getMessageBodyMD5());
        } catch (Throwable e) {
            // 消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理
            System.out.println(new Date() + " Send mq message failed. Topic is:" + topic);
            e.printStackTrace();
        }

        mqClient.close();
    }
}
