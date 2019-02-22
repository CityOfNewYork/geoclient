package gov.nyc.doitt.gis.geoclient.api;

public class Street extends CodeNamedValue {

    public Street(String code, String name) {
        super(code, name, false);
    }

    public Street(String name) {
        super(null, name, false);
    }

}
