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
package io.github.photowey.spring.infras.common.future;

import io.github.photowey.spring.infras.common.thrower.AssertionErrorThrower;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;

/**
 * {@code Futures}
 *
 * @author photowey
 * @version 1.3.0
 * @since 2024/04/28
 */
public final class Futures {

    private Futures() {
        AssertionErrorThrower.throwz(Futures.class);
    }

    public static <T> T get(Future<T> future) {
        return get(future, (e) -> {
            throw new RuntimeException(e);
        });
    }

    public static <T> T get(Future<T> future, Function<Exception, T> fx) {
        try {
            return future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return fx.apply(e);
        } catch (ExecutionException e) {
            return fx.apply(e);
        }
    }
}
