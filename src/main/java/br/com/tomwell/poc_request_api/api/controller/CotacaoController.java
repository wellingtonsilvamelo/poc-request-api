package br.com.tomwell.poc_request_api.api.controller;

import br.com.tomwell.poc_request_api.api.controller.dto.CotacaoRequest;
import br.com.tomwell.poc_request_api.service.CotacaoService;
import br.com.tomwell.poc_request_api.service.client.http.CotacaoProdutoAResponse;
import br.com.tomwell.poc_request_api.service.impl.CotacaoServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/quotes")
public class CotacaoController {

    private final CotacaoService cotacaoService;

    public CotacaoController(CotacaoService cotacaoService) {
        this.cotacaoService = cotacaoService;
    }

    @PostMapping("/async")
    public ResponseEntity<String> quoteAsync(@RequestBody CotacaoRequest quoteRequest) {
        cotacaoService.processarCotacao(quoteRequest);
        return ResponseEntity.accepted().body("Solicitação recebida com sucesso");
    }

    @PostMapping("sync")
    public ResponseEntity<CotacaoProdutoAResponse> quoteSync(@RequestBody CotacaoRequest cotacaoRequest) {
        var cotacaoProdutoAResponse = cotacaoService.processarCotacaoSincroca(cotacaoRequest);
        return ResponseEntity.accepted().body(cotacaoProdutoAResponse);
    }

}
