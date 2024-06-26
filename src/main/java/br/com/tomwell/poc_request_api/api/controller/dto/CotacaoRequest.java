package br.com.tomwell.poc_request_api.api.controller.dto;

import java.util.List;

public record CotacaoRequest(
    String clientId, String cpf, int qtdVidas, List<Integer> produtos
) {
}
