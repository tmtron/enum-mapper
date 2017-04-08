package com.tmtron.enums;

public final class LauncherAge_MapperFull {
    public static <V> IsetOLD<V> setONLINE(V value) {
        StagedBuilder<V> result = new StagedBuilder<>();
        result.enumMapperBuilder.put(TestLauncherAge.LauncherAge.ONLINE, value);
        return result;
    }

    public interface IsetOFFLINE<V> {
        EnumMapper<TestLauncherAge.LauncherAge, V> setOFFLINE(V value);
    }

    public interface IsetOLD<V> {
        IsetOFFLINE<V> setOLD(V value);
    }

    private static class StagedBuilder<V> implements IsetOLD<V>, IsetOFFLINE<V> {
        private final EnumMapper.Builder<TestLauncherAge.LauncherAge, V> enumMapperBuilder = EnumMapper.builder
                (TestLauncherAge.LauncherAge.class);

        @Override
        public IsetOFFLINE<V> setOLD(V value) {
            enumMapperBuilder.put(TestLauncherAge.LauncherAge.OLD, value);
            return this;
        }

        @Override
        public EnumMapper<TestLauncherAge.LauncherAge, V> setOFFLINE(V value) {
            enumMapperBuilder.put(TestLauncherAge.LauncherAge.OFFLINE, value);
            return enumMapperBuilder.build();
        }
    }
}