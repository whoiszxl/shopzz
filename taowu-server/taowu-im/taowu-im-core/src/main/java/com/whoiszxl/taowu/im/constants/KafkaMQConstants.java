package com.whoiszxl.taowu.im.constants;

/**
 * Kafka MQ 常量
 * @author whoiszxl
 */
public class KafkaMQConstants {

    private static final String NODE_ID = "node_001";

    public static final String IM_NETTY_TO_MEMBER_TOPIC = "im_netty_to_member_topic_" + NODE_ID;
    public static final String IM_NETTY_TO_MESSAGE_TOPIC = "im_netty_to_message_topic" + NODE_ID;
    public static final String IM_NETTY_TO_GROUP_TOPIC = "im_netty_to_group_topic_" + NODE_ID;
    public static final String IM_NETTY_TO_GPT_TOPIC = "im_netty_to_gpt_topic" + NODE_ID;


    public static final String IM_MEMBER_TO_NETTY_TOPIC = "im_member_to_netty_topic_" + NODE_ID;
    public static final String IM_MESSAGE_TO_NETTY_TOPIC = "im_message_to_netty_topic_" + NODE_ID;
    public static final String IM_GROUP_TO_NETTY_TOPIC = "im_group_to_netty_topic_" + NODE_ID;
}
