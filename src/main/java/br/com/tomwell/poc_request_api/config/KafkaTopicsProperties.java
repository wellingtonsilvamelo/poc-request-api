package br.com.tomwell.poc_request_api.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.kafka.topics")
public class KafkaTopicsProperties {

    private String pocQuoteRequest;
    private String pocQuoteCalc;

}
