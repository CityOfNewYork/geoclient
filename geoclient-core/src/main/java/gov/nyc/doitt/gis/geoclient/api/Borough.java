/*
 * Copyright 2013-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gov.nyc.doitt.gis.geoclient.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    public boolean isAlsoKnownAs(String name) {
        Optional<String> result = this.aliases.stream().filter(alias -> alias.equalsIgnoreCase(name)).findFirst();
        return result.isPresent();
    }

    public List<String> getAliases() {
        return Collections.unmodifiableList(this.aliases);
    }

}
