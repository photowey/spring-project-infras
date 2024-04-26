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
package io.github.photowey.spring.infras.bean.notify;

import io.github.photowey.spring.infras.core.getter.ApplicationContextGetter;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

import java.util.concurrent.Executor;

/**
 * {@code NotifyCenter}
 *
 * @author photowey
 * @version 1.1.0
 * @since 2024/04/26
 */
public interface NotifyCenter extends ApplicationContextAware, ApplicationContextGetter {

    String SPRING_INFRAS_NOTIFIER_BEAN_NAME = "springInfrasNotifyCenter";
    String NOTIFY_EXECUTOR_BEAN_NAME = "notifyAsyncExecutor";

    String NOTIFY_EXECUTOR_CORE_POOL_SIZE_CONFIG_KEY = "io.github.photowey.notify.async.executor.core.pool.size";
    String NOTIFY_EXECUTOR_MAX_POOL_SIZE_CONFIG_KEY = "io.github.photowey.notify.async.executor.max.pool.size";
    String NOTIFY_EXECUTOR_QUEUE_CAPACITY_CONFIG_KEY = "io.github.photowey.notify.async.executor.queue.capacity";
    String NOTIFY_EXECUTOR_KEEP_ALIVE_CONFIG_KEY = "io.github.photowey.notify.async.executor.keep.alive";

    /**
     * Publish event.
     *
     * @param event the event.
     * @param <E>   the event type.
     */
    <E extends ApplicationEvent> void publishEvent(E event);

    /**
     * Publish async event.
     *
     * @param event the async event.
     * @param <E>   the event type.
     */
    default <E extends ApplicationEvent> void publishAsyncEvent(E event) {
        this.publishAsyncEvent(event, this.tryAcquireDefaultEventExecutor());
    }

    /**
     * Publish async event.
     *
     * @param event    the async event.
     * @param executor the async executor of event.
     * @param <E>      the event type.
     */
    <E extends ApplicationEvent> void publishAsyncEvent(E event, Executor executor);

    default Executor tryAcquireDefaultEventExecutor() {
        try {
            return this.applicationContext().getBean(NOTIFY_EXECUTOR_BEAN_NAME, Executor.class);
        } catch (NoSuchBeanDefinitionException e) {
            throw new RuntimeException("infras: not found an Executor(java.util.concurrent.Executor) Bean named: notifyAsyncExecutor");
        }
    }
}
