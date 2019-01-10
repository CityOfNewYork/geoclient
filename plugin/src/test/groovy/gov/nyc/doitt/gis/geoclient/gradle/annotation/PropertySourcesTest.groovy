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
package gov.nyc.doitt.gis.geoclient.gradle.annotation

import java.lang.reflect.Method
import java.lang.reflect.Modifier

import spock.lang.Specification

class PropertySourcesTest extends Specification {
    FruitExtension fruit
    def setup() {
        fruit = new FruitExtension()
    }

    def "repeatable annotation type container @PropertySources returns elements in declaration order"() {
        given:
        PropertySources gAggregateSources = getPropertySources(fruit, "getGrape")
        and:
        PropertySource[] gSources = gAggregateSources.value()
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

    def "stand-alone declarations of @PropertySource will be returned in declaration order"() {
        given:
        PropertySources bAggregateSources = getPropertySources(fruit, "getBanana")
        and:
        PropertySource[] bSources = bAggregateSources.value()
        expect:
        bSources.length == 2
        bSources[0].value() == Source.ENVIRONMENT_VARIABLE
        bSources[1].value() == Source.SYSTEM_PROPERTY
    }

    def "repeatable annotation type container @PropertySources does not break when empty"() {
        given:
        PropertySources gAggregateSources = getPropertySources(fruit, "getApple")
        and:
        PropertySource[] gSources = gAggregateSources.value()
        expect:
        gSources.length == 0
    }

    def "repeatable annotation @PropertySource can be used on types"() {
        given:
        PropertySource fruitSource = getPropertySource(fruit)
        expect:
        fruitSource.value() == Source.EXTENSION_DEFAULT
    }

    PropertySources getPropertySources(FruitExtension instance, String methodName) {
        Method method = instance.class.declaredMethods.find { m ->
            if(m.getName() == methodName) {
                return m
            }
        }
        PropertySources result = getPropertySources(method)
        assert result
        result
    }

    PropertySources getPropertySources(Method method) {
        // Only public or protected methods which take no arguments
        if(Modifier.isPublic(method.modifiers) || Modifier.isProtected(method.modifiers)) {
            if(method.parameterTypes.length == 0) {
                PropertySources ps = method.getAnnotation(PropertySources)
                if(ps != null) {
                    return ps
                }
            }
        }
    }

    PropertySource getPropertySource(FruitExtension fruit) {
        fruit.getClass().getAnnotation(PropertySource)
    }

}
