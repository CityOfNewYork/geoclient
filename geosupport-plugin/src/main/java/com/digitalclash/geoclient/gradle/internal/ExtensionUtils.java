package com.digitalclash.geoclient.gradle.internal;

import org.gradle.api.Action;

import com.digitalclash.geoclient.gradle.GeosupportApplication;
import com.digitalclash.geoclient.gradle.GeosupportExtension;
import com.digitalclash.geoclient.gradle.GeosupportIntegrationTestOptions;

public class ExtensionUtils {

    private ExtensionUtils() {}

    public static Action<GeosupportExtension> conventionsFrom(final GeosupportExtension extension) {
        return new Action<GeosupportExtension>() {
                    @Override
                    public void execute(GeosupportExtension target) {
                        target.getGeofiles().convention(extension.getGeofiles().get());
                        target.getHome().convention(extension.getHome().get());
                        target.getIncludePath().convention(extension.getIncludePath().get());
                        target.getLibraryPath().convention(extension.getLibraryPath().get());
                    }
        };
    }

    public static Action<GeosupportIntegrationTestOptions> conventionsFrom(final GeosupportIntegrationTestOptions extension) {
        return new Action<GeosupportIntegrationTestOptions>() {
                    @Override
                    public void execute(GeosupportIntegrationTestOptions target) {
                        target.getTestName().convention(extension.getTestName().get());
                        target.getSourceSetName().convention(extension.getSourceSetName().get());
                        target.getJavaSourceDir().convention(extension.getJavaSourceDir().get());
                        target.getResourcesSourceDir().convention(extension.getResourcesSourceDir().get());
                        target.getValidate().convention(extension.getValidate().get());
                        target.getExportLdLibraryPath().convention(extension.getExportLdLibraryPath().get());
                        target.getUseJavaLibraryPath().convention(extension.getUseJavaLibraryPath().get());
                    }
        };
    }

    public static Action<GeosupportApplication> conventionsFrom(final GeosupportApplication extension) {
        return new Action<GeosupportApplication>() {
                    @Override
                    public void execute(GeosupportApplication target) {
                        target.geosupport(ExtensionUtils.conventionsFrom(extension.getGeosupport()));
                        target.integrationTestOptions(ExtensionUtils.conventionsFrom(extension.getIntegrationTestOptions()));
                    }
        };
    }
}
