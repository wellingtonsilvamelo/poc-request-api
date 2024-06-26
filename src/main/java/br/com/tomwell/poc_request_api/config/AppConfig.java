package br.com.tomwell.poc_request_api.config;

import br.com.tomwell.poc_request_api.model.Cotacao;
import br.com.tomwell.poc_request_api.service.CotacaoService;
import br.com.tomwell.poc_request_api.service.client.http.ProdutoAClient;
import br.com.tomwell.poc_request_api.service.client.http.ProdutoBClient;
import br.com.tomwell.poc_request_api.service.client.http.ProdutoCClient;
import br.com.tomwell.poc_request_api.service.future.CotacaoStrategy;
import br.com.tomwell.poc_request_api.service.impl.CotacaoServiceImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@EnableConfigurationProperties({KafkaTopicsProperties.class})
public class AppConfig {

    private final KafkaTopicsProperties kafkaTopicsProperties;

    public AppConfig(KafkaTopicsProperties kafkaTopicsProperties) {
        this.kafkaTopicsProperties = kafkaTopicsProperties;
    }

    @Bean
    public CotacaoService cotacaoService(KafkaTemplate<String, Cotacao> kafkaTemplate,
            /*ProdutoAClient produtoAClient, ProdutoBClient produtoBClient, ProdutoCClient produtoCClient,*/ CotacaoStrategy cotacaoStrategy) {
        return new CotacaoServiceImpl(kafkaTopicsProperties.getPocQuoteRequest(), kafkaTemplate,
            /*produtoAClient, produtoBClient, produtoCClient,*/ cotacaoStrategy);
    }
}
