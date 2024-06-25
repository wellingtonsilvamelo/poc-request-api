package br.com.tomwell.poc_request_api.service;

import br.com.tomwell.poc_request_api.api.controller.dto.CotacaoRequest;
import br.com.tomwell.poc_request_api.service.client.http.CotacaoProdutoAResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public interface CotacaoService {

    @Async
    void processarCotacao(CotacaoRequest cotacaoRequest);

    CotacaoProdutoAResponse processarCotacaoSincroca(CotacaoRequest cotacaoRequest);
}
