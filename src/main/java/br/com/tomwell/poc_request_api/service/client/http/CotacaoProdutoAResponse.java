package br.com.tomwell.poc_request_api.service.client.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CotacaoProdutoAResponse implements Serializable {

    private Long id;
    private String sigla;
    private String descricao;
    private Double valor;
    private OffsetDateTime dataInicioVigencia;
    private OffsetDateTime dataFimVigencia;

}
