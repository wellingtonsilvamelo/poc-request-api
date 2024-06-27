package br.com.tomwell.poc_request_api.service.impl;

import br.com.tomwell.poc_request_api.api.controller.dto.CotacaoRequest;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
    public List<Cotacao> processarCotacaoSincroca(Cotacao cotacao) {
        Map<Integer, CompletableFuture<?>> futuresMap = new HashMap<>();
        List<Cotacao> cotacoes = new ArrayList<>();

        cotacao.getProdutos().forEach(idProduto -> {
            CotacaoProdutoFeature<?> cotacaoProdutoFeature = cotacaoStrategy.getStrategy(idProduto);
            if (cotacaoProdutoFeature != null) {
                futuresMap.put(idProduto, cotacaoProdutoFeature.executar(cotacao));
            }
        });

        CompletableFuture<?>[] futuresArray = futuresMap.values().toArray(new CompletableFuture[0]);
        CompletableFuture.allOf(futuresArray).join();

        futuresMap.forEach((key, future) -> {
            try {
                cotacoes.add(CotacaoMapper.INSTANCE.toCotacao((CotacaoProdutoAResponse) future.get()));
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        return cotacoes;
    }
}
