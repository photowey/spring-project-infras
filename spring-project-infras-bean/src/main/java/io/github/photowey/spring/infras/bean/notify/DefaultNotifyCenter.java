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

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * {@code DefaultNotifyCenter}
 *
 * @author photowey
 * @version 1.1.0
 * @since 2024/04/26
 */
public class DefaultNotifyCenter implements NotifyCenter {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }

    @Override
    public ApplicationContext applicationContext() {
        return this.applicationContext;
    }

    @Override
    public <E extends ApplicationEvent> void publishEvent(E event) {
        this.applicationContext.publishEvent(event);
    }

    @Override
    public <E extends ApplicationEvent> void publishAsyncEvent(E event, Executor executor) {
        CompletableFuture.runAsync(() -> this.publishEvent(event), executor);
    }
}
