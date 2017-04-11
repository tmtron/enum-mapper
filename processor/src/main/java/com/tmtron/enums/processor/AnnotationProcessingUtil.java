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
package com.tmtron.enums.processor;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;

class AnnotationProcessingUtil {

    private static volatile Supplier<ZonedDateTime> zonedDateTimeProvider = ZonedDateTime::now;

    // visible for testing
    static void setCurrentTimeProvider(Supplier<ZonedDateTime> currentTimeProvider) {
        AnnotationProcessingUtil.zonedDateTimeProvider = currentTimeProvider;
    }

    static ZonedDateTime now() {
        return zonedDateTimeProvider.get();
    }

    /**
     * Returns the AnnotationValue with the elementNameSimple from the annotationMirror
     * or throws a {@link RuntimeException} when the element does not exist
     *
     * @param annotationMirror  must contain the element
     * @param elementNameSimple the simple name of the element
     */
    static AnnotationValue getRequiredAnnotationValue(AnnotationMirror annotationMirror,
                                                      String elementNameSimple) {
        for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry :
                annotationMirror.getElementValues().entrySet()) {
            if (elementNameSimple.equals(entry.getKey().getSimpleName().toString())) {
                // e.g. "enums" -> {com.test.Dummy.ColorEnum.class, com.test.Dummy.BoolEnum.class}
                return entry.getValue();
            }
        }
        throw new RuntimeException("Required annotation value '" + elementNameSimple + "' not found");
    }

    /**
     * Will do the type checking to assure that the given object is indeed a list
     * which only contains items of type itemsClass and returns the object cast to
     * the list-type.
     *
     * @param object     the object which must be of type List of T
     * @param itemsClass the required type for all items in the object list
     * @param objName    the name of the object (will be used for thrown Exceptions)
     * @param <T>        the type of the list-elements (itemClass)
     * @return the object cast to a list of T
     * @throws RuntimeException when object is not a list of T
     */
    @SuppressWarnings("unchecked")
    static <T> List<T> asList(Object object, Class<T> itemsClass, String objName) {
        if (!(object instanceof List<?>)) {
            throw new RuntimeException("The annotation member '" + objName + "' must be an array/list.");
        }
        List<?> objList = (List<?>) object;
        objList.forEach(
                item -> {
                    if (!(itemsClass.isAssignableFrom(item.getClass()))) {
                        throw new RuntimeException("The annotation member '" + objName + "' (array/list)"
                                + " must only contain items of type: " + itemsClass.getCanonicalName());
                    }
                }
        );
        // the loop above has check that all items are okay
        return (List<T>) objList;
    }
}
