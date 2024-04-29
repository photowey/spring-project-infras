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
package io.github.photowey.spring.infras.core.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.photowey.spring.infras.common.json.jackson.Jackson;
import io.github.photowey.spring.infras.core.getter.ApplicationContextGetter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;

/**
 * {@code ApplicationContextInjector}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/04/26
 */
public class ApplicationContextInjector implements ApplicationContextAware, ApplicationContextGetter, DisposableBean {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

        this.inject();
    }

    @Override
    public ApplicationContext applicationContext() {
        return this.applicationContext;
    }

    @Override
    public void destroy() throws Exception {
        ApplicationContextHolder.INSTANCE.cleanSharedObjects();
        Jackson.clean();
    }

    private void inject() {
        ApplicationContextHolder.INSTANCE.applicationContext(this.configurableApplicationContext());
        Jackson.injectSharedObjectMapper(this.objectMapper());
    }

    public ObjectMapper objectMapper() {
        return this.applicationContext.getBean(ObjectMapper.class);
    }
}
