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
package io.github.photowey.spring.infras.starter.autoconfigure.config;

import io.github.photowey.spring.infras.bean.annotation.EnableInfrasComponents;
import io.github.photowey.spring.infras.bean.notify.DefaultNotifyCenter;
import io.github.photowey.spring.infras.bean.notify.NotifyCenter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * {@code SpringInfrasAutoConfigure}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/04/26
 */
@Configuration
@EnableInfrasComponents
@Import(value = {
        SpringInfrasAutoConfigure.SpringInfrasConfigure.class,
        TaskExecutorConfigure.class,
})
public class SpringInfrasAutoConfigure {

    @Configuration
    static class SpringInfrasConfigure {

        @Bean
        @ConditionalOnMissingBean(NotifyCenter.class)
        public NotifyCenter notifyCenter() {
            return new DefaultNotifyCenter();
        }
    }
}
