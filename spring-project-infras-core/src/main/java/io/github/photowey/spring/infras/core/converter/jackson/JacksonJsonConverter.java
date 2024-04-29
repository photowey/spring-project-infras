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
package io.github.photowey.spring.infras.core.converter.jackson;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.photowey.spring.infras.core.converter.JsonConverter;
import io.github.photowey.spring.infras.core.getter.ObjectMapperGetter;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * {@code JacksonJsonConverter}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/04/29
 */
public interface JacksonJsonConverter extends JsonConverter, ObjectMapperGetter {

    String JACKSON_JSON_CONVERTER_BEAN_NAME = "io.github.photowey.spring.infras.core.converter.jackson.JacksonJsonConverter";

    @Override
    default <P> String toJSONString(P payload) {
        try {
            return this.objectMapper().writeValueAsString(payload);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    default <T> T parseObject(String body, Class<T> clazz) {
        try {
            return this.objectMapper().readValue(body, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    default <T> T parseObject(byte[] body, Class<T> clazz) {
        try {
            return this.objectMapper().readValue(body, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    default <T> T parseObject(InputStream body, Class<T> clazz) {
        try {
            return this.objectMapper().readValue(body, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    default <T> List<T> parseArray(String body, Class<T> clazz) {
        return this.parseObject(body, new TypeReference<List<T>>() {});
    }

    @Override
    default <T> List<T> parseArray(byte[] body, Class<T> clazz) {
        return this.parseObject(body, new TypeReference<List<T>>() {});
    }

    @Override
    default <T> List<T> parseArray(InputStream body, Class<T> clazz) {
        return this.parseObject(body, new TypeReference<List<T>>() {});
    }

    @Override
    default <T> T toObject(Map<String, Object> map, Class<T> targetClass) {
        try {
            return this.objectMapper().convertValue(map, targetClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    default <T> Map<String, Object> toMap(T object) {
        try {
            return this.objectMapper().convertValue(object, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    default <T> T parseObject(String body, TypeReference<T> clazz) {
        try {
            return this.objectMapper().readValue(body, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    default <T> T parseObject(byte[] body, TypeReference<T> clazz) {
        try {
            return this.objectMapper().readValue(body, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    default <T> T parseObject(InputStream body, TypeReference<T> clazz) {
        try {
            return this.objectMapper().readValue(body, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}