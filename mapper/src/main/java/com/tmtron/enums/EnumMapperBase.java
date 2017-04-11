/*
 * Copyright © 2017 Martin Trummer (martin.trummer@tmtron.com)
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
package com.tmtron.enums;

import java.util.EnumMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EnumMapperBase<K extends Enum<K>, V> {

    private final EnumMap<K, V> enumMap;

    protected EnumMapperBase(@Nonnull EnumMap<K, V> enumMap) {
        this.enumMap = checkNonnull(enumMap, "enumMap must not be null");
    }

    /**
     * Returns the enum-key which is mapped to the given value or {@code defEnum} if this map contains no mapping for
     * the value.
     */
    public @Nonnull
    K getEnumOrDefault(@Nonnull V value, @Nonnull K defEnum) {
        checkNonnull(value, "value must not be null");
        checkNonnull(defEnum, "default enum must not be null");
        K result = getEnumOrNull(value);
        if (result != null) return result;
        return defEnum;
    }

    /**
     * Returns the enum-key which is mapped to the given value or throws an exception when this map contains no mapping
     * for the value.
     *
     * @throws IllegalArgumentException
     */
    public @Nonnull
    K getEnumOrRaise(@Nonnull V value) {
        checkNonnull(value, "value must not be null");
        K result = getEnumOrNull(value);
        if (result != null) return result;
        throw new IllegalArgumentException("no mapping found for value: " + value.toString());
    }

    /**
     * Returns the enum-key which is mapped to the given value or {@code null} when this map contains no mapping for the
     * value.
     */
    @Nullable
    public K getEnumOrNull(@Nonnull V value) {
        checkNonnull(value, "value must not be null");
        for (K key : enumMap.keySet()) {
            V lookupValue = enumMap.get(key);
            if (value.equals(lookupValue)) {
                return key;
            }
        }
        return null;
    }

    public static <T> T checkNonnull(@Nullable T obj, @Nonnull String msg) {
        if (obj == null) throw new IllegalArgumentException(msg);
        return obj;
    }

    /**
     * Returns the number of key-value mappings.
     *
     * @return the number of key-value mappings
     */
    public int size() {
        return enumMap.size();
    }

    /**
     * Returns the value to which the specified key is mapped or {@code null}
     */
    protected @Nullable
    V getValueOrNullInternal(@Nonnull K enumValue) {
        checkNonnull(enumValue, "enum value must not be null");
        return enumMap.get(enumValue);
    }

    protected static class BuilderBase<K extends Enum<K>, V> {
        protected final Class<K> enumClass;
        protected final EnumMap<K, V> enumMap;

        protected BuilderBase(@Nonnull K key) {
            this(checkNonnull(key, "key must not be null").getDeclaringClass());
        }

        protected BuilderBase(@Nonnull Class<K> enumClass) {
            checkNonnull(enumClass, "enum class must not be null");
            this.enumClass = enumClass;
            this.enumMap = new EnumMap<>(enumClass);
        }

        /**
         * Associates the specified value with the specified key.
         * If the builder previously contained a mapping for this key, the old value is replaced.
         *
         * @param key the key with which the specified value is to be associated
         * @param val the value to be associated with the specified key
         * @return the builder for method chaining
         */

        public BuilderBase<K, V> put(@Nonnull K key, @Nonnull V val) {
            checkNonnull(key, "key must not be null");
            checkNonnull(val, "value must not be null");
            enumMap.put(key, val);
            return this;
        }

    }

}