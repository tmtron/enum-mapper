package com.tmtron.enums;

import java.util.EnumMap;

import javax.annotation.Nullable;

public class EnumMapper<K extends Enum<K>, V> {

    private final EnumMap<K, V> enumMap;

    public EnumMapper(EnumMap<K, V> enumMap) {
        this.enumMap = enumMap;
    }

    @SuppressWarnings("unchecked")
    private static <K extends Enum<K>, V> EnumMapper<K, V> ofArgs(boolean partial, Object... varArgs) {

        K key1 = (K) varArgs[0];
        Builder<K, V> builder = new Builder<>(key1);
        for (int i = 0; i < varArgs.length; i = i + 2) {
            K key = (K) varArgs[i];
            V val = (V) varArgs[i + 1];
            builder.put(key, val);
        }

        if (partial) {
            return builder.buildPartial();
        } else {
            return builder.build();
        }
    }

    public static <K extends Enum<K>, V> EnumMapper<K, V> of(K key1, V val1, K key2, V val2) {
        return EnumMapper.ofArgs(false, key1, val1
                , key2, val2
        );
    }

    public static <K extends Enum<K>, V> EnumMapper<K, V> of(K key1, V val1, K key2, V val2
            , K key3, V val3) {
        return EnumMapper.ofArgs(false, key1, val1
                , key2, val2
                , key3, val3
        );
    }

    public static <K extends Enum<K>, V> EnumMapper<K, V> of(K key1, V val1, K key2, V val2
            , K key3, V val3, K key4, V val4) {
        return EnumMapper.ofArgs(false, key1, val1
                , key2, val2
                , key3, val3
                , key4, val4
        );
    }

    public static <K extends Enum<K>, V> EnumMapper<K, V> of(K key1, V val1, K key2, V val2
            , K key3, V val3, K key4, V val4, K key5, V val5) {
        return EnumMapper.ofArgs(false, key1, val1
                , key2, val2
                , key3, val3
                , key4, val4
                , key5, val5
        );
    }

    public static <K extends Enum<K>, V> EnumMapper<K, V> of(K key1, V val1, K key2, V val2
            , K key3, V val3, K key4, V val4, K key5, V val5, K key6, V val6) {
        return EnumMapper.ofArgs(false, key1, val1
                , key2, val2
                , key3, val3
                , key4, val4
                , key5, val5
                , key6, val6
        );
    }

    public static <K extends Enum<K>, V> EnumMapper<K, V> of(K key1, V val1, K key2, V val2
            , K key3, V val3, K key4, V val4, K key5, V val5, K key6, V val6
            , K key7, V val7) {
        return EnumMapper.ofArgs(false, key1, val1
                , key2, val2
                , key3, val3
                , key4, val4
                , key5, val5
                , key6, val6
                , key7, val7
        );
    }

    public static <K extends Enum<K>, V> EnumMapper<K, V> of(K key1, V val1, K key2, V val2
            , K key3, V val3, K key4, V val4, K key5, V val5, K key6, V val6
            , K key7, V val7, K key8, V val8) {
        return EnumMapper.ofArgs(false, key1, val1
                , key2, val2
                , key3, val3
                , key4, val4
                , key5, val5
                , key6, val6
                , key7, val7
                , key8, val8
        );
    }

    public static <K extends Enum<K>, V> EnumMapper<K, V> of(K key1, V val1, K key2, V val2
            , K key3, V val3, K key4, V val4, K key5, V val5, K key6, V val6
            , K key7, V val7, K key8, V val8, K key9, V val9) {
        return EnumMapper.ofArgs(false, key1, val1
                , key2, val2
                , key3, val3
                , key4, val4
                , key5, val5
                , key6, val6
                , key7, val7
                , key8, val8
                , key9, val9);
    }

    public static <K extends Enum<K>, V> EnumMapper<K, V> ofPartial(K key1, V val1, K key2, V val2) {
        return EnumMapper.ofArgs(true, key1, val1
                , key2, val2
        );
    }

    public static <K extends Enum<K>, V> EnumMapper<K, V> ofPartial(K key1, V val1, K key2, V val2
            , K key3, V val3) {
        return EnumMapper.ofArgs(true, key1, val1
                , key2, val2
                , key3, val3
        );
    }

    public static <K extends Enum<K>, V> EnumMapper<K, V> ofPartial(K key1, V val1, K key2, V val2
            , K key3, V val3, K key4, V val4) {
        return EnumMapper.ofArgs(true, key1, val1
                , key2, val2
                , key3, val3
                , key4, val4
        );
    }

