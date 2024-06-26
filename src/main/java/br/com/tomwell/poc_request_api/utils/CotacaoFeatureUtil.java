package br.com.tomwell.poc_request_api.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CotacaoFeatureUtil {

    public static Map<String, Object> processFuturesMap(Map<Integer, CompletableFuture<?>> futuresMap) {
        Map<String, Object> resultMap = new HashMap<>();

        futuresMap.forEach((key, future) -> {
            try {
                Object obj = future.get();
                Class<?> objClass = obj.getClass();
                Field[] fields = objClass.getDeclaredFields();

                for (Field field : fields) {
                    field.setAccessible(true);
                    resultMap.put(field.getName(), field.get(obj));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return resultMap;
    }
}