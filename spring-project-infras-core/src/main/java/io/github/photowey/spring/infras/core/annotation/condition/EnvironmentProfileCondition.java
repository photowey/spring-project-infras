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
package io.github.photowey.spring.infras.core.annotation.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@code EnvironmentProfileCondition}
 *
 * @author photowey
 * @version 1.4.0
 * @since 2024/05/15
 */
class EnvironmentProfileCondition implements Condition {

    private static final String VALUE = "value";
    private static final String ANNOTATION_SUPPORTS = EnvironmentProfile.class.getName();

    private final ConcurrentHashMap<String, Boolean> ctx = new ConcurrentHashMap<>();

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment env = context.getEnvironment();
        String expression = (String) metadata.getAnnotationAttributes(ANNOTATION_SUPPORTS).get(VALUE);

        String profiles = env.resolvePlaceholders(expression);
        String cacheKey = this.populateCacheKey(metadata, profiles);

        return this.ctx.computeIfAbsent(cacheKey, x -> this.evaluateCondition(context, profiles));
    }

    private String populateCacheKey(AnnotatedTypeMetadata metadata, String profiles) {
        String className = this.tryAcquireClassName(metadata);
        return className + ":" + profiles;
    }

    private boolean evaluateCondition(ConditionContext context, String profiles) {
        Environment env = context.getEnvironment();
        boolean negate = false;
        if (profiles.startsWith("!")) {
            negate = true;
            profiles = profiles.substring(1);
        }

        Set<String> requiredProfiles = new HashSet<>(Arrays.asList(profiles.split(",")));
        Set<String> activeProfiles = new HashSet<>(Arrays.asList(env.getActiveProfiles()));

        boolean matches = !requiredProfiles.isEmpty() && requiredProfiles.stream().anyMatch(activeProfiles::contains);

        return negate != matches;
    }

    private String tryAcquireClassName(AnnotatedTypeMetadata metadata) {
        if (metadata instanceof AnnotationMetadata) {
            return ((AnnotationMetadata) metadata).getClassName();
        }

        throw new UnsupportedOperationException("Unsupported metadata:[" + metadata.getClass().getName() + "]");
    }
}
