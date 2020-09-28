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

import java.util.function.Predicate;


@FunctionalInterface
public interface CheckedPredicate<T> {


    static <T> CheckedPredicate<T> of(CheckedPredicate<T> methodReference) {
        return methodReference;
    }

    boolean test(T t) throws Throwable;

    default CheckedPredicate<T> negate() {
        return t -> !test(t);
    }

    default Predicate<T> unchecked() {
        return t -> {
            try {
                return test(t);
            } catch(Throwable x) {
                return Throws.sneakyThrow(x);
            }
        };
    }
}
