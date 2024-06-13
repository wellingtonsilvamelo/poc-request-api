package br.com.tomwell.poc_request_api.service;

import br.com.tomwell.poc_request_api.dto.QuoteRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class QuoteKafkaService {

    @Value("${spring.kafka.topic}")
    private String topic;

    private final KafkaTemplate<String, QuoteRequest> kafkaTemplate;

    public QuoteKafkaService(KafkaTemplate<String, QuoteRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Async
    public void processQuote(QuoteRequest quoteRequest) {
        kafkaTemplate.send(this.topic, quoteRequest);
    }
}
