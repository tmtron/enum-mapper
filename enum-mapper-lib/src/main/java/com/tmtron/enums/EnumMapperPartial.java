/*
 * Copyright © 2018 Martin Trummer (martin.trummer@tmtron.com)
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

/**
 * {@link EnumMapperPartial} allows to associate enum-constants with values.
 * <p> In comparison to {@link EnumMapperFull} it will not check if all enumerations constants are mapped.
 * </p>
 * <p>
 * Note: this class does not allow for {@code null} values. You may use {@link java.util.Optional} as type.
 * </p>
 *
 * @param <K> the enum-type
 * @param <V> the type of the values that you want to associate
 */
public class EnumMapperPartial<K extends Enum<K>, V> extends EnumMapperBase<K, V> {

    private EnumMapperPartial(@Nonnull EnumMap<K, V> enumMap) {
        super(enumMap);
    }

    @SuppressWarnings("unchecked")
    private static <K extends Enum<K>, V> EnumMapperPartial<K, V> ofArgs(Object... varArgs) {

        K key1 = (K) varArgs[0];
        Builder<K, V> builder = new Builder<>(key1);
        for (int i = 0; i < varArgs.length; i = i + 2) {
            K key = (K) varArgs[i];
            V val = (V) varArgs[i + 1];
            builder.put(key, val);
        }

        return builder.build();
    }

    public static @Nonnull
    <K extends Enum<K>, V> EnumMapperPartial<K, V> of(@Nonnull K key1, @Nonnull V val1
            , @Nonnull K key2, @Nonnull V val2) {
        return EnumMapperPartial.ofArgs(key1, val1
                , key2, val2
        );
    }

    public static @Nonnull
    <K extends Enum<K>, V> EnumMapperPartial<K, V> of(@Nonnull K key1, @Nonnull V val1
            , @Nonnull K key2, @Nonnull V val2
            , @Nonnull K key3, @Nonnull V val3) {
        return EnumMapperPartial.ofArgs(key1, val1
                , key2, val2
                , key3, val3
        );
    }

    public static @Nonnull
    <K extends Enum<K>, V> EnumMapperPartial<K, V> of(@Nonnull K key1, @Nonnull V val1
            , @Nonnull K key2, @Nonnull V val2
            , @Nonnull K key3, @Nonnull V val3
            , @Nonnull K key4, @Nonnull V val4) {
        return EnumMapperPartial.ofArgs(key1, val1
                , key2, val2
                , key3, val3
                , key4, val4
        );
    }

    public static @Nonnull
    <K extends Enum<K>, V> EnumMapperPartial<K, V> of(@Nonnull K key1, @Nonnull V val1
            , @Nonnull K key2, @Nonnull V val2
            , @Nonnull K key3, @Nonnull V val3
            , @Nonnull K key4, @Nonnull V val4
            , @Nonnull K key5, @Nonnull V val5) {
        return EnumMapperPartial.ofArgs(key1, val1
                , key2, val2
                , key3, val3
                , key4, val4
                , key5, val5
        );
    }

    public static @Nonnull
    <K extends Enum<K>, V> EnumMapperPartial<K, V> of(@Nonnull K key1, @Nonnull V val1
            , @Nonnull K key2, @Nonnull V val2
            , @Nonnull K key3, @Nonnull V val3
            , @Nonnull K key4, @Nonnull V val4
            , @Nonnull K key5, @Nonnull V val5
            , @Nonnull K key6, @Nonnull V val6
    ) {
        return EnumMapperPartial.ofArgs(key1, val1
                , key2, val2
                , key3, val3
                , key4, val4
                , key5, val5
                , key6, val6
        );
    }

    public static @Nonnull
    <K extends Enum<K>, V> EnumMapperPartial<K, V> of(@Nonnull K key1, @Nonnull V val1
            , @Nonnull K key2, @Nonnull V val2
            , @Nonnull K key3, @Nonnull V val3
            , @Nonnull K key4, @Nonnull V val4
            , @Nonnull K key5, @Nonnull V val5
            , @Nonnull K key6, @Nonnull V val6
            , @Nonnull K key7, @Nonnull V val7) {
        return EnumMapperPartial.ofArgs(key1, val1
                , key2, val2
                , key3, val3
                , key4, val4
                , key5, val5
                , key6, val6
                , key7, val7
        );
    }

