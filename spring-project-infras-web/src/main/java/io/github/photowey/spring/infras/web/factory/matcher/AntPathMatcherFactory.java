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
package io.github.photowey.spring.infras.web.factory.matcher;

import io.github.photowey.spring.infras.core.thrower.AssertionErrorThrower;
import org.springframework.util.AntPathMatcher;

/**
 * {@code AntPathMatcherFactory}
 *
 * @author photowey
 * @version 1.1.0
 * @since 2024/04/26
 */
public final class AntPathMatcherFactory {

    private AntPathMatcherFactory() {
        AssertionErrorThrower.throwz(AntPathMatcherFactory.class);
    }

    public static AntPathMatcher create() {
        return create(false, false);
    }

    public static AntPathMatcher create(boolean caseSensitive) {
        return create(caseSensitive, false);
    }

    public static AntPathMatcher create(boolean caseSensitive, boolean trimTokens) {
        AntPathMatcher matcher = new AntPathMatcher();
        matcher.setCaseSensitive(caseSensitive);
        matcher.setTrimTokens(trimTokens);

        return matcher;
    }
}
