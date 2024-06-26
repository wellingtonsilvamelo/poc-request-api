package br.com.tomwell.poc_request_api.service.impl;

import br.com.tomwell.poc_request_api.mapper.CotacaoMapper;
import br.com.tomwell.poc_request_api.model.Cotacao;
import br.com.tomwell.poc_request_api.api.controller.dto.CotacaoResponse;
import br.com.tomwell.poc_request_api.service.CotacaoService;
import br.com.tomwell.poc_request_api.service.client.http.CotacaoProdutoAResponse;
import br.com.tomwell.poc_request_api.service.client.http.ProdutoAClient;
import br.com.tomwell.poc_request_api.service.client.http.ProdutoBClient;
import br.com.tomwell.poc_request_api.service.client.http.ProdutoCClient;
import br.com.tomwell.poc_request_api.service.future.CotacaoProdutoFeature;
import br.com.tomwell.poc_request_api.service.future.CotacaoStrategy;
import br.com.tomwell.poc_request_api.utils.CotacaoFeatureUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class  CotacaoServiceImpl implements CotacaoService {

    private final String topic;
    private final KafkaTemplate<String, Cotacao> kafkaTemplate;
    private final CotacaoStrategy cotacaoStrategy;
    /*private final ProdutoAClient produtoAClient;
    private final ProdutoBClient produtoBClient;
    private final ProdutoCClient produtoCClient;*/

    public CotacaoServiceImpl(String topic, KafkaTemplate<String, Cotacao> kafkaTemplate,
          /*ProdutoAClient produtoAClient, ProdutoBClient produtoBClient, ProdutoCClient produtoCClient,*/
          CotacaoStrategy cotacaoStrategy) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
        /*this.produtoAClient = produtoAClient;
        this.produtoBClient = produtoBClient;
        this.produtoCClient = produtoCClient;*/
        this.cotacaoStrategy = cotacaoStrategy;
    }

    @Override
    public void processarCotacao(Cotacao cotacao) {
        kafkaTemplate.send(this.topic, cotacao);
    }

    @Override
    public Cotacao processarCotacaoSincroca(Cotacao cotacao) {
        Map<Integer, CompletableFuture<?>> futuresMap = new HashMap<>();

        cotacao.getProdutos().forEach(idProduto -> {
            CotacaoProdutoFeature<?> cotacaoProdutoFeature = cotacaoStrategy.getStrategy(idProduto);
            if (cotacaoProdutoFeature != null) {
                futuresMap.put(idProduto, cotacaoProdutoFeature.executar(cotacao));
            }
        });

        /*CompletableFuture<CotacaoProdutoAResponse> futureA = CompletableFuture
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
            });*/

        CompletableFuture<?>[] futuresArray = futuresMap.values().toArray(new CompletableFuture[0]);
        CompletableFuture.allOf(futuresArray).join();
//        CompletableFuture.allOf(futureA, futureB, futureC).join();

        Map<String, Object> resultMap = CotacaoFeatureUtil.processFuturesMap(futuresMap);

        return CotacaoMapper.INSTANCE.toCotacao(resultMap);
    }
}
