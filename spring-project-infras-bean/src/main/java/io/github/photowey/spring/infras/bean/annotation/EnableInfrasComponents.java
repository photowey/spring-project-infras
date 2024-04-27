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
package io.github.photowey.spring.infras.bean.annotation;

import io.github.photowey.spring.infras.bean.engine.notify.NotifyEngine;
import io.github.photowey.spring.infras.bean.engine.notify.NotifyEngineAwareBeanPostProcessor;
import io.github.photowey.spring.infras.bean.engine.notify.NotifyEngineImpl;
import io.github.photowey.spring.infras.bean.notify.DefaultNotifyCenter;
import io.github.photowey.spring.infras.bean.notify.NotifyCenter;
import io.github.photowey.spring.infras.core.context.ApplicationContextInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * {@code EnableInfrasComponents}
 *
 * @author photowey
 * @version 1.1.0
 * @since 2024/04/26
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(value = {NotifyEngineAwareBeanPostProcessor.class, EnableInfrasComponents.InfrasEngineConfigure.class, EnableInfrasComponents.InfrasComponentConfigure.class,})
public @interface EnableInfrasComponents {

    @Configuration
    class InfrasEngineConfigure {

        @Bean
        public NotifyEngine notifyEngine() {
            return new NotifyEngineImpl();
        }
    }

    @Configuration
    class InfrasComponentConfigure {

        @Bean
        public ApplicationContextInjector applicationContextInjector() {
            return new ApplicationContextInjector();
        }

        @Bean(NotifyCenter.SPRING_INFRAS_NOTIFIER_BEAN_NAME)
        public NotifyCenter notifyCenter() {
            return new DefaultNotifyCenter();
        }
    }
}
