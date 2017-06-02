
[![Build Status](https://travis-ci.org/tmtron/enum-mapper.svg?label=travis)](https://travis-ci.org/tmtron/enum-mapper/builds)
[![license](https://img.shields.io/github/license/tmtron/enum-mapper.svg?maxAge=2592000)](https://raw.githubusercontent.com/tmtron/enum-mapper/master/LICENSE)  

# enum-mapper
The main use: make sure that you always handle all available constants of an enum. An annotation processor will
 make sure that you get a compile-time error otherwise: see [Full Enum Mapper](#full-enum-mapper).  
You can also use a [Partial Mapper](#partial-enum-mapper) and it supports [Reverse Mapping](#reverse-mapping).

## Build Configuration

The project is available in [Maven Central](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.tmtron.enums%22) and 
 [JCenter-Bintray](https://bintray.com/tmtron/maven/com.tmtron.enum-mapper) repositories.
  
```gradle
    def VERSION_ENUM_MAPPER = '1.0.2' // check for newer versions here: https://goo.gl/LSP1fv
    compile  "com.tmtron.enums:enum-mapper-lib:${VERSION_ENUM_MAPPER}"
    apt "com.tmtron.enums:enum-mapper-processor:${VERSION_ENUM_MAPPER}"
```
### enum-mapper-lib 
Contains java code and annoations.  
This is always needed at compile-time.  
[![Maven Central lib](https://img.shields.io/maven-central/v/com.tmtron.enums/enum-mapper-lib.svg?maxAge=864000)](https://maven-badges.herokuapp.com/maven-central/com.tmtron.enums/enum-mapper-lib) [![Javadoc](https://javadoc-emblem.rhcloud.com/doc/com.tmtron.enums/enum-mapper-lib/badge.svg?maxAge=864000)](http://www.javadoc.io/doc/com.tmtron.enums/enum-mapper-lib/) 
 
### enum-mapper-processor  
Contains the annotation-processor.  
This is needed by the annotation-processing build-step (`apt`) and it is only required for the [Full Enum Mapper](#full-enum-mapper).  
 [![Maven Central processor](https://img.shields.io/maven-central/v/com.tmtron.enums/enum-mapper-processor.svg?maxAge=864000)](https://maven-badges.herokuapp.com/maven-central/com.tmtron.enums/enum-mapper-processor) [![Javadoc](https://javadoc-emblem.rhcloud.com/doc/com.tmtron.enums/enum-mapper-processor/badge.svg?maxAge=864000)](http://www.javadoc.io/doc/com.tmtron.enums/enum-mapper-processor/) 

## Full Enum Mapper

Just annotate your enum with the `@EnumMapper` annotation:
```java
@EnumMapper
public enum Seasons {
  SPRING, SUMMER, FALL, WINTER
}
```

When you build your project, the annotation processor will generate a java class `Seasons_MapperFull`, 
which can be used to map all enum constants to arbitrary values.

Here is an example use where we map each enum constant to a string. 
```java
EnumMapperFull<Seasons, String> germanSeasons = Seasons_MapperFull
     .setSPRING("Fruehling")
     .setSUMMER("Sommer")
     .setFALL("Herbst")
     .setWINTER("Winter");

String germanSummer = germanSeasons.getValue(Seasons.SUMMER); // returns "Sommer"
```

The great thing about this is that you will get a compile time error, when you
forget to map any enum-constant - or when you change the enumeration (e.g. add or remove an enum-constant).

Detailed usage examples are also available on the [github enum-mapper-example project](
https://github.com/tmtron/enum-mapper-example)

### Third party enums
You can also generate full-mappers for Enums that you don't control. Just add the `@EnumMappers` 
(note the plural form!) annotation to any class and reference the enum classes that you want to map.
 
For example let's assume, that you use a 3rd party library which defines these enums:
```java
public enum ColorEnum {
    RED, BLUE, GREEN
}
public enum BoolEnum {
    ON, OFF
}
```

Since you cannot change the source code of the 3rd party library, you cannot add the `@EnumMapper` annotation
to the enum classes.  
Instead you can use the the `@EnumMappers` (note the plural form!) annotation on any of your own classes, like this:
```java
@EnumMappers({ColorEnum.class, BoolEnum.class})
public class AppUtil {
    // your code here
}
``` 
Then the annotation processor will create a full enum-mapper for `ColorEnum` and for `BoolEnum`.

Hint: when you want to map a single enum, you don't need the curly braces (for the array-syntax)  
```java
@EnumMappers(ColorEnum.class)
public class AppUtil {
    // your code here
}
``` 

## Partial Enum Mapper
The project also includes a partial enum-mapper class which you may want to use instead of a switch statement.    
Note. The partial enum mapper does not need the annotation processor.

An example of a partial mapper for your `Seasons` enum:
```java
    EnumMapperPartial<Seasons, String> ExtremeSeasons =
            EnumMapperPartial.of(SUMMER, "Sommer"
                    , WINTER, "Winter");
```
Now you can call the `getValueOrNull` or `getValueOrDefault` methods like this:
```java
ExtremeSeasons.getValueOrNull(SUMMER);                      // returns "Sommer"
ExtremeSeasons.getValueOrNull(WINTER);                      // returns "Winter"
ExtremeSeasons.getValueOrNull(SPRING));                     // returns null
ExtremeSeasons.getValueOrDefault(SPRING, "not extreme");    // returns "not extreme"
ExtremeSeasons.getValueOrRaise(SPRING);                     // throws an IllegalArgumentException
```

## Reverse mapping
The full and the partial mappers also support reverse lookup.

For example, we can use the `ExtremeSeasons` mapper to get the enum-constant for a string, like this:
```java
ExtremeSeasons.getEnumOrNull("Sommer");                 // returns the enum-constant SUMMER
ExtremeSeasons.getEnumOrDefault("Fruehling", FALL));    // returns the enum-constant FALL
ExtremeSeasons.getEnumOrRaise("Fruehling");             // throws an IllegalArgumentException 
``` 
When you do a reverse mapping the mapped values should of course be unique.

### Alternatives
This section mentions some alternative approaches that you can use instead of this annotation processor.  
See also: [Stackoverflow: How to ensure completeness in an enum switch at compile time?](https://stackoverflow.com/questions/16797529/how-to-ensure-completeness-in-an-enum-switch-at-compile-time)

#### Abstract Enum Methods 
As mentioned in this [Stackoverflow answer](https://stackoverflow.com/a/16798500/6287240) 
you can have abstract methods on your enum definition. 
```java
public enum AlternativeBool {

    ON {
        @Override
        public String getGermanName() {
            return "ein";
        }
    }
    , OFF {
        @Override
        public String getGermanName() {
            return "aus";
        }
    };


    public abstract String getGermanName();
}
```

*Advantages* 
* this approach does not need an annotation processor
* you also get a compile-time error should you forget to implement a method for a new enum

*Disadvantages*
* it is quite verbose
* you can only use this for enums that you control

#### IDE checks
Some IDEs allow you to activate a check that will warn you when you forget an enum constant in a switch statement:
* [Eclipse: Ensuring completeness of switch statements](http://help.eclipse.org/kepler/index.jsp?topic=%2Forg.eclipse.jdt.doc.user%2Ftasks%2Ftask-ensuring_switch_completeness.htm)

*Advantages* 
* this approach does not need an annotation processor
* direct and immediate feedback

*Disadvantages*
* you could forget to active the switch 
* someone members of your team may use other IDEs which don't support this feature

#### Other tools 
For example [FindBugs](http://findbugs.sourceforge.net/) has a check *Switch statement found where default case is missing* 
[SF_SWITCH_NO_DEFAULT](http://findbugs.sourceforge.net/bugDescriptions.html#SF_SWITCH_NO_DEFAULT)

*Advantages*
* other tools may offer way more functionality

*Disadvantages*
* those are other tools that you must learn to use and maintain 


## License
This plugin is under the [Apache 2.0 license](http://www.apache.org/licenses/LICENSE-2.0.html). Copyright 2017, Martin Trummer
