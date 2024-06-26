package br.com.tomwell.poc_request_api.service.future;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CotacaoStrategy {

    private final Map<Integer, CotacaoProdutoFeature<?>> strategies;

    public CotacaoStrategy(List<CotacaoProdutoFeature<?>> features) {
        this.strategies = new HashMap<>();
        features.forEach(feature -> strategies.put(feature.getIdProduto(), feature));
    }

    public CotacaoProdutoFeature<?> getStrategy(Integer productId) {
        return strategies.get(productId);
    }
}
