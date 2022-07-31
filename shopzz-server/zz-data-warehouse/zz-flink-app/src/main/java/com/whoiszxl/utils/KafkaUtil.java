package com.whoiszxl.utils;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.streaming.connectors.kafka.KafkaSerializationSchema;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;

/**
 * kafka工具类
 */
public class KafkaUtil {

    /**
     * 默认Kafka broker集群地址，多地址使用(,)分隔
     */
    private static final String DEFAULT_KAFKA_BROKERS = "120.24.51.152:9092,120.76.99.12:9092,120.25.230.239:9092";

    private static String default_topic = "DWD_DEFAULT_TOPIC";

    /**
     * 获取Flink的kafka生产者
     * @param topic
     * @return
     */
    public static FlinkKafkaProducer<String> getKafkaProducer(String topic) {
        return new FlinkKafkaProducer<>(DEFAULT_KAFKA_BROKERS, topic, new SimpleStringSchema());
    }

    public static FlinkKafkaProducer<String> getKafkaProducer(String topic, String kafkaBrokers) {
        return new FlinkKafkaProducer<>(kafkaBrokers, topic, new SimpleStringSchema());
    }

    public static <T> FlinkKafkaProducer<T> getKafkaProducer(KafkaSerializationSchema<T> kafkaSerializationSchema) {

        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, DEFAULT_KAFKA_BROKERS);

        return new FlinkKafkaProducer<>(
                default_topic,
                kafkaSerializationSchema,
                properties,
                FlinkKafkaProducer.Semantic.EXACTLY_ONCE);
    }

    public static FlinkKafkaConsumer<String> getKafkaConsumer(String topic, String groupId) {

        Properties properties = new Properties();

        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, DEFAULT_KAFKA_BROKERS);

        return new FlinkKafkaConsumer<>(topic,
                new SimpleStringSchema(),
                properties);

    }

    public static String getKafkaDDL(String topic, String groupId) {
        return  " 'connector' = 'kafka', " +
                " 'topic' = '" + topic + "'," +
                " 'properties.bootstrap.servers' = '" + DEFAULT_KAFKA_BROKERS + "', " +
                " 'properties.group.id' = '" + groupId + "', " +
                " 'format' = 'json', " +
                " 'scan.startup.mode' = 'latest-offset'  ";
    }



}
