package com.lebron.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Iterator;
import java.util.Properties;

public class Consumer3 extends Thread{
    KafkaConsumer<Integer, String> kafkaConsumer;
    String topic;
    public Consumer3(String topic){
        this.topic = topic;
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.1.3:9091");
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, "sg-consumer1");
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "sg-gid");
        properties.setProperty(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        properties.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        kafkaConsumer = new KafkaConsumer<>(properties);
    }

    @Override
    public void run() {
        kafkaConsumer.subscribe(Collections.singleton(topic));
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ConsumerRecords<Integer, String> records = kafkaConsumer.poll(Duration.ofSeconds(1));
            Iterator<ConsumerRecord<Integer, String>> iterator =  records.iterator();
            while (iterator.hasNext()) {
                ConsumerRecord<Integer, String> record = iterator.next();
                System.out.println(record);
            }
        }
    }

    public static void main(String[] args) {
        new Consumer3("test_sg").run();
    }
}
