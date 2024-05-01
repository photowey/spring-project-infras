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
package io.github.photowey.spring.infras.common.function.consumer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * {@code ConsumerTest}
 *
 * @author photowey
 * @version 1.3.0
 * @since 2024/04/29
 */
class ConsumerTest {

    @Test
    void testConsumer() {
        this.accept(1, 2, (t, u) -> {
            Assertions.assertEquals(t, 1);
            Assertions.assertEquals(u, 2);
        });

        this.accept(1, 2, 3, (t, u, v) -> {
            Assertions.assertEquals(t, 1);
            Assertions.assertEquals(u, 2);
            Assertions.assertEquals(v, 3);
        });

        this.accept(1, 2, 3, 4, (t, u, v, w) -> {
            Assertions.assertEquals(t, 1);
            Assertions.assertEquals(u, 2);
            Assertions.assertEquals(v, 3);
            Assertions.assertEquals(w, 4);
        });

        this.accept(1, 2, 3, 4, 5, (t, u, v, w, z) -> {
            Assertions.assertEquals(t, 1);
            Assertions.assertEquals(u, 2);
            Assertions.assertEquals(v, 3);
            Assertions.assertEquals(w, 4);
            Assertions.assertEquals(z, 5);
        });
    }

    private <T, U> void accept(T v1, U v2, DoubleConsumer<T, U> fx) {
        fx.accept(v1, v2);
    }

    private <T, U, V> void accept(T v1, U v2, V v3, TripleConsumer<T, U, V> fx) {
        fx.accept(v1, v2, v3);
    }

    private <T, U, V, W> void accept(T v1, U v2, V v3, W v4, QuadraConsumer<T, U, V, W> fx) {
        fx.accept(v1, v2, v3, v4);
    }

    private <T, U, V, W, Z> void accept(T v1, U v2, V v3, W v4, Z v5, PentaConsumer<T, U, V, W, Z> fx) {
        fx.accept(v1, v2, v3, v4, v5);
    }

    public static class ConsumerContext {

        private int counter;

        public ConsumerContext() {}

        public ConsumerContext(int counter) {
            this.counter = counter;
        }

        public int getCounter() {
            return counter;
        }

        public void setCounter(int counter) {
            this.counter = counter;
        }

        public int counter() {
            return counter;
        }

        public ConsumerContext counter(int counter) {
            this.counter = counter;
            return this;
        }
    }
}