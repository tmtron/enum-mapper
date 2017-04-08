package com.test;

// comment

import com.tmtron.enums.MapAllEnums;

@MapAllEnums(enums = {TwoEnums_Source.BoolEnum.class, TwoEnums_Source.ColorEnum.class})
public class TwoEnums_Source {
    enum BoolEnum {OFF, ON}

    enum ColorEnum {RED, BLUE}
}