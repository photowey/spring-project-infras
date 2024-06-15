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
package io.github.photowey.spring.infras.core.converter;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * {@code JsonConverter}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/04/29
 */
public interface JsonConverter extends Converter {

    <P> String toJSONString(P payload);

    <T> T parseObject(String body, Class<T> clazz);

    <T> T parseObject(byte[] body, Class<T> clazz);

    <T> T parseObject(InputStream body, Class<T> clazz);

    // ----------------------------------------------------------------

    <T> List<T> parseArray(String body, Class<T> clazz);

    <T> List<T> parseArray(byte[] body, Class<T> clazz);

    <T> List<T> parseArray(InputStream body, Class<T> clazz);

    // ----------------------------------------------------------------

    <T> T toObject(Map<String, Object> map, Class<T> targetClass);

    <T> Map<String, Object> toMap(T object);

    // ----------------------------------------------------------------

    default <T> T throwUnchecked(final Throwable ex, final Class<T> returnType) {
        throwsUnchecked(ex);
        throw new AssertionError("json: this.code.should.be.unreachable.here!");
    }

    default <T> T throwUnchecked(final Throwable ex) {
        return throwUnchecked(ex, null);
    }

    @SuppressWarnings("unchecked")
    default <T extends Throwable> void throwsUnchecked(Throwable throwable) throws T {
        throw (T) throwable;
    }
}