package com.lebron.kafka;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

public class CustomProducerInterceptor implements ProducerInterceptor<Integer, String> {

    @Override
    public ProducerRecord<Integer, String> onSend(ProducerRecord<Integer, String> record) {
        String val = "sss-" + record.value();
        return new ProducerRecord<>(
                record.topic(), record.partition(),
                record.timestamp(), record.key(),
                val, record.headers());
    }

    int success;
    int failure;

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        if (metadata == null) {
            failure++;
        }
        if (exception == null) {
            success++;
        }
    }

    @Override
    public void close() {
        System.out.println("interceptor关闭");
    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
