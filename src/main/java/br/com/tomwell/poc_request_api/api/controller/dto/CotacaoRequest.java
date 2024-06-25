package br.com.tomwell.poc_request_api.api.controller.dto;

public record CotacaoRequest(
    String clientId, String cpf, int qtdVidas
) {
}
