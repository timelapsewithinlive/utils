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

/**
 * A {@linkplain Runnable} which may throw.
 */
@FunctionalInterface
public interface CheckedRunnable {

    static CheckedRunnable of(CheckedRunnable methodReference) {
        return methodReference;
    }

    void run() throws Throwable;

    default Runnable unchecked() {
        return () -> {
            try {
                run();
            } catch(Throwable x) {
                CheckedRunnableModule.sneakyThrow(x);
            }
        };
    }
}

interface CheckedRunnableModule {

    @SuppressWarnings("unchecked")
    static <T extends Throwable, R> R sneakyThrow(Throwable t) throws T {
        throw (T) t;
    }

}
