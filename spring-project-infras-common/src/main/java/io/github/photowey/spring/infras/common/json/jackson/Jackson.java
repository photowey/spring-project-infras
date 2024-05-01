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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.photowey.spring.infras.common.thrower.AssertionErrorThrower;

import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
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

    private static final ConcurrentHashMap<Class<ObjectMapper>, ObjectMapper> ctx = new ConcurrentHashMap<>(2);

    private static ObjectMapper initDefaultObjectMapper() {
        JsonMapper.Builder builder = JsonMapper.builder()
                .configure(JsonParser.Feature.ALLOW_COMMENTS, true)
                .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
                .configure(JsonParser.Feature.IGNORE_UNDEFINED, true)
                .configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true)

                //.configure(DeserializationFeature.USE_LONG_FOR_INTS, true)

                .configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true)
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

    public static void injectSharedObjectMapper(ObjectMapper objectMapper) {
        sharedObjectMapper = objectMapper;
    }

    public static ObjectMapper getObjectMapper() {
        return sharedObjectMapper != null ? sharedObjectMapper : ctx.computeIfAbsent(ObjectMapper.class, (x) -> initDefaultObjectMapper());
    }

    public static void clean() {
        ctx.clear();
    }

    // ----------------------------------------------------------------

    /**
     * Parse JSON Object.
     *
     * @param json  the string json body.
     * @param clazz the target class.
     * @param <T>   the target class type.
     * @return T type.
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        return parseObject(getObjectMapper(), json, clazz);
    }

    public static <T> T parseObject(ObjectMapper objectMapper, String json, Class<T> clazz) {
        checkNPE(objectMapper);
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            return throwUnchecked(e);
        }
    }

    // ----------------------------------------------------------------

    public static <T> T parseObject(byte[] json, Class<T> clazz) {
        return parseObject(getObjectMapper(), json, clazz);
    }

    public static <T> T parseObject(ObjectMapper objectMapper, byte[] json, Class<T> clazz) {
        checkNPE(objectMapper);
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            return throwUnchecked(e);
        }
    }

    // ----------------------------------------------------------------

    public static <T> T parseObject(InputStream json, Class<T> clazz) {
        return parseObject(getObjectMapper(), json, clazz);
    }

    public static <T> T parseObject(ObjectMapper objectMapper, InputStream json, Class<T> clazz) {
        checkNPE(objectMapper);
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            return throwUnchecked(e);
        }
    }

    // ----------------------------------------------------------------

    public static <T> T parseObject(String json, TypeReference<T> typeRef) {
        return parseObject(getObjectMapper(), json, typeRef);
    }

    public static <T> T parseObject(ObjectMapper objectMapper, String json, TypeReference<T> typeRef) {
        checkNPE(objectMapper);
        try {
            return objectMapper.readValue(json, typeRef);
        } catch (Exception e) {
            return throwUnchecked(e);
        }
    }

    // ----------------------------------------------------------------

    public static <T> T parseObject(byte[] json, TypeReference<T> typeRef) {
        return parseObject(getObjectMapper(), json, typeRef);
    }

    public static <T> T parseObject(ObjectMapper objectMapper, byte[] json, TypeReference<T> typeRef) {
        checkNPE(objectMapper);
        try {
            return objectMapper.readValue(json, typeRef);
        } catch (Exception e) {
            return throwUnchecked(e);
        }
    }

    // ----------------------------------------------------------------

    public static <T> T parseObject(InputStream json, TypeReference<T> typeRef) {
        return parseObject(getObjectMapper(), json, typeRef);
    }

    public static <T> T parseObject(ObjectMapper objectMapper, InputStream json, TypeReference<T> typeRef) {
        checkNPE(objectMapper);
        try {
            return objectMapper.readValue(json, typeRef);
        } catch (Exception e) {
            return throwUnchecked(e);
        }
    }

    // ----------------------------------------------------------------

    // Notes:
    // Due to the introduction of the parameter TypeReference,
    // the essence of the parseArray and toXxx methods is the same, just a name difference.

    /**
     * Parse {@code json} Array.
     *
     * @param json    the string Array json body.
     * @param typeRef the target reference.
     * @param <T>     the target class type.
     * @return T type.
     */
    public static <T> T parseArray(String json, TypeReference<T> typeRef) {
        return parseObject(json, typeRef);
    }

    public static <T> T parseArray(ObjectMapper objectMapper, String json, TypeReference<T> typeRef) {
        return parseObject(objectMapper, json, typeRef);
    }

    // ----------------------------------------------------------------

    public static <T> T parseArray(byte[] json, TypeReference<T> typeRef) {
        return parseObject(json, typeRef);
    }

    public static <T> T parseArray(ObjectMapper objectMapper, byte[] json, TypeReference<T> typeRef) {
        return parseObject(objectMapper, json, typeRef);
    }

    // ----------------------------------------------------------------

    public static <T> T parseArray(InputStream json, TypeReference<T> typeRef) {
        return parseObject(json, typeRef);
    }

    public static <T> T parseArray(ObjectMapper objectMapper, InputStream json, TypeReference<T> typeRef) {
        return parseObject(objectMapper, json, typeRef);
    }

    // ----------------------------------------------------------------

    public static <T> T toList(String json, TypeReference<T> typeRef) {
        return parseObject(json, typeRef);
    }

    public static <T> T toList(byte[] json, TypeReference<T> typeRef) {
        return parseObject(json, typeRef);
    }

    public static <T> T toList(InputStream json, TypeReference<T> typeRef) {
        return parseObject(json, typeRef);
    }

    // ----------------------------------------------------------------

    public static <T> T toSet(String json, TypeReference<T> typeRef) {
        return parseObject(json, typeRef);
    }

    public static <T> T toSet(byte[] json, TypeReference<T> typeRef) {
        return parseObject(json, typeRef);
    }

    public static <T> T toSet(InputStream json, TypeReference<T> typeRef) {
        return parseObject(json, typeRef);
    }

    // ----------------------------------------------------------------

    public static <T> T toCollection(String json, TypeReference<T> typeRef) {
        return parseObject(json, typeRef);
    }

    public static <T> T toCollection(byte[] json, TypeReference<T> typeRef) {
        return parseObject(json, typeRef);
    }

    public static <T> T toCollection(InputStream json, TypeReference<T> typeRef) {
        return parseObject(json, typeRef);
    }

    // ----------------------------------------------------------------

    public static <T> T toMap(String json, TypeReference<T> typeRef) {
        return parseObject(json, typeRef);
    }

    public static <T> T toMap(byte[] json, TypeReference<T> typeRef) {
        return parseObject(json, typeRef);
    }

    public static <T> T toMap(InputStream json, TypeReference<T> typeRef) {
        return parseObject(json, typeRef);
    }

    // ----------------------------------------------------------------

    /**
     * Write an Object to json string.
     *
     * @param object the target object.
     * @param <T>    the target object type.
     * @return the string json body.
     */
    public static <T> String toJSONString(T object) {
        return toJSONString(object, Function.identity());
    }

    /**
     * Write an Object to json string with view.
     *
     * @param object the target object.
     * @param view   the json view.
     * @param <T>    the target object type.
     * @return the string json body.
     */
    public static <T> String toJSONString(T object, Class<?> view) {
        return toJSONString(object, (writer) -> {
            return null != view
                    ? writer.withView(view)
                    : writer;
        });
    }

    public static <T> String toJSONString(T object, Function<ObjectWriter, ObjectWriter> fx) {
        return toJSONString(getObjectMapper(), object, fx);
    }

    public static <T> String toJSONString(ObjectMapper objectMapper, T object, Function<ObjectWriter, ObjectWriter> fx) {
        checkNPE(objectMapper);
        try {
            ObjectWriter objectWriter = objectMapper.writer();
            objectWriter = fx.apply(objectWriter);
            return objectWriter.writeValueAsString(object);
        } catch (Exception e) {
            return throwUnchecked(e, String.class);
        }
    }

    // ----------------------------------------------------------------

    public static String toPrettyString(String json) {
        return toPrettyString(getObjectMapper(), json);
    }

    public static String toPrettyString(ObjectMapper objectMapper, String json) {
        checkNPE(objectMapper);
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

    public static <T> byte[] toBytes(T object) {
        return toBytes(getObjectMapper(), object);
    }

    public static <T> byte[] toBytes(ObjectMapper objectMapper, T object) {
        checkNPE(objectMapper);
        try {
            return objectMapper.writeValueAsBytes(object);
        } catch (Exception e) {
            return throwUnchecked(e, byte[].class);
        }
    }

    // ----------------------------------------------------------------

    public static JsonNode toJsonNode(String json) {
        return parseObject(json, JsonNode.class);
    }

    public static JsonNode toJsonNode(ObjectMapper objectMapper, String json) {
        return parseObject(objectMapper, json, JsonNode.class);
    }

    public static JsonNode toJsonNode(byte[] json) {
        return parseObject(json, JsonNode.class);
    }

    public static JsonNode toJsonNode(ObjectMapper objectMapper, byte[] json) {
        return parseObject(objectMapper, json, JsonNode.class);
    }

    public static JsonNode toJsonNode(InputStream json) {
        return parseObject(json, JsonNode.class);
    }

    public static JsonNode toJsonNode(ObjectMapper objectMapper, InputStream json) {
        return parseObject(objectMapper, json, JsonNode.class);
    }

    // ----------------------------------------------------------------

    public static <T> T toObject(Map<String, Object> map, Class<T> targetClass) {
        return toObject(getObjectMapper(), map, targetClass);
    }

    public static <T> T toObject(ObjectMapper objectMapper, Map<String, Object> map, Class<T> targetClass) {
        checkNPE(objectMapper);
        return objectMapper.convertValue(map, targetClass);
    }

    // ----------------------------------------------------------------

    public static <T> Map<String, Object> toMap(T object) {
        return toMap(getObjectMapper(), object);
    }

    public static <T> Map<String, Object> toMap(ObjectMapper objectMapper, T object) {
        checkNPE(objectMapper);
        return objectMapper.convertValue(object, new TypeReference<Map<String, Object>>() {});
    }

    // ----------------------------------------------------------------

    public static void checkNPE(ObjectMapper objectMapper) {
        Objects.requireNonNull(objectMapper, "infras: the objectMapper can't be null.");
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
