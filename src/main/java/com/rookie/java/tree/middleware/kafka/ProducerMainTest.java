package com.rookie.java.tree.middleware.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @Author rookie
 * @Date 2024-11-02 6:02
 * @Description
 **/
public class ProducerMainTest {

    public static final String broker = "43.142.3.88:9092";
    public static final String topic = "rookie-topic";

    public static Properties initConfig(){
        Properties properties = new Properties();
        properties.put("bootstrap.servers", broker);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("client.id", "producer.client.id.demo");
        return properties;
    }

    public static void main(String[] args) {
        Properties properties = initConfig();
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, "hello, Kafka!");

        try {
            producer.send(record);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
