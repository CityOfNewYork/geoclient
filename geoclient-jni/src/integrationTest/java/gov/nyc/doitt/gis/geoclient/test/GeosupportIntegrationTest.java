/*
 * Copyright 2013-2022 the original author or authors.
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
package gov.nyc.doitt.gis.geoclient.test;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
/**
 * Composed {@link Annotation} indicating that this type or method invokes a
 * native JNI call to the {@code Geoclient} C runtime and will only be enabled
 * on non-mainframe platforms supported by {@code Geosupport}. At this time, the
 * following platforms are supported:
 * <p>
 * <ul>
 * <li>64-bit Linux ({@code libc 6.+})</li>
 * <li>64-bit Windows ({@code MSVC++ 2013, 2015, 2017})</li>
 * </ul>
 * <p>
 * To enforce the above, this type is composed from the following
 * {@code JUnit 5} {@link Annotation}s configured as follows:
 * <ul>
 * <li>{@code @EnabledIfEnvironmentVariable(named = "GEOFILES", matches = ".+(/|\\\\)$")}</li>
 * <li>{@code @EnabledIfSystemProperty(named = "os.arch", matches = ".*64.*")}</li>
 * <li>{@code @EnabledOnOs({ OS.LINUX, OS.WINDOWS })}</li>
 * </ul>
 *
 * @author mlipper
 *
 * @since 2.0
 *
 * @see org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable
 * @see org.junit.jupiter.api.condition.EnabledIfSystemProperty
 * @see org.junit.jupiter.api.condition.EnabledOnOs
 *
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
@Documented
@EnabledIfEnvironmentVariable(named = "GEOFILES", matches = ".+(/|\\\\)$")
@EnabledIfSystemProperty(named = "os.arch", matches = ".*64.*")
@EnabledOnOs({ OS.LINUX, OS.WINDOWS })
public @interface GeosupportIntegrationTest {

}