    public static @Nonnull
    <K extends Enum<K>, V> EnumMapperPartial<K, V> of(@Nonnull K key1, @Nonnull V val1
            , @Nonnull K key2, @Nonnull V val2
            , @Nonnull K key3, @Nonnull V val3
            , @Nonnull K key4, @Nonnull V val4
            , @Nonnull K key5, @Nonnull V val5
            , @Nonnull K key6, @Nonnull V val6
            , @Nonnull K key7, @Nonnull V val7
            , @Nonnull K key8, @Nonnull V val8) {
        return EnumMapperPartial.ofArgs(key1, val1
                , key2, val2
                , key3, val3
                , key4, val4
                , key5, val5
                , key6, val6
                , key7, val7
                , key8, val8
        );
    }

    public static @Nonnull
    <K extends Enum<K>, V> EnumMapperPartial<K, V> of(@Nonnull K key1, @Nonnull V val1
            , @Nonnull K key2, @Nonnull V val2
            , @Nonnull K key3, @Nonnull V val3
            , @Nonnull K key4, @Nonnull V val4
            , @Nonnull K key5, @Nonnull V val5
            , @Nonnull K key6, @Nonnull V val6
            , @Nonnull K key7, @Nonnull V val7
            , @Nonnull K key8, @Nonnull V val8
            , @Nonnull K key9, @Nonnull V val9) {
        return EnumMapperPartial.ofArgs(key1, val1
                , key2, val2
                , key3, val3
                , key4, val4
                , key5, val5
                , key6, val6
                , key7, val7
                , key8, val8
                , key9, val9);
    }

    /**
     * Returns a builder for the given enumClass.
     * <p>
     * You should prefer the {@code of} methods or {@link #builder(Enum, Object)}
     * </p>
     *
     * @param enumClass the enum-class (of the K type-parameter)
     * @param <K>       key type (Enum)
     * @param <V>       value type
     * @return a builder for the given enumClass.
     */
    public static @Nonnull
    <K extends Enum<K>, V> Builder<K, V> builder(@Nonnull Class<K> enumClass) {
        return new Builder<>(enumClass);
    }

    /**
     * Returns a builder and already adds the first key-value pair
     * <p>
     * You should prefer the {@code of} methods.
     * </p>
     *
     * @param key1 first key
     * @param val1 first value
     * @param <K>  key type (Enum)
     * @param <V>  value type
     * @return a builder including the first key-value pair
     */
    public static @Nonnull
    <K extends Enum<K>, V> Builder<K, V> builder(@Nonnull K key1, @Nonnull V val1) {
        return new Builder<K, V>(key1).put(key1, val1);
    }

    /**
     * @param enumValue the enum value
     * @return the value to which the specified key is mapped, or {@code null} if this map contains no mapping for the
     * key.
     */
    public @Nullable
    V getValueOrNull(@Nonnull K enumValue) {
        return getValueOrNullInternal(enumValue);
    }

    /**
     * @param enumValue enum-key
     * @return the value to which the specified key is mapped
     * @throws IllegalArgumentException when there is no mapping for the enum
     */
    public @Nonnull
    V getValueOrRaise(@Nonnull K enumValue) {
        checkNonnull(enumValue, "enumValue must not be null");
        V result = getValueOrNullInternal(enumValue);
        if (result != null) return result;
        throw new IllegalArgumentException("no mapping found for enum: " + enumValue.toString());
    }

    /**
     * @param enumValue the enum value
     * @param defValue  the default value when there is no mapping
     * @return the value to which the specified key is mapped or the default
     */
    public @Nonnull
    V getValueOrDefault(@Nonnull K enumValue, @Nonnull V defValue) {
        checkNonnull(enumValue, "enum value must not be null");
        checkNonnull(defValue, "default value must not be null");
        V result = getValueOrNull(enumValue);
        if (result == null) return defValue;
        return result;
    }

    public static class Builder<K extends Enum<K>, V> extends BuilderBase<K, V> {

        private Builder(@Nonnull K key) {
            super(checkNonnull(key, "key must not be null"));
        }

        private Builder(@Nonnull Class<K> enumClass) {
            super(enumClass);
        }

        @Override
        public Builder<K, V> put(@Nonnull K key, @Nonnull V val) {
            super.put(key, val);
            return this;
        }

        /**
         * @return an enum mapper.
         * @throws IllegalArgumentException when you did not call put for all possible Enum constants
         */
        public EnumMapperPartial<K, V> build() {
            return new EnumMapperPartial<>(enumMap);
        }
    }

}
