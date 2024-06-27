package br.com.tomwell.poc_request_api.service.future;

import br.com.tomwell.poc_request_api.enums.ProdutoEnum;
import br.com.tomwell.poc_request_api.model.Cotacao;
import br.com.tomwell.poc_request_api.service.client.http.CotacaoProdutoAResponse;
import br.com.tomwell.poc_request_api.service.client.http.ProdutoAClient;
import br.com.tomwell.poc_request_api.service.client.http.ProdutoCClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class CotacaoProdutoCFuture implements CotacaoProdutoFeature<CotacaoProdutoAResponse> {

    private final ProdutoCClient produtoCClient;

    public CotacaoProdutoCFuture(ProdutoCClient produtoCClient) {
        this.produtoCClient = produtoCClient;
    }

    @Override
    public CompletableFuture<CotacaoProdutoAResponse> executar(Cotacao cotacao) {
        return CompletableFuture
            .supplyAsync(produtoCClient::obterCotacao)
            .exceptionally(ex -> {
                log.error("Erro ao obter cotação do ProdutoC: {}", ex.getMessage());
                return null;
            });

    }

    @Override
    public Integer getIdProduto() {
        return ProdutoEnum.VIDA.getIdProduto();
    }
}
