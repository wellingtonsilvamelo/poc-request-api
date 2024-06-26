package br.com.tomwell.poc_request_api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProdutoEnum {
    AUTO(1801),
    RESIDE(1802),
    CELULAR(1803),
    VIDA(1804),
    RC(1805),
    CARTOES(1806);

    private final Integer idProduto;
}
