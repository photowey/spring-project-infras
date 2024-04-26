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
import io.github.photowey.spring.infras.core.hardware.HardwareUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.StringUtils;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * {@code TaskExecutorConfigure}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/04/26
 */
@Configuration
public class TaskExecutorConfigure {

    @Bean(NotifyCenter.DEFAULT_NOTIFY_EXECUTOR_NAME)
    @ConditionalOnMissingBean(name = NotifyCenter.DEFAULT_NOTIFY_EXECUTOR_NAME)
    public ThreadPoolTaskExecutor notifyAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(this.determineCorePoolSize(HardwareUtils.getNcpu() + 1));
        taskExecutor.setMaxPoolSize(this.determineMaxPoolSize(HardwareUtils.getNcpu() + 1));
        taskExecutor.setQueueCapacity(this.determineQueueCapacity(1 << 10));

        taskExecutor.setKeepAliveSeconds(60);
        taskExecutor.setThreadGroupName("async");
        taskExecutor.setThreadNamePrefix("notify" + "-");

        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.setAllowCoreThreadTimeOut(true);
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);

        // taskExecutor.initialize();

        return taskExecutor;
    }

    private int determineCorePoolSize(int defaultValue) {
        return this.determineSystemConfigValue(NotifyCenter.NOTIFY_EXECUTOR_CORE_POOL_SIZE_CONFIG_KEY, String.valueOf(defaultValue));
    }

    private int determineMaxPoolSize(int defaultValue) {
        return this.determineSystemConfigValue(NotifyCenter.NOTIFY_EXECUTOR_MAX_POOL_SIZE_CONFIG_KEY, String.valueOf(defaultValue));
    }

    private int determineQueueCapacity(int defaultValue) {
        return this.determineSystemConfigValue(NotifyCenter.NOTIFY_EXECUTOR_QUEUE_CAPACITY_CONFIG_KEY, String.valueOf(defaultValue));
    }

    private int determineSystemConfigValue(String configKey, String defaultValue) {
        String configValue = System.getenv(configKey);
        if (StringUtils.hasText(configValue)) {
            // Not check.
            return Integer.parseInt(configValue);
        }

        configValue = System.getProperty(configKey, defaultValue);
        return Integer.parseInt(configValue);
    }
}
