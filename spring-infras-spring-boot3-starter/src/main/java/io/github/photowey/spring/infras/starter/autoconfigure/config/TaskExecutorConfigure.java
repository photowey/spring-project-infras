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

import io.github.photowey.spring.infras.bean.notify.NotifyCenter;
import io.github.photowey.spring.infras.starter.autoconfigure.property.SpringInfrasProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * {@code TaskExecutorConfigure}
 *
 * @author photowey
 * @version 1.5.0
 * @since 2024/05/17
 */
@Configuration
public class TaskExecutorConfigure {

    @Bean(NotifyCenter.NOTIFY_EXECUTOR_BEAN_NAME)
    @ConditionalOnMissingBean(name = NotifyCenter.NOTIFY_EXECUTOR_BEAN_NAME)
    public ThreadPoolTaskExecutor notifyAsyncExecutor(SpringInfrasProperties props) {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(props.threadPool().coreSize());
        taskExecutor.setMaxPoolSize(props.threadPool().maxSize());
        taskExecutor.setQueueCapacity(props.threadPool().queueCapacity());

        taskExecutor.setKeepAliveSeconds(props.threadPool().keepAliveSeconds());
        taskExecutor.setThreadGroupName(props.threadPool().threadGroup());
        taskExecutor.setThreadNamePrefix(props.threadPool().threadNamePrefix());

        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.setAllowCoreThreadTimeOut(true);
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);

        // taskExecutor.initialize();

        return taskExecutor;
    }
}
