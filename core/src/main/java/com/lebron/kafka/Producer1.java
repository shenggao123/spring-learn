package com.lebron.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Producer1 extends Thread{
    KafkaProducer<Integer, String> kafkaProducer;
    String topic;
    public Producer1(String topic){
        this.topic = topic;
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.1.3:9092,192.168.1.3:9093,192.168.1.3:9094");
        properties.setProperty(ProducerConfig.CLIENT_ID_CONFIG, "sg-consumer1");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        kafkaProducer = new KafkaProducer<>(properties);
    }

    @Override
    public void run() {
        for (int i = 0; ; i++) {
            ProducerRecord<Integer, String> record = new ProducerRecord<>(topic, "sg-msg"+ i);
            kafkaProducer.send(record, (metadata, exception) -> {
//                System.out.println(metadata);
            });
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Producer1("test_sg").run();
    }
}
