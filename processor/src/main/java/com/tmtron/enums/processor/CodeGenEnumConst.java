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

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;

import javax.lang.model.element.Element;

public class CodeGenEnumConst {

    private final TypeVariableName typeVariableName4Value;
    private final Element enumConstant;
    // e.g. ONLINE
    public final String identifier;
    // e.g. "setONLINE
    public final String setterName;
    // e.g. "IsetONLINE
    public final String interfaceName;
    public final ClassName interfaceClassName;
    // e.g. "IsetONLINE<V>
    public final TypeName interfaceTypeName;

    public CodeGenEnumConst(TypeVariableName typeVariableName4Value, Element enumConstant) {
        this.typeVariableName4Value = typeVariableName4Value;
        this.enumConstant = enumConstant;

        identifier = enumConstant.toString();
        setterName = "set" + identifier;
        interfaceName = "I" + setterName;
        interfaceClassName = ClassName.get("", interfaceName);
        // StagedBuilder<V>
        interfaceTypeName = ParameterizedTypeName.get(interfaceClassName, typeVariableName4Value);
    }
}
