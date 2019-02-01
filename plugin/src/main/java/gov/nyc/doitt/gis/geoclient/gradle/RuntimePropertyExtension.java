package gov.nyc.doitt.gis.geoclient.gradle;

import org.gradle.api.NamedDomainObjectContainer;
//import org.gradle.api.tasks.testing.TestFilter;

public interface RuntimePropertyExtension {

    String getName();

    NamedDomainObjectContainer<RuntimeProperty> getRuntimeProperties();

    // TestFilter getTestFilter();
}
