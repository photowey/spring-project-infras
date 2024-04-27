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
import io.github.photowey.spring.infras.starter.autoconfigure.property.SpringInfrasProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * {@code SpringInfrasConfigure}
 *
 * @author photowey
 * @version 1.2.0
 * @since 2024/04/26
 */
@Configuration
@Import(value = {
        TaskExecutorConfigure.class,
        SpringInfrasConfigure.SpringInfrasComponentConfigure.class,
})
@ConditionalOnMissingClass("org.springframework.boot.autoconfigure.AutoConfiguration")
public class SpringInfrasConfigure implements SmartInitializingSingleton {

    private static final Logger log = LoggerFactory.getLogger(SpringInfrasConfigure.class);

    @Override
    public void afterSingletonsInstantiated() {
        log.info("infras: auto.configure.in.mode: [spring.factories]");
    }

    @Configuration
    @EnableInfrasComponents
    @EnableConfigurationProperties(SpringInfrasProperties.class)
    static class SpringInfrasComponentConfigure {}
}
