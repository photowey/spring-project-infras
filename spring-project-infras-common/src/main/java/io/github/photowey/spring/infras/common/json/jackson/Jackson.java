/*
 * Copyright © 2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.photowey.spring.infras.common.json.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.photowey.spring.infras.common.thrower.AssertionErrorThrower;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * {@code Jackson}
 *
 * @author photowey
 * @version 1.3.0
 * @since 2024/04/28
 */
public final class Jackson {

    private Jackson() {
        AssertionErrorThrower.throwz(Jackson.class);
    }

    private static ObjectMapper sharedObjectMapper;

    private static final InheritableThreadLocal<ObjectMapper> objectMapperHolder = new InheritableThreadLocal<ObjectMapper>() {
        @Override
        protected ObjectMapper initialValue() {
            return initObjectMapper();
        }
    };

    private static ObjectMapper initDefaultObjectMapper() {
        JsonMapper.Builder builder = JsonMapper.builder()
                .configure(JsonParser.Feature.ALLOW_COMMENTS, true)
                .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
                .configure(JsonParser.Feature.IGNORE_UNDEFINED, true)
                .configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true)

                .configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true)
                .configure(DeserializationFeature.USE_LONG_FOR_INTS, true)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)

                // Exclude properties not annotated with @JsonView
                .configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false)
                .addModule(new JavaTimeModule());

        JsonMapper jsonMapper = builder.build();
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return jsonMapper;
    }

    // ----------------------------------------------------------------

    private static ObjectMapper initObjectMapper() {
        return sharedObjectMapper != null ? sharedObjectMapper : initDefaultObjectMapper();
    }

    public static void injectSharedObjectMapper(ObjectMapper objectMapper) {
        sharedObjectMapper = objectMapper;
    }

    public static ObjectMapper getObjectMapper() {
        return sharedObjectMapper != null ? sharedObjectMapper : objectMapperHolder.get();
    }

    // ----------------------------------------------------------------

    /**
     * Parse Json Object.
     *
     * @param json  the string json body.
     * @param clazz the target class.
     * @param <T>   the target class type.
     * @return T type.
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        try {
            ObjectMapper mapper = getObjectMapper();
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException processingException) {
            return throwUnchecked(processingException);
        }
    }

    public static <T> T parseObject(byte[] json, Class<T> clazz) {
        try {
            ObjectMapper mapper = getObjectMapper();
            return mapper.readValue(json, clazz);
        } catch (Exception processingException) {
            return throwUnchecked(processingException);
        }
    }

    public static <T> T parseObject(InputStream json, Class<T> clazz) {
        try {
            ObjectMapper mapper = getObjectMapper();
            return mapper.readValue(json, clazz);
        } catch (Exception processingException) {
            return throwUnchecked(processingException);
        }
    }

    public static <T> T parseObject(String json, TypeReference<T> typeRef) {
        try {
            ObjectMapper mapper = getObjectMapper();
            return mapper.readValue(json, typeRef);
        } catch (JsonProcessingException processingException) {
            return throwUnchecked(processingException);
        }
    }

    public static <T> T parseObject(byte[] json, TypeReference<T> typeRef) {
        try {
            ObjectMapper mapper = getObjectMapper();
            return mapper.readValue(json, typeRef);
        } catch (Exception processingException) {
            return throwUnchecked(processingException);
        }
    }

    public static <T> T parseObject(InputStream json, TypeReference<T> typeRef) {
        try {
            ObjectMapper mapper = getObjectMapper();
            return mapper.readValue(json, typeRef);
        } catch (Exception processingException) {
            return throwUnchecked(processingException);
        }
    }

    // ----------------------------------------------------------------

    public static <T> List<T> parseArray(byte[] json) {
        return parseObject(json, new TypeReference<List<T>>() {});
    }

    public static <T> List<T> parseArray(byte[] json, Class<T> clazz) {
        return parseObject(json, new TypeReference<List<T>>() {});
    }

    public static <T> List<T> parseArray(InputStream json) {
        return parseObject(json, new TypeReference<List<T>>() {});
    }

    public static <T> List<T> parseArray(InputStream json, Class<T> clazz) {
        return parseObject(json, new TypeReference<List<T>>() {});
    }

    // ----------------------------------------------------------------

    public static <T> List<T> parseArray(String json) {
        return parseObject(json, new TypeReference<List<T>>() {});
    }

    /**
     * Parse {@code json} Array.
     *
     * @param json  the string Array json body.
     * @param clazz the target class.
     * @param <T>   the target class type.
     * @return T type.
     */
    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        return parseObject(json, new TypeReference<List<T>>() {});
    }

    // ----------------------------------------------------------------

    public static <T> List<T> toList(String json) {
        return parseArray(json);
    }

    public static <T> List<T> toList(String json, Class<T> clazz) {
        return parseArray(json, clazz);
    }

    // ----------------------------------------------------------------

    public static <T> Set<T> toSet(String json) {
        return parseObject(json, new TypeReference<Set<T>>() {});
    }

    public static <T> Set<T> toSet(String json, Class<T> clazz) {
        return parseObject(json, new TypeReference<Set<T>>() {});
    }

    // ----------------------------------------------------------------

    public static <T> Collection<T> toCollection(String json) {
        return parseObject(json, new TypeReference<Collection<T>>() {});
    }

    public static <T> Collection<T> toCollection(String json, Class<T> clazz) {
        return parseObject(json, new TypeReference<Collection<T>>() {});
    }

    // ----------------------------------------------------------------

    public static <T> Map<String, T> toMap(String json) {
        return parseObject(json, new TypeReference<Map<String, T>>() {});
    }

    public static <T> Map<String, T> toMap(String json, Class<T> clazz) {
        return parseObject(json, new TypeReference<Map<String, T>>() {});
    }

    // ----------------------------------------------------------------

    /**
     * Write a Object to json string.
     *
     * @param object the target object.
     * @param <T>    the target object type.
     * @return the string json body.
     */
    public static <T> String toJsonString(T object) {
        return toJsonString(object, Function.identity());
    }

    public static <T> String toJsonString(T object, Class<?> view) {
        return toJsonString(object, (writer) -> {
            return null != view
                    ? writer.withView(view)
                    : writer;
        });
    }

    public static <T> String toJsonString(T object, Function<ObjectWriter, ObjectWriter> fx) {
        try {
            ObjectMapper objectMapper = getObjectMapper();
            ObjectWriter objectWriter = objectMapper.writer();
            objectWriter = fx.apply(objectWriter);
            return objectWriter.writeValueAsString(object);
        } catch (Exception ioe) {
            return throwUnchecked(ioe, String.class);
        }
    }

    // ----------------------------------------------------------------

    public static String toPrettyJson(String json) {
        ObjectMapper objectMapper = getObjectMapper();
        try {
            // @formatter:off
            return objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(objectMapper.readValue(json, JsonNode.class));
            // @formatter:on
        } catch (Exception e) {
            return throwUnchecked(e, String.class);
        }
    }

    // ----------------------------------------------------------------

    public static <T> byte[] toByteArray(T object) {
        try {
            ObjectMapper mapper = getObjectMapper();
            return mapper.writeValueAsBytes(object);
        } catch (Exception ioe) {
            return throwUnchecked(ioe, byte[].class);
        }
    }

    // ----------------------------------------------------------------

    public static JsonNode toJsonNode(String json) {
        return parseObject(json, JsonNode.class);
    }

    // ----------------------------------------------------------------

    public static <T> T toObject(Map<String, Object> map, Class<T> targetClass) {
        ObjectMapper mapper = getObjectMapper();
        return mapper.convertValue(map, targetClass);
    }

    // ----------------------------------------------------------------

    public static <T> Map<String, Object> toMap(T object) {
        ObjectMapper mapper = getObjectMapper();
        return mapper.convertValue(object, new TypeReference<Map<String, Object>>() {});
    }

    // ----------------------------------------------------------------

    public static <T> T throwUnchecked(final Throwable ex, final Class<T> returnType) {
        throwsUnchecked(ex);
        throw new AssertionError("json: this.code.should.be.unreachable.here!");
    }

    public static <T> T throwUnchecked(final Throwable ex) {
        return throwUnchecked(ex, null);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Throwable> void throwsUnchecked(Throwable throwable) throws T {
        throw (T) throwable;
    }
}
