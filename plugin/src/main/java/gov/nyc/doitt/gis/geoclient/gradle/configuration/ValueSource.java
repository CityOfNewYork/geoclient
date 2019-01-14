package gov.nyc.doitt.gis.geoclient.gradle.configuration;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(METHOD)
@Retention(RUNTIME)
@Documented
@Inherited
@Repeatable(ValueSources.class)
public @interface ValueSource {

    String key() default "";

    Source source();

    int order() default 0;
}
