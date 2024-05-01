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
package io.github.photowey.spring.infras.common.threadpool.scheduled;

import io.github.photowey.spring.infras.common.LocalTest;
import io.github.photowey.spring.infras.common.future.Sleepers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * {@code SafeScheduledThreadPoolExecutorTest}
 *
 * @author photowey
 * @version 1.3.0
 * @since 2024/04/28
 */
class SafeScheduledThreadPoolExecutorTest extends LocalTest {

    private ScheduledThreadPoolExecutor executor;
    private SafeScheduledThreadPoolExecutor safeExecutor;
    private ScheduledThreadPoolExecutorContext ctx;

    public static class ScheduledThreadPoolExecutorContext {

        private int counter;

        public ScheduledThreadPoolExecutorContext(int counter) {
            this.counter = counter;
        }

        public int getCounter() {
            return counter;
        }

        public void setCounter(int counter) {
            this.counter = counter;
        }

        public void increment() {
            this.counter++;
        }
    }

    @BeforeEach
    void init() {
        this.executor = new ScheduledThreadPoolExecutor(2);
        this.safeExecutor = new SafeScheduledThreadPoolExecutor(2);
        this.ctx = new ScheduledThreadPoolExecutorContext(0);
    }

    @AfterEach
    void destroy() {
        this.executor.shutdown();
    }

    @Test
    void testScheduleAtFixedRate() {
        this.executor.scheduleAtFixedRate(() -> {
            this.ctx.increment();
            if (1 != 2) {
                throw new RuntimeException("crash");
            }
        }, 1, 1, TimeUnit.SECONDS);

        Sleepers.sleep(5, TimeUnit.SECONDS);
        Assertions.assertEquals(1, this.ctx.getCounter());
    }

    @Test
    void testSafeScheduleAtFixedRate() {
        this.safeExecutor.scheduleAtFixedRate(() -> {
            this.ctx.increment();
            if (1 != 2) {
                throw new RuntimeException("crash");
            }
        }, 1, 1, TimeUnit.SECONDS);

        Sleepers.sleep(5, TimeUnit.SECONDS);
        Assertions.assertTrue(this.ctx.getCounter() > 1);
    }

    @Test
    void testSafeScheduleAtFixedRate_custom() {
        this.safeExecutor.scheduleAtFixedRate(() -> {
            this.ctx.increment();
            if (1 != 2) {
                throw new RuntimeException("crash");
            }
        }, 1, 1, TimeUnit.SECONDS, (t) -> {
            throw new RuntimeException("Rethrow");
        });

        Sleepers.sleep(5, TimeUnit.SECONDS);
        Assertions.assertEquals(1, this.ctx.getCounter());
    }

    @Test
    void testSafeScheduleAtFixedRate_custom_silent() {
        this.safeExecutor.scheduleAtFixedRate(() -> {
            this.ctx.increment();
            if (1 != 2) {
                throw new RuntimeException("crash");
            }
        }, 1, 1, TimeUnit.SECONDS, Throwable::printStackTrace);

        Sleepers.sleep(5, TimeUnit.SECONDS);
        Assertions.assertTrue(this.ctx.getCounter() > 1);
    }
}