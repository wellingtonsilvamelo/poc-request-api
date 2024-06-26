package br.com.tomwell.poc_request_api.service.client.http;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ProdutoBClient {

    private final RestClient restClient;

    public ProdutoBClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public CotacaoProdutoAResponse obterCotacao() {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//        throw new IllegalArgumentException("Erro inesperado!");

        return restClient.get()
            .uri("http://localhost:3000/produtos/2")
            .retrieve()
            .body(CotacaoProdutoAResponse.class);
    }
}
