package br.com.tomwell.poc_request_api.mapper;

import br.com.tomwell.poc_request_api.api.controller.dto.CotacaoRequest;
import br.com.tomwell.poc_request_api.api.controller.dto.CotacaoResponse;
import br.com.tomwell.poc_request_api.model.Cotacao;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CotacaoMapper {
    CotacaoMapper INSTANCE = Mappers.getMapper(CotacaoMapper.class);

    Cotacao toCotacao(CotacaoRequest cotacaoRequest);
    CotacaoRequest toCotacaoRequest(Cotacao cotacao);
    CotacaoResponse toCotacaoResponse(Cotacao cotacao);
    Cotacao toCotacao(Map<String, Object> map);

    default Double mapToDouble(Object value) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        return null;
    }

    default List<Integer> mapToListInteger(Object value) {
        if (value instanceof List<?>) {
            return ((List<?>) value).stream()
                    .filter(item -> item instanceof Integer)
                    .map(item -> (Integer) item)
                    .collect(Collectors.toList());
        }
        return null;
    }

    default OffsetDateTime mapToOffsetDateTime(Object value) {
        if (value instanceof String) {
            return OffsetDateTime.parse((String) value, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }
        return null;
    }
}
