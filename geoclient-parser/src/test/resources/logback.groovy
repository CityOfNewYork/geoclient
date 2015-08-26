import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

import static ch.qos.logback.classic.Level.DEBUG

import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

import static ch.qos.logback.classic.Level.INFO
import static ch.qos.logback.classic.Level.WARN

appender("stdout", ConsoleAppender) {
  target = "System.out"
  encoder(PatternLayoutEncoder) {
   // pattern = "%d %p [%c] - <%m>%n"
    pattern = "%d %p - %m%n"
  }
}
logger("org.springframework", INFO)
logger("gov.nyc.doitt.gis.geoclient", INFO)
root(WARN, ["stdout"])