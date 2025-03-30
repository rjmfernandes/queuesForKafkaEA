package io.confluent.csta;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class KafkaShareConsumerApp {
    private static final String TOPIC = "quickstart-events";
    private static final String GROUP_ID = "shared-consumer-group";

    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("group.id", GROUP_ID);

        try (KafkaShareConsumer<String, String> consumer = new KafkaShareConsumer<>(
            props, new StringDeserializer(), new StringDeserializer())) {
            consumer.subscribe(Arrays.asList(TOPIC));

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(10000));
                System.out.println("Polled records after 10s: " + records.count());
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("Consumed message: value=%s",record.value());
                }
            }
        }
    }
}