    public static <K extends Enum<K>, V> EnumMapper<K, V> ofPartial(K key1, V val1, K key2, V val2
            , K key3, V val3, K key4, V val4, K key5, V val5) {
        return EnumMapper.ofArgs(true, key1, val1
                , key2, val2
                , key3, val3
                , key4, val4
                , key5, val5
        );
    }

    public static <K extends Enum<K>, V> EnumMapper<K, V> ofPartial(K key1, V val1, K key2, V val2
            , K key3, V val3, K key4, V val4, K key5, V val5, K key6, V val6) {
        return EnumMapper.ofArgs(true, key1, val1
                , key2, val2
                , key3, val3
                , key4, val4
                , key5, val5
                , key6, val6
        );
    }

    public static <K extends Enum<K>, V> EnumMapper<K, V> ofPartial(K key1, V val1, K key2, V val2
            , K key3, V val3, K key4, V val4, K key5, V val5, K key6, V val6
            , K key7, V val7) {
        return EnumMapper.ofArgs(true, key1, val1
                , key2, val2
                , key3, val3
                , key4, val4
                , key5, val5
                , key6, val6
                , key7, val7
        );
    }

    public static <K extends Enum<K>, V> EnumMapper<K, V> ofPartial(K key1, V val1, K key2, V val2
            , K key3, V val3, K key4, V val4, K key5, V val5, K key6, V val6
            , K key7, V val7, K key8, V val8) {
        return EnumMapper.ofArgs(true, key1, val1
                , key2, val2
                , key3, val3
                , key4, val4
                , key5, val5
                , key6, val6
                , key7, val7
                , key8, val8
        );
    }

    public static <K extends Enum<K>, V> EnumMapper<K, V> ofPartial(K key1, V val1, K key2, V val2
            , K key3, V val3, K key4, V val4, K key5, V val5, K key6, V val6
            , K key7, V val7, K key8, V val8, K key9, V val9) {
        return EnumMapper.ofArgs(true, key1, val1
                , key2, val2
                , key3, val3
                , key4, val4
                , key5, val5
                , key6, val6
                , key7, val7
                , key8, val8
                , key9, val9);
    }

    public static <K extends Enum<K>, V> Builder<K, V> builder(Class<K> enumClass) {
        return new Builder<>(enumClass);
    }

    /**
     * Returns enum-key which is mapped to the given value or {@code defEnum} if this map contains no mapping for
     * the key.
     */
    public K getEnumOrDefault(V value, K defEnum) {
        K result = getEnum(value);
        if (result != null) return result;
        return defEnum;
    }

    /**
     * Returns enum-key which is mapped to the given value or {@code null} if this map contains no mapping for the key.
     */
    @Nullable
    public K getEnum(V value) {
        for (K key : enumMap.keySet()) {
            if (value.equals(getValue(key))) {
                return key;
            }
        }
        return null;
    }

    /**
     * returns a builder and already adds the first key-value pair
     *
     * @param key1 first key
     * @param val1 first value
     * @param <K>  key type (Enum)
     * @param <V>  value type
     */
    public static <K extends Enum<K>, V> Builder<K, V> builder(K key1, V val1) {
        return new Builder<K, V>(key1).put(key1, val1);
    }

    /**
     * Returns the value to which the specified key is mapped, or {@code null} if this map contains no mapping for the
     * key.
     */
    public V getValue(K enumValue) {
        return enumMap.get(enumValue);
    }

    /**
     * Returns the number of key-value mappings.
     *
     * @return the number of key-value mappings
     */
    public int size() {
        return enumMap.size();
    }

    public static class Builder<K extends Enum<K>, V> {
        private final Class<K> enumClass;
        private final EnumMap<K, V> enumMap;

        private Builder(K key) {
            this(key.getDeclaringClass());
        }

        private Builder(Class<K> enumClass) {
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
        public Builder<K, V> put(K key, V val) {
            enumMap.put(key, val);
            return this;
        }

        /**
         * Returns an enum mapper.
         *
         * @throws IllegalArgumentException when you did not call put for all possible Enum constants
         */
        public EnumMapper<K, V> build() {
            K[] enumConstants = enumClass.getEnumConstants();
            // check that all possible values exist in the map
            for (Object enumConstant : enumConstants) {
                //noinspection SuspiciousMethodCalls
                if (!enumMap.containsKey(enumConstant)) {
                    throw new IllegalArgumentException("Enum value is not mapped: " + enumConstant.toString());
                }
            }
            return buildPartial();
        }

        /**
         * Returns an enum mapper.
         * In comparison to {@link #build()}, this method will no throw an exception when you did not map all possible
         * Enum constants.
         */
        public EnumMapper<K, V> buildPartial() {
            return new EnumMapper<>(enumMap);
        }
    }
}
