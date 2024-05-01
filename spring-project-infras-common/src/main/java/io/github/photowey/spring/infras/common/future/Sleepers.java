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

import java.util.concurrent.TimeUnit;

/**
 * {@code Sleepers}
 *
 * @author photowey
 * @version 1.3.0
 * @since 2024/04/28
 */
public final class Sleepers {

    private Sleepers() {
        AssertionErrorThrower.throwz(Sleepers.class);
    }

    public static void sleep(long expected, TimeUnit unit) {
        sleep(unit.toMillis(expected));
    }

    public static void sleep(long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}