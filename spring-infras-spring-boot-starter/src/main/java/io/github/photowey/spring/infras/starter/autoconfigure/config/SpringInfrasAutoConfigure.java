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

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Import;

/**
 * {@code SpringInfrasAutoConfigure}
 *
 * @author photowey
 * @version 1.1.0
 * @since 2024/04/26
 */
@AutoConfiguration
@Import(value = {
        TaskExecutorConfigure.class,
        AbstractSpringInfrasConfigure.SpringInfrasComponentConfigure.class,
})
@ConditionalOnClass(AutoConfiguration.class)
public class SpringInfrasAutoConfigure extends AbstractSpringInfrasConfigure {

    @Override
    public void afterSingletonsInstantiated() {
        log.info("infras: auto.configure.in.mode: [org.springframework.boot.autoconfigure.AutoConfiguration.imports]");
    }
}
