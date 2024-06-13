package br.com.tomwell.poc_request_api.dto;

public record QuoteRequest(
    String clientId, String cpf, int qtdVidas
) {
}
