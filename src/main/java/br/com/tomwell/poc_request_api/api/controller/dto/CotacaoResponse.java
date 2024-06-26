package br.com.tomwell.poc_request_api.api.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CotacaoResponse {
    private Long id;
    private String sigla;
    private String descricao;
    private Double valor;
    private OffsetDateTime dataInicioVigencia;
    private OffsetDateTime dataFimVigencia;
}
