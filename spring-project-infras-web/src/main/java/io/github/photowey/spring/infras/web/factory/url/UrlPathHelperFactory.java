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
package io.github.photowey.spring.infras.web.factory.url;

import io.github.photowey.spring.infras.core.thrower.AssertionErrorThrower;
import org.springframework.web.util.UrlPathHelper;

/**
 * {@code UrlPathHelperFactory}
 *
 * @author photowey
 * @version 1.1.0
 * @since 2024/04/26
 */
public final class UrlPathHelperFactory {

    private UrlPathHelperFactory() {
        AssertionErrorThrower.throwz(UrlPathHelperFactory.class);
    }

    public static UrlPathHelper create() {
        return new UrlPathHelper();
    }
}