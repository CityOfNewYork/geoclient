package gov.nyc.doitt.gis.geoclient.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class Borough extends CodeNamedValue {
    
    private final List<String> aliases;
    
    protected Borough(String code, String name, String[] aliases) {
        super(code, name, false);
        this.aliases = new ArrayList<String>();
        this.aliases.add(name);
        this.aliases.addAll(Arrays.asList(aliases));
    }
    
    public Borough isAlsoKnownAs(String name) {
        Optional<String> result = this.aliases.stream().filter(alias -> alias.equalsIgnoreCase(name)).findFirst();
        if(result.isPresent()) {
            return this;
        }
        return null;
    }

    
}
