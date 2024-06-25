package br.com.tomwell.poc_request_api.service.impl;

import br.com.tomwell.poc_request_api.api.controller.dto.CotacaoRequest;
import br.com.tomwell.poc_request_api.service.CotacaoService;
import br.com.tomwell.poc_request_api.service.client.http.CotacaoProdutoAResponse;
import br.com.tomwell.poc_request_api.service.client.http.ProdutoAClient;
import br.com.tomwell.poc_request_api.service.client.http.ProdutoBClient;
import br.com.tomwell.poc_request_api.service.client.http.ProdutoCClient;
import ch.qos.logback.core.net.SyslogOutputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class  CotacaoServiceImpl implements CotacaoService {

    private final String topic;
    private final KafkaTemplate<String, CotacaoRequest> kafkaTemplate;
    private final ProdutoAClient produtoAClient;
    private final ProdutoBClient produtoBClient;
    private final ProdutoCClient produtoCClient;

    public CotacaoServiceImpl(String topic, KafkaTemplate<String, CotacaoRequest> kafkaTemplate,
          ProdutoAClient produtoAClient, ProdutoBClient produtoBClient, ProdutoCClient produtoCClient) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
        this.produtoAClient = produtoAClient;
        this.produtoBClient = produtoBClient;
        this.produtoCClient = produtoCClient;
    }

    @Override
    public void processarCotacao(CotacaoRequest cotacaoRequest) {
        kafkaTemplate.send(this.topic, cotacaoRequest);
    }

    @Override
    public CotacaoProdutoAResponse processarCotacaoSincroca(CotacaoRequest cotacaoRequest) {

        CompletableFuture<CotacaoProdutoAResponse> futureA = CompletableFuture
            .supplyAsync(produtoAClient::obterCotacao)
            .exceptionally(ex -> {
                log.error("Erro ao obter cotação do ProdutoA: {}", ex.getMessage());
                return new CotacaoProdutoAResponse();
            });

        CompletableFuture<CotacaoProdutoAResponse> futureB = CompletableFuture
            .supplyAsync(produtoBClient::obterCotacao)
            .exceptionally(ex -> {
                log.error("Erro ao obter cotação do ProdutoB: {}", ex.getMessage());
                return new CotacaoProdutoAResponse();
            });

        CompletableFuture<CotacaoProdutoAResponse> futureC = CompletableFuture
            .supplyAsync(produtoCClient::obterCotacao)
            .exceptionally(ex -> {
                log.error("Erro ao obter cotação do ProdutoC: {}", ex.getMessage());
                return new CotacaoProdutoAResponse();
            });

        CotacaoProdutoAResponse response = new CotacaoProdutoAResponse();

        CompletableFuture.allOf(futureA, futureB, futureC).join();

        response.setId(futureA.join().getId());
        response.setDescricao(futureB.join().getDescricao());
        response.setValor(futureC.join().getValor());

        return response;
    }
}
