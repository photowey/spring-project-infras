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
package io.github.photowey.spring.infras.common.future;

import io.github.photowey.spring.infras.common.LocalTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * {@code FuturesTest}
 *
 * @author photowey
 * @version 1.3.0
 * @since 2024/04/28
 */
class FuturesTest extends LocalTest {

    private ExecutorService executor;

    @BeforeEach
    void init() {
        this.executor = Executors.newFixedThreadPool(2);
    }

    @AfterEach
    void destroy() {
        this.executor.shutdown();
    }

    @Test
    void testAsync_future() {
        CompletableFuture<Long> f1 = CompletableFuture.supplyAsync(() -> {
            sleep(1_000);
            return 89757L;
        }, this.executor);

        CompletableFuture<Long> f2 = CompletableFuture.supplyAsync(() -> {
            sleep(2_000);
            return 9527L;
        }, this.executor);

        CompletableFuture.allOf(f1, f2).join();

        Long id1 = Futures.get(f1);
        Long id2 = Futures.get(f2);

        Assertions.assertEquals(89757L, id1);
        Assertions.assertEquals(9527L, id2);
    }
}