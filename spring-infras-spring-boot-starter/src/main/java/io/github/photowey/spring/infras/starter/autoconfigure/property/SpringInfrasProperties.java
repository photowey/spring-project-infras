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
package io.github.photowey.spring.infras.starter.autoconfigure.property;

import io.github.photowey.spring.infras.bean.notify.NotifyCenter;
import io.github.photowey.spring.infras.common.hardware.HardwareUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * {@code SpringInfrasProperties}
 *
 * @author photowey
 * @version 1.1.0
 * @since 2024/04/26
 */
@ConfigurationProperties(prefix = "spring.infras")
public class SpringInfrasProperties implements Serializable {

    private static final long serialVersionUID = -9045576339686393939L;

    // ----------------------------------------------------------------

    private ThreadPool threadPool = new ThreadPool();

    // ----------------------------------------------------------------

    public static String getPrefix() {
        return "spring.infras";
    }

    // ----------------------------------------------------------------

    public static class ThreadPool implements Serializable {

        private static final long serialVersionUID = 7085909071925205173L;

        private int coreSize = determineCorePoolSize(HardwareUtils.getNcpu() + 1);
        private int maxSize = determineMaxPoolSize(HardwareUtils.getDoubleNcpu() + 1);
        private int queueCapacity = determineQueueCapacity(1 << 10);
        private int keepAliveSeconds = determineKeepAlive(60);
        private String threadGroup = "async";
        private String threadNamePrefix = "notify-";

        // ----------------------------------------------------------------

        public int getCoreSize() {
            return coreSize;
        }

        public int getMaxSize() {
            return maxSize;
        }

        public int getQueueCapacity() {
            return queueCapacity;
        }

        public int getKeepAliveSeconds() {
            return keepAliveSeconds;
        }

        public String getThreadGroup() {
            return threadGroup;
        }

        public String getThreadNamePrefix() {
            return threadNamePrefix;
        }

        // ----------------------------------------------------------------

        public int coreSize() {
            return coreSize;
        }

        public int maxSize() {
            return maxSize;
        }

        public int queueCapacity() {
            return queueCapacity;
        }

        public int keepAliveSeconds() {
            return keepAliveSeconds;
        }

        public String threadGroup() {
            return threadGroup;
        }

        public String threadNamePrefix() {
            return threadNamePrefix;
        }

        // ----------------------------------------------------------------

        public void setCoreSize(int coreSize) {
            this.coreSize = coreSize;
        }

        public void setMaxSize(int maxSize) {
            this.maxSize = maxSize;
        }

        public void setQueueCapacity(int queueCapacity) {
            this.queueCapacity = queueCapacity;
        }

        public void setKeepAliveSeconds(int keepAliveSeconds) {
            this.keepAliveSeconds = keepAliveSeconds;
        }

        public void setThreadGroup(String threadGroup) {
            this.threadGroup = threadGroup;
        }

        public void setThreadNamePrefix(String threadNamePrefix) {
            this.threadNamePrefix = threadNamePrefix;
        }
    }

    // ----------------------------------------------------------------

    public ThreadPool threadPool() {
        return threadPool;
    }

    // ----------------------------------------------------------------

    public ThreadPool getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(ThreadPool threadPool) {
        this.threadPool = threadPool;
    }

    public static int determineCorePoolSize(int defaultValue) {
        return determineSystemConfigValue(NotifyCenter.NOTIFY_EXECUTOR_CORE_POOL_SIZE_CONFIG_KEY, String.valueOf(defaultValue));
    }

    public static int determineMaxPoolSize(int defaultValue) {
        return determineSystemConfigValue(NotifyCenter.NOTIFY_EXECUTOR_MAX_POOL_SIZE_CONFIG_KEY, String.valueOf(defaultValue));
    }

    public static int determineQueueCapacity(int defaultValue) {
        return determineSystemConfigValue(NotifyCenter.NOTIFY_EXECUTOR_QUEUE_CAPACITY_CONFIG_KEY, String.valueOf(defaultValue));
    }

    public static int determineKeepAlive(int defaultValue) {
        return determineSystemConfigValue(NotifyCenter.NOTIFY_EXECUTOR_KEEP_ALIVE_CONFIG_KEY, String.valueOf(defaultValue));
    }

    public static int determineSystemConfigValue(String configKey, String defaultValue) {
        String configValue = System.getenv(configKey);
        if (StringUtils.hasText(configValue)) {
            // Not check.
            return Integer.parseInt(configValue);
        }

        configValue = System.getProperty(configKey, defaultValue);
        return Integer.parseInt(configValue);
    }
}