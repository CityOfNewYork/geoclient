package gov.nyc.doitt.gis.geoclient.gradle.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ TYPE, METHOD })
@Retention(RUNTIME)
@Documented
@Inherited
@Repeatable(PropertySources.class)
public @interface PropertySource {

    Source value();

    int order() default 0;
}
