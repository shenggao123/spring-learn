package com.lebron.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.record.Record;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class Producer extends Thread{
    KafkaProducer<Integer, String> kafkaProducer;
    String topic;
    public Producer(String topic){
        this.topic = topic;
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.1.3:9092,192.168.1.3:9093,192.168.1.3:9094");
        properties.setProperty(ProducerConfig.CLIENT_ID_CONFIG, "sg-consumer1");
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, "100");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.RETRIES_CONFIG, "10");
        properties.setProperty(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.lebron.kafka.CustomPartitioner");
        properties.setProperty(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, "com.lebron.kafka.Custom2ProducerInterceptor,com.lebron.kafka.CustomProducerInterceptor");
        kafkaProducer = new KafkaProducer<>(properties);
    }

    @Override
    public void run() {
        for (int i = 0; ; i++) {
            ProducerRecord<Integer, String> record = new ProducerRecord<>(topic, "sg-msg"+ i);
            record.headers().add("sdasd", "asdasd".getBytes());
            kafkaProducer.send(record, (metadata, exception) -> System.out.println(metadata));
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Producer("test_sg").run();
    }
}
