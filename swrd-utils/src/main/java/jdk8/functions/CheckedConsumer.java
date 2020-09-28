/* ____  ______________  ________________________  __________
 * \   \/   /      \   \/   /   __/   /      \   \/   /      \
 *  \______/___/\___\______/___/_____/___/\___\______/___/\___\
 *
 * Copyright 2020 Vavr, http://vavr.io
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
package jdk8.functions;

import java.util.Objects;
import java.util.function.Consumer;

@FunctionalInterface
public interface CheckedConsumer<T> {

    static <T> CheckedConsumer<T> of(CheckedConsumer<T> methodReference) {
        return methodReference;
    }

    void accept(T t) throws Throwable;

    default CheckedConsumer<T> andThen(CheckedConsumer<? super T> after) {
        Objects.requireNonNull(after, "after is null");
        return (T t) -> {
            accept(t);
            after.accept(t);
        };
    }

    default Consumer<T> unchecked() {
        return t -> {
            try {
                accept(t);
            } catch(Throwable x) {
                CheckedConsumerModule.sneakyThrow(x);
            }
        };
    }
}

interface CheckedConsumerModule {

    @SuppressWarnings("unchecked")
    static <T extends Throwable, R> R sneakyThrow(Throwable t) throws T {
        throw (T) t;
    }

}
