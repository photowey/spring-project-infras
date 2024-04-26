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
package io.github.photowey.spring.infras.bean.engine;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * {@code AbstractEngineAwareBeanPostProcessor}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/04/26
 */
public abstract class AbstractEngineAwareBeanPostProcessor<E extends Engine> implements EngineAwareBeanPostProcessor {

    protected ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (this.supports(bean)) {
            E engine = this.tryAcquireEngine(beanFactory);
            this.inject(engine, bean);
        }

        return bean;
    }

    public abstract boolean supports(Object bean);

    public abstract E tryAcquireEngine(ConfigurableListableBeanFactory beanFactory);

    public abstract void inject(E engine, Object bean);
}
