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
package io.github.photowey.spring.infras.common.function;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * {@code FunctionTest}
 *
 * @author photowey
 * @version 1.3.0
 * @since 2024/04/29
 */
class FunctionTest {

    @Test
    void testFunction() {
        Integer applied1 = this.apply(1, 2, Integer::sum);
        Assertions.assertEquals(3, applied1);

        Integer applied2 = this.apply(1, 2, 3, (t, u, v) -> {
            return t + u + v;
        });
        Assertions.assertEquals(6, applied2);

        Integer applied3 = this.apply(1, 2, 3, 4, (t, u, v, w) -> {
            return t + u + v + w;
        });
        Assertions.assertEquals(10, applied3);

        Integer applied4 = this.apply(1, 2, 3, 4, 5, (t, u, v, w, z) -> {
            return t + u + v + w + z;
        });
        Assertions.assertEquals(15, applied4);
    }

    private <T, U, R> R apply(T v1, U v2, DoubleFunction<T, U, R> fx) {
        return fx.apply(v1, v2);
    }

    private <T, U, V, R> R apply(T v1, U v2, V v3, TripleFunction<T, U, V, R> fx) {
        return fx.apply(v1, v2, v3);
    }

    private <T, U, V, W, R> R apply(T v1, U v2, V v3, W v4, QuadraFunction<T, U, V, W, R> fx) {
        return fx.apply(v1, v2, v3, v4);
    }

    private <T, U, V, W, Z, R> R apply(T v1, U v2, V v3, W v4, Z v5, PentaFunction<T, U, V, W, Z, R> fx) {
        return fx.apply(v1, v2, v3, v4, v5);
    }
}