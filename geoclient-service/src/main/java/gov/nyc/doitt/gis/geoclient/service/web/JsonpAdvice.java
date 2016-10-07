/**
 * 
 */
package gov.nyc.doitt.gis.geoclient.service.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

/**
 * Per the Spring Framework reference documentation (section 22.3.3) for JSONP
 * view resolution when using Jackson.
 * 
 * @see the <a href=
 *      "http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#mvc-ann-jsonp">
 *      Spring Framework Reference documentation</a>
 * @author mlipper
 * @since 2.0.0
 */
@ControllerAdvice
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {

  /**
   * Constructs a new {@link JsonpAdvice} instance which relies on a query
   * parameter named {@code callback}.
   * 
   * @param queryParamNames
   */
  public JsonpAdvice() {
    super("callback");
  }

}
