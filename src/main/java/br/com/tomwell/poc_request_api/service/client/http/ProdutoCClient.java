package br.com.tomwell.poc_request_api.service.client.http;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ProdutoCClient {

    private final RestClient restClient;

    public ProdutoCClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public CotacaoProdutoAResponse obterCotacao() {
        try {
            Thread.sleep(4000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return restClient.get()
            .uri("http://localhost:3000/produtos/3")
            .retrieve()
            .body(CotacaoProdutoAResponse.class);
    }
}
