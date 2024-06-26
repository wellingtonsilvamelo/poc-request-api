package br.com.tomwell.poc_request_api.service.future;

import br.com.tomwell.poc_request_api.enums.ProdutoEnum;
import br.com.tomwell.poc_request_api.model.Cotacao;
import br.com.tomwell.poc_request_api.service.client.http.CotacaoProdutoAResponse;
import br.com.tomwell.poc_request_api.service.client.http.ProdutoAClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class CotacaoProdutoAFuture implements CotacaoProdutoFeature<CotacaoProdutoAResponse> {

    private final ProdutoAClient produtoAClient;

    public CotacaoProdutoAFuture(ProdutoAClient produtoAClient) {
        this.produtoAClient = produtoAClient;
    }

    @Override
    public CompletableFuture<CotacaoProdutoAResponse> executar(Cotacao cotacao) {
        return CompletableFuture
            .supplyAsync(produtoAClient::obterCotacao)
            .exceptionally(ex -> {
                log.error("Erro ao obter cotação do ProdutoA: {}", ex.getMessage());
                return new CotacaoProdutoAResponse();
            });

    }

    @Override
    public Integer getIdProduto() {
        return ProdutoEnum.AUTO.getIdProduto();
    }
}
