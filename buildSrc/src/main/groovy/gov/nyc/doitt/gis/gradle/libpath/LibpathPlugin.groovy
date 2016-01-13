package gov.nyc.doitt.gis.gradle.libpath

import org.gradle.model.Managed
import org.gradle.platform.base.BinarySpec
import org.gradle.platform.base.ComponentSpec

@Managed
interface PathReporter extends BinarySpec {
  List<File> directories
}
