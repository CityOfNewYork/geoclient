package gov.nyc.doitt.gis.geoclient.gradle.property

import static gov.nyc.doitt.gis.geoclient.gradle.property.Source.*

import gov.nyc.doitt.gis.geoclient.gradle.property.ValueSource
import gov.nyc.doitt.gis.geoclient.gradle.property.ValueSources

@ValueSource(value = EXTENSION_DEFAULT, order = -1)
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
    @ValueSource(ENVIRONMENT_VARIABLE)
    @ValueSource(SYSTEM_PROPERTY)
    String getBanana() {
        return banana;
    }
    void setBanana(String banana) {
        this.banana = banana;
    }

    // Use repeatable annotation nested within the containing type.
    // See https://docs.oracle.com/javase/tutorial/java/annotations/repeating.html
    @ValueSources([
        @ValueSource(value = ENVIRONMENT_VARIABLE, order = 2),
        @ValueSource(EXTENSION_DEFAULT),
        @ValueSource(value = GRADLE_PROPERTY, order = 2),
        @ValueSource(value = SYSTEM_PROPERTY, order = 1)
    ])
    public String getGrape() {
        return grape;
    }

    public void setGrape(String grape) {
        this.grape = grape;
    }
}