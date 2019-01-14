package gov.nyc.doitt.gis.geoclient.gradle.configuration

import static gov.nyc.doitt.gis.geoclient.gradle.configuration.Source.*

import gov.nyc.doitt.gis.geoclient.gradle.configuration.ValueSource
import gov.nyc.doitt.gis.geoclient.gradle.configuration.ValueSources

class FruitExtension {

    private String apple
    private String banana
    private String grape

    // Empty repeatable annotation type container
    @ValueSources(value = [])
    String getApple() {
        return apple;
    }

    void setApple(String apple) {
        this.apple = apple;
    }

    // Use repeatable annotation without containing type.
    // See https://docs.oracle.com/javase/tutorial/java/annotations/repeating.html
    @ValueSource(key="BANANA", source=ENVIRONMENT_VARIABLE)
    @ValueSource(key="fruit.banana", source=SYSTEM_PROPERTY)
    String getBanana() {
        return banana;
    }
    void setBanana(String banana) {
        this.banana = banana;
    }

    // Use repeatable annotation nested within the containing type.
    // See https://docs.oracle.com/javase/tutorial/java/annotations/repeating.html
    @ValueSources([
        @ValueSource(key="GRAPE", source = ENVIRONMENT_VARIABLE, order = 2),
        @ValueSource(key="grape", source = PROJECT_PROPERTY, order = 2),
        @ValueSource(key="fruit.Grape", source = SYSTEM_PROPERTY, order = 1)
    ])
    public String getGrape() {
        return grape;
    }

    public void setGrape(String grape) {
        this.grape = grape;
    }
}