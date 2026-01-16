package es.upm.etsisi.poo.MapDB;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import java.io.*;
import java.util.HashMap;

public class JacksonSerializer {
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY
        );
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static byte[] serialize(Object obj) {
        try {
            return mapper.writeValueAsBytes(obj);
        } catch (Exception e) {
            System.err.println("ERROR serializando: " + e.getMessage());
            throw new RuntimeException("Error serializando", e);
        }
    }

    public static <T> T deserialize(byte[] data, Class<T> clazz) {
        if (data == null || data.length == 0) return null;

        try {
            return mapper.readValue(data, clazz);
        } catch (Exception e) {
            System.err.println("ERROR deserializando a " + clazz.getSimpleName());
            System.err.println("  Causa: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            try {
                String json = new String(data, "UTF-8");
                System.err.println("  JSON (primeros 300 chars): " +
                        (json.length() > 300 ? json.substring(0, 300) + "..." : json));
            } catch (UnsupportedEncodingException e1) {
                System.err.println("  No se pudo convertir bytes a string");
            }

            return null;
        }
    }
    public static es.upm.etsisi.poo.products.Product deserializeProduct(byte[] data) {
        return deserialize(data, es.upm.etsisi.poo.products.Product.class);
    }
    @SuppressWarnings("unchecked")
    public static HashMap<String, Object> deserializeHashMap(byte[] data) {
        return deserialize(data, HashMap.class);
    }
}