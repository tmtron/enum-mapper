package com.tmtron.enums;

// comment

@MapAllEnums(enums = {TwoEnums_Source.BoolEnum.class, TwoEnums_Source.ColorEnum.class})
public class TwoEnums_Source {
    enum BoolEnum {OFF, ON}

    enum ColorEnum {RED, BLUE}
}