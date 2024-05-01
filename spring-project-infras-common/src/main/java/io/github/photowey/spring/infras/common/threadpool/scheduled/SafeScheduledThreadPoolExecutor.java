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

import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * {@code SafeScheduledThreadPoolExecutor}
 *
 * @author photowey
 * @version 1.3.0
 * @since 2024/04/28
 */
public class SafeScheduledThreadPoolExecutor extends ScheduledThreadPoolExecutor {

    public SafeScheduledThreadPoolExecutor(int corePoolSize) {
        super(corePoolSize);
    }

    public SafeScheduledThreadPoolExecutor(int corePoolSize, ThreadFactory threadFactory) {
        super(corePoolSize, threadFactory);
    }

    public SafeScheduledThreadPoolExecutor(int corePoolSize, RejectedExecutionHandler handler) {
        super(corePoolSize, handler);
    }

    public SafeScheduledThreadPoolExecutor(int corePoolSize, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, threadFactory, handler);
    }

    // ----------------------------------------------------------------

    // Ensure that when the thread pool is executing a task,
    // the task throws an uncontrollable exception and causes task scheduling to be blocked.

    // ----------------------------------------------------------------

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        return this.scheduleWithFixedDelay(command, initialDelay, delay, unit, (x) -> {});
    }

    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit, Consumer<Throwable> fx) {
        return super.scheduleWithFixedDelay(() -> this.safeRun(command, fx), initialDelay, delay, unit);
    }

    // ----------------------------------------------------------------

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        return this.scheduleAtFixedRate(command, initialDelay, period, unit, (x) -> {});
    }

    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit, Consumer<Throwable> fx) {
        return super.scheduleAtFixedRate(() -> this.safeRun(command, fx), initialDelay, period, unit);
    }

    // ----------------------------------------------------------------

    public void safeRun(Runnable command, Consumer<Throwable> fx) {
        try {
            command.run();
        } catch (Throwable e) {
            fx.accept(e);
        }
    }
}