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

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * {@code ConditionalOnSpEL}
 * |- Alias for {@code ConditionalOnProfile}
 * <p>
 * Examples:
 * <pre>
 *  public interface GithubAccessor {}
 *
 * {@literal @}Profile("dev")
 *  public class DevelopGithubAccessor implements GithubAccessor {}
 *
 * {@literal @}Profile("test")
 *  public class TestGithubAccessor implements GithubAccessor {}
 *
 * {@literal @}Profile("prod")
 *  public class DefaultGithubAccessor implements GithubAccessor {}
 *
 * {@literal @}ConditionalOnSpEL("${io.github.photowey.github.accessor.mock.profiles}")
 *  public class MockGithubAccessor implements GithubAccessor {}
 *
 * {@literal @}ConditionalOnSpEL("!${io.github.photowey.github.accessor.mock.profiles}")
 *  public class NonMockGithubAccessor implements GithubAccessor {}
 * </pre>
 *
 * @author photowey
 * @version 1.5.0
 * @since 2024/05/16
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@ConditionalOnProfile
public @interface ConditionalOnSpEL {

    /**
     * The set of profiles for which the annotated component should be registered.
     * Multiple profiles can be specified and separated by commas.
     */
    @AliasFor(annotation = ConditionalOnProfile.class, attribute = "value")
    String value() default "dev";
}
