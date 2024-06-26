package br.com.tomwell.poc_request_api.service.future;

import br.com.tomwell.poc_request_api.model.Cotacao;

import java.util.concurrent.CompletableFuture;

public interface CotacaoProdutoFeature<T> {
    CompletableFuture<T> executar(Cotacao cotacao);
    Integer getIdProduto();
}
