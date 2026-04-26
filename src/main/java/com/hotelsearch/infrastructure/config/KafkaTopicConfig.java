package com.hotelsearch.infrastructure.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    
    @Bean
    public NewTopic hotelSearchTopic() {
        return TopicBuilder.name("hotel_availability_searches")
                .partitions(3)
                .replicas(1)
                .config("retention.ms", "86400000")
                .build();
    }
}
