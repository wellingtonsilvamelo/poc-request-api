package br.com.tomwell.poc_request_api.api.resource;

import br.com.tomwell.poc_request_api.api.controller.dto.CotacaoRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public interface CotacaoResource {

    ResponseEntity<String> quoteAsync(CotacaoRequest quoteRequest);
    ResponseEntity<String> quoteSync(CotacaoRequest cotacaoRequest);

}
