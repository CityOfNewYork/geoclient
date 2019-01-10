package gov.nyc.doitt.gis.geoclient.gradle.annotation

import static gov.nyc.doitt.gis.geoclient.gradle.annotation.Source.*

@PropertySource(value = EXTENSION_DEFAULT, order = -1)
class FruitExtension {

    private String apple
    private String banana
    private String grape

    // Empty repeatable annotation type container
    @PropertySources(value = [])
    String getApple() {
        return apple;
    }

    void setApple(String apple) {
        this.apple = apple;
    }

    // Use repeatable annotation without containing type.
    // See https://docs.oracle.com/javase/tutorial/java/annotations/repeating.html
    @PropertySource(ENVIRONMENT_VARIABLE)
    @PropertySource(SYSTEM_PROPERTY)
    String getBanana() {
        return banana;
    }
    void setBanana(String banana) {
        this.banana = banana;
    }

    // Use repeatable annotation nested within the containing type.
    // See https://docs.oracle.com/javase/tutorial/java/annotations/repeating.html
    @PropertySources([
        @PropertySource(value = ENVIRONMENT_VARIABLE, order = 2),
        @PropertySource(EXTENSION_DEFAULT),
        @PropertySource(value = GRADLE_PROPERTY, order = 2),
        @PropertySource(value = SYSTEM_PROPERTY, order = 1)
    ])
    public String getGrape() {
        return grape;
    }

    public void setGrape(String grape) {
        this.grape = grape;
    }
}