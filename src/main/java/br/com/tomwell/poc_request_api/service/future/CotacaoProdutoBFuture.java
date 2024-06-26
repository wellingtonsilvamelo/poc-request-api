package br.com.tomwell.poc_request_api.service.future;

import br.com.tomwell.poc_request_api.enums.ProdutoEnum;
import br.com.tomwell.poc_request_api.model.Cotacao;
import br.com.tomwell.poc_request_api.service.client.http.CotacaoProdutoAResponse;
import br.com.tomwell.poc_request_api.service.client.http.ProdutoBClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class CotacaoProdutoBFuture implements CotacaoProdutoFeature<CotacaoProdutoAResponse> {

    private final ProdutoBClient produtoBClient;

    public CotacaoProdutoBFuture(ProdutoBClient produtoBClient) {
        this.produtoBClient = produtoBClient;
    }

    @Override
    public CompletableFuture<CotacaoProdutoAResponse> executar(Cotacao cotacao) {
        return CompletableFuture
            .supplyAsync(produtoBClient::obterCotacao)
            .exceptionally(ex -> {
                log.error("Erro ao obter cotação do ProdutoB: {}", ex.getMessage());
                return new CotacaoProdutoAResponse();
            });

    }

    @Override
    public Integer getIdProduto() {
        return ProdutoEnum.RESIDE.getIdProduto();
    }
}
