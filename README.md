
[![Build Status](https://travis-ci.org/tmtron/enum-mapper.svg?label=travis)](https://travis-ci.org/tmtron/enum-mapper/builds)
[![license](https://img.shields.io/github/license/tmtron/green-annotations.svg?maxAge=2592000)](https://raw.githubusercontent.com/tmtron/green-annotations/develop/LICENSE)

# enum-mapper
The main use is an annotation processor that builds an enum mapper which causes a compile-time error when you forget to map an enum.

## Simple Example
Just annotate your enum with the `@EnumMapper` annotation:
```java
@EnumMapper
public enum Seasons {
  SPRING, SUMMER, FALL, WINTER
}
```
When you build your project, the annotation processor will generate a `Seasons_MapperFull`, which can be used to map all enum constants to arbitrary values.
Here is an example use where we map each enum constant to a string. 
```java
EnumMapperFull<Seasons, String> germanSeasons = Seasons_MapperFull
     .setSPRING("Fruehling")
     .setSUMMER("Sommer")
     .setFALL("Herbst")
     .setWINTER("Winter");

String germanSummer = germanSeasons.getValue(Seasons.SUMMER); // returns "Sommer"
```
Should you forget to map any enum-constant or change the enumeration (e.g. add or remove an enum-constant), you will get a compile error.

## Build configuration
TODO: add info when the project is published

## License
This plugin is under the [Apache 2.0 license](http://www.apache.org/licenses/LICENSE-2.0.html). Copyright 2017, Martin Trummer
