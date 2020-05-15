/**
 *
 */
package gov.nyc.doitt.gis.geoclient.test;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.junit.jupiter.api.Tag;

@Retention(RUNTIME)
@Target({ TYPE, METHOD })
@Documented
@Tag("jni")
/**
 * {@code Annotation} indicating that the type or method will require a working
 * JNI configuration to Geosupport.
 *
 * Composed with the {@code JUnit 5} annotation {@link Tag} class and configured
 * with: <code>@Tag("jni")</code>
 *
 * @author mlipper
 *
 */
public @interface NativeIntegrationTest {

}
