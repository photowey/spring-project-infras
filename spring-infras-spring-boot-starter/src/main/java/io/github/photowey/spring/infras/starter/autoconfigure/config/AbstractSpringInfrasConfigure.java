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
import io.github.photowey.spring.infras.web.reader.RemoteResourceReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@code AbstractSpringInfrasConfigure}
 *
 * @author photowey
 * @version 1.4.0
 * @since 2024/05/16
 */
public abstract class AbstractSpringInfrasConfigure implements SmartInitializingSingleton {

    protected static final Logger log = LoggerFactory.getLogger(AbstractSpringInfrasConfigure.class);

    @Configuration
    @EnableInfrasComponents
    @EnableConfigurationProperties(SpringInfrasProperties.class)
    static class SpringInfrasComponentConfigure {

        @Bean("io.github.photowey.spring.infras.web.reader.RemoteResourceReader")
        public RemoteResourceReader resourceReader() {
            return new RemoteResourceReader();
        }
    }
}