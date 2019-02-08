package gov.nyc.doitt.gis.geoclient.gradle.plugin

class PropertySource {
    
    String key
    Object value
    Source source
    
    boolean isPresent() {
        this.key && this.value && this.source
    }
}