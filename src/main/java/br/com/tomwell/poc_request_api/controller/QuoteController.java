package br.com.tomwell.poc_request_api.controller;

import br.com.tomwell.poc_request_api.dto.QuoteRequest;
import br.com.tomwell.poc_request_api.service.QuoteKafkaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quotes")
public class QuoteController {

    private final QuoteKafkaService quoteKafkaService;

    public QuoteController(QuoteKafkaService quoteKafkaService) {
        this.quoteKafkaService = quoteKafkaService;
    }

    @PostMapping
    public ResponseEntity<String> quoteSync(@RequestBody QuoteRequest quoteRequest) {
        quoteKafkaService.processQuote(quoteRequest);
        return ResponseEntity.accepted().body("Solicitação recebida com sucesso");
    }
}
