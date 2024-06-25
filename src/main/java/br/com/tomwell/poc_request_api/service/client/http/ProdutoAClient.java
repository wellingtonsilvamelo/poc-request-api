package br.com.tomwell.poc_request_api.service.client.http;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ProdutoAClient {

    private final RestClient restClient;

    public ProdutoAClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public CotacaoProdutoAResponse obterCotacao() {
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return restClient.get()
            .uri("http://localhost:3000/produtos/1")
            .retrieve()
            .body(CotacaoProdutoAResponse.class);
    }
}
