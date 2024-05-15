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
import org.springframework.core.env.Profiles;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@code EnvironmentProfileCondition}
 *
 * @author photowey
 * @version 1.4.0
 * @since 2024/05/15
 */
class EnvironmentProfileCondition implements Condition {

    private static final String VALUE = "value";

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        MultiValueMap<String, Object> attrs = metadata.getAllAnnotationAttributes(EnvironmentProfile.class.getName());
        if (attrs != null) {
            for (Object expectProfiles : attrs.get(VALUE)) {
                String[] parsedProfiles = this.parseProfiles(context, expectProfiles);
                if (this.acceptsProfiles(context, parsedProfiles)) {
                    return true;
                }
            }

            return false;
        }

        return true;
    }

    private boolean acceptsProfiles(ConditionContext context, String[] parsedProfiles) {
        return context.getEnvironment().acceptsProfiles(Profiles.of(parsedProfiles));
    }

    private String[] parseProfiles(ConditionContext context, Object profiles) {
        String[] expectProfiles = (String[]) profiles;
        Environment environment = context.getEnvironment();

        List<String> parsedProfiles = new ArrayList<>();

        for (String profile : expectProfiles) {
            List<String> parsed = Stream.of(environment.resolvePlaceholders(profile).split(",")).collect(Collectors.toList());
            parsedProfiles.addAll(parsed);
        }

        return parsedProfiles.toArray(new String[0]);
    }
}
