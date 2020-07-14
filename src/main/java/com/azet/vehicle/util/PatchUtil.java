package com.azet.vehicle.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import java.util.function.Consumer;

public class PatchUtil {

    public static <T> T apply(JsonPatch patch, T dto, Consumer<T> patchCallback) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode patched = patch.apply(objectMapper.convertValue(dto, JsonNode.class));
            @SuppressWarnings("unchecked")
            T patchedValue = objectMapper.treeToValue(patched, (Class<T>) dto.getClass());
            patchCallback.accept(patchedValue);
            return patchedValue;
        } catch (JsonPatchException | JsonProcessingException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
