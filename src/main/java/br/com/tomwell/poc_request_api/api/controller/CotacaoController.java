package br.com.tomwell.poc_request_api.api.controller;

import br.com.tomwell.poc_request_api.api.controller.dto.CotacaoRequest;
import br.com.tomwell.poc_request_api.api.controller.dto.CotacaoResponse;
import br.com.tomwell.poc_request_api.mapper.CotacaoMapper;
import br.com.tomwell.poc_request_api.service.CotacaoService;
import br.com.tomwell.poc_request_api.service.client.http.CotacaoProdutoAResponse;
import br.com.tomwell.poc_request_api.service.impl.CotacaoServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/quotes")
public class CotacaoController {

    private final CotacaoService cotacaoService;

    public CotacaoController(CotacaoService cotacaoService) {
        this.cotacaoService = cotacaoService;
    }

    @PostMapping("/async")
    public ResponseEntity<String> quoteAsync(@RequestBody CotacaoRequest quoteRequest) {
        cotacaoService.processarCotacao(CotacaoMapper.INSTANCE.toCotacao(quoteRequest));
        return ResponseEntity.accepted().body("Solicitação recebida com sucesso");
    }

    @PostMapping("sync")
    public ResponseEntity<List<CotacaoResponse>> quoteSync(@RequestBody CotacaoRequest cotacaoRequest) {
        var cotacao = cotacaoService.processarCotacaoSincroca(CotacaoMapper.INSTANCE.toCotacao(cotacaoRequest));
        return ResponseEntity.ok().body(
            CotacaoMapper.INSTANCE.toCotacaoResponse(cotacao));
    }

}
