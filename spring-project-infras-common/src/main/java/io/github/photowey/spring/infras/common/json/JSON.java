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
package io.github.photowey.spring.infras.common.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * {@code JSON}
 *
 * @author photowey
 * @version 1.3.0
 * @since 2024/04/29
 */
public enum JSON {

    ;


    public enum Jackson {

        ;

        /**
         * Parse JSON Object.
         *
         * @param json  the string json body.
         * @param clazz the target class.
         * @param <T>   the target class type.
         * @return T type.
         */
        public static <T> T parseObject(String json, Class<T> clazz) {
            return io.github.photowey.spring.infras.common.json.jackson.Jackson.parseObject(json, clazz);
        }

        public static <T> T parseObject(ObjectMapper objectMapper, String json, Class<T> clazz) {
            return io.github.photowey.spring.infras.common.json.jackson.Jackson.parseObject(objectMapper, json, clazz);
        }

        public static <T> T parseObject(byte[] json, Class<T> clazz) {
            return io.github.photowey.spring.infras.common.json.jackson.Jackson.parseObject(json, clazz);
        }

        public static <T> T parseObject(ObjectMapper objectMapper, byte[] json, Class<T> clazz) {
            return io.github.photowey.spring.infras.common.json.jackson.Jackson.parseObject(objectMapper, json, clazz);
        }

        public static <T> T parseObject(InputStream json, Class<T> clazz) {
            return io.github.photowey.spring.infras.common.json.jackson.Jackson.parseObject(json, clazz);
        }

        public static <T> T parseObject(ObjectMapper objectMapper, InputStream json, Class<T> clazz) {
            return io.github.photowey.spring.infras.common.json.jackson.Jackson.parseObject(objectMapper, json, clazz);
        }

        public static <T> T parseObject(String json, TypeReference<T> typeRef) {
            return io.github.photowey.spring.infras.common.json.jackson.Jackson.parseObject(json, typeRef);
        }

        public static <T> T parseObject(ObjectMapper objectMapper, String json, TypeReference<T> typeRef) {
            return io.github.photowey.spring.infras.common.json.jackson.Jackson.parseObject(objectMapper, json, typeRef);
        }

        public static <T> T parseObject(byte[] json, TypeReference<T> typeRef) {
            return io.github.photowey.spring.infras.common.json.jackson.Jackson.parseObject(json, typeRef);
        }

        public static <T> T parseObject(ObjectMapper objectMapper, byte[] json, TypeReference<T> typeRef) {
            return io.github.photowey.spring.infras.common.json.jackson.Jackson.parseObject(objectMapper, json, typeRef);
        }

        public static <T> T parseObject(InputStream json, TypeReference<T> typeRef) {
            return io.github.photowey.spring.infras.common.json.jackson.Jackson.parseObject(json, typeRef);
        }

        public static <T> T parseObject(ObjectMapper objectMapper, InputStream json, TypeReference<T> typeRef) {
            return io.github.photowey.spring.infras.common.json.jackson.Jackson.parseObject(objectMapper, json, typeRef);
        }

        // ----------------------------------------------------------------

        public static <T> List<T> parseArray(byte[] json) {
            return io.github.photowey.spring.infras.common.json.jackson.Jackson.parseArray(json);
        }

        public static <T> List<T> parseArray(byte[] json, Class<T> clazz) {
            return io.github.photowey.spring.infras.common.json.jackson.Jackson.parseArray(json, clazz);
        }

        public static <T> List<T> parseArray(InputStream json) {
            return io.github.photowey.spring.infras.common.json.jackson.Jackson.parseArray(json);
        }

        public static <T> List<T> parseArray(InputStream json, Class<T> clazz) {
            return io.github.photowey.spring.infras.common.json.jackson.Jackson.parseArray(json, clazz);
        }

        // ----------------------------------------------------------------

        public static <T> List<T> parseArray(String json) {
            return io.github.photowey.spring.infras.common.json.jackson.Jackson.parseArray(json);
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
            return io.github.photowey.spring.infras.common.json.jackson.Jackson.parseArray(json, clazz);
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
            return io.github.photowey.spring.infras.common.json.jackson.Jackson.toJSONString(object);
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
            return io.github.photowey.spring.infras.common.json.jackson.Jackson.toJSONString(object, view);
        }

        public static <T> String toJSONString(T object, Function<ObjectWriter, ObjectWriter> fx) {
            return io.github.photowey.spring.infras.common.json.jackson.Jackson.toJSONString(object, fx);
        }

        public static <T> String toJSONString(ObjectMapper objectMapper, T object, Function<ObjectWriter, ObjectWriter> fx) {
            return io.github.photowey.spring.infras.common.json.jackson.Jackson.toJSONString(objectMapper, object, fx);
        }

        // ----------------------------------------------------------------

        public static String toPrettyString(String json) {
            return io.github.photowey.spring.infras.common.json.jackson.Jackson.toPrettyString(json);
        }

        public static String toPrettyString(ObjectMapper objectMapper, String json) {
            return io.github.photowey.spring.infras.common.json.jackson.Jackson.toPrettyString(objectMapper, json);
        }

        // ----------------------------------------------------------------

        public static <T> byte[] toBytes(T object) {
            return io.github.photowey.spring.infras.common.json.jackson.Jackson.toBytes(object);
        }

        public static <T> byte[] toBytes(ObjectMapper objectMapper, T object) {
            return io.github.photowey.spring.infras.common.json.jackson.Jackson.toBytes(objectMapper, object);
        }

        // ----------------------------------------------------------------

        public static JsonNode toJsonNode(String json) {
            return io.github.photowey.spring.infras.common.json.jackson.Jackson.toJsonNode(json);
        }

        public static JsonNode toJsonNode(ObjectMapper objectMapper, String json) {
            return io.github.photowey.spring.infras.common.json.jackson.Jackson.toJsonNode(objectMapper, json);
        }

        // ----------------------------------------------------------------

        public static <T> T toObject(Map<String, Object> map, Class<T> targetClass) {
            return io.github.photowey.spring.infras.common.json.jackson.Jackson.toObject(map, targetClass);
        }

        public static <T> T toObject(ObjectMapper objectMapper, Map<String, Object> map, Class<T> targetClass) {
            return io.github.photowey.spring.infras.common.json.jackson.Jackson.toObject(objectMapper, map, targetClass);
        }

        // ----------------------------------------------------------------

        public static <T> Map<String, Object> toMap(T object) {
            return io.github.photowey.spring.infras.common.json.jackson.Jackson.toMap(object);
        }

        public static <T> Map<String, Object> toMap(ObjectMapper objectMapper, T object) {
            return io.github.photowey.spring.infras.common.json.jackson.Jackson.toMap(objectMapper, object);
        }
    }

    public enum Fastjson {

        ;
    }

    public enum Gson {

        ;
    }

}
