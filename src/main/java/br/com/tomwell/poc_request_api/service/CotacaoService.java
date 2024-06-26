package br.com.tomwell.poc_request_api.service;

import br.com.tomwell.poc_request_api.model.Cotacao;
import br.com.tomwell.poc_request_api.api.controller.dto.CotacaoResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public interface CotacaoService {

    @Async
    void processarCotacao(Cotacao cotacao);

    Cotacao processarCotacaoSincroca(Cotacao cotacao);
}
