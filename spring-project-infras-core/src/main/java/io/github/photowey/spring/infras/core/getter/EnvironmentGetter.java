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
package io.github.photowey.spring.infras.core.getter;

import org.springframework.core.env.Environment;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

/**
 * {@code EnvironmentGetter}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/04/26
 */
public interface EnvironmentGetter {

    String SPRING_PROFILES_ACTIVE_CONFIG_KEY = "spring.profiles.active";

    String APPLICATION_SERVER_PORT_CONFIG_KEY = "server.port";
    String APPLICATION_SERVER_SERVLET_CONTEXT_PATH_CONFIG_KEY = "server.servlet.context-path";
    String APPLICATION_SERVER_SERVLET_CONTEXT_PATH_ROOT = "/";

    String SPRING_PROFILES_ACTIVE_DEV = "dev";
    String SPRING_PROFILES_ACTIVE_TEST = "test";

    String SPRING_PROFILES_ACTIVE_PRO = "pro";
    String SPRING_PROFILES_ACTIVE_PROD = "prod";

    /**
     * GET {@link Environment}
     *
     * @return {@link Environment}
     */
    default Environment environment() {
        return null;
    }

    default String determineProfilesActive() {
        return Objects.requireNonNull(this.environment()).getProperty(SPRING_PROFILES_ACTIVE_CONFIG_KEY);
    }

    default String determineServerServletContextPath() {
        return Objects.requireNonNull(this.environment()).getProperty(APPLICATION_SERVER_SERVLET_CONTEXT_PATH_CONFIG_KEY, APPLICATION_SERVER_SERVLET_CONTEXT_PATH_ROOT);
    }

    default boolean determineDeveloping(String profiles) {
        if (ObjectUtils.isEmpty(profiles)) {
            return false;
        }

        return profiles.contains(SPRING_PROFILES_ACTIVE_DEV) || profiles.contains(SPRING_PROFILES_ACTIVE_TEST);
    }

    default int determineServerPort() {
        return Integer.parseInt(Objects.requireNonNull(this.environment()).getProperty(APPLICATION_SERVER_PORT_CONFIG_KEY, "8080"));
    }
}
