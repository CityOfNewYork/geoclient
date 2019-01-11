/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gov.nyc.doitt.gis.geoclient.gradle.property

import java.lang.reflect.Method
import java.lang.reflect.Modifier

import spock.lang.Specification

class ValueSourcesTest extends Specification {
    FruitExtension fruit
    def setup() {
        fruit = new FruitExtension()
    }

    def "repeatable annotation type container @ValueSources returns elements in declaration order"() {
        given:
        ValueSources gAggregateSources = getValueSources(fruit, "getGrape")
        and:
        ValueSource[] gSources = gAggregateSources.value()
        expect:
        gSources.length == 4
        gSources[0].value() == Source.ENVIRONMENT_VARIABLE
        gSources[0].order() == 2
        gSources[1].value() == Source.EXTENSION_DEFAULT
        gSources[1].order() == 0
        gSources[2].value() == Source.GRADLE_PROPERTY
        gSources[2].order() == 2
        gSources[3].value() == Source.SYSTEM_PROPERTY
        gSources[3].order() == 1
    }

    def "stand-alone declarations of @ValueSource will be returned in declaration order"() {
        given:
        ValueSources bAggregateSources = getValueSources(fruit, "getBanana")
        and:
        ValueSource[] bSources = bAggregateSources.value()
        expect:
        bSources.length == 2
        bSources[0].value() == Source.ENVIRONMENT_VARIABLE
        bSources[1].value() == Source.SYSTEM_PROPERTY
    }

    def "repeatable annotation type container @ValueSources does not break when empty"() {
        given:
        ValueSources gAggregateSources = getValueSources(fruit, "getApple")
        and:
        ValueSource[] gSources = gAggregateSources.value()
        expect:
        gSources.length == 0
    }

    def "repeatable annotation @ValueSource can be used on types"() {
        given:
        ValueSource fruitSource = getValueSource(fruit)
        expect:
        fruitSource.value() == Source.EXTENSION_DEFAULT
    }

    ValueSources getValueSources(FruitExtension instance, String methodName) {
        Method method = instance.class.declaredMethods.find { m ->
            if(m.getName() == methodName) {
                return m
            }
        }
        ValueSources result = getValueSources(method)
        assert result
        result
    }

    ValueSources getValueSources(Method method) {
        // Only public or protected methods which take no arguments
        if(Modifier.isPublic(method.modifiers) || Modifier.isProtected(method.modifiers)) {
            if(method.parameterTypes.length == 0) {
                ValueSources ps = method.getAnnotation(ValueSources)
                if(ps != null) {
                    return ps
                }
            }
        }
    }

    ValueSource getValueSource(FruitExtension fruit) {
        fruit.getClass().getAnnotation(ValueSource)
    }

}
