package gov.nyc.doitt.gis.geoclient.gradle;

import org.gradle.api.NamedDomainObjectContainer;

public interface RuntimePropertyExtension {

    String getName();

    NamedDomainObjectContainer<RuntimeProperty> getRuntimeProperties();
}
