package br.com.tomwell.poc_request_api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cotacao {
    private List<Integer> produtos;
    private String clientId;
    private String cpf;
    private int qtdVidas;
    private Long id;
    private String sigla;
    private String descricao;
    private Double valor;
    private OffsetDateTime dataInicioVigencia;
    private OffsetDateTime dataFimVigencia;
}
