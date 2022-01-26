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

/**
 * Indicates that the given street code is not valid. Typically, this is based
 * on the set of known types defined in {@link StreetCodeType}.
 *
 * @see StreetCodeType
 * @author mlipper
 *
 */
public class InvalidStreetCodeException extends RuntimeException {

    private static final long serialVersionUID = 971853261039580054L;

    /**
     * Creates an instance using a default message built with the given street code argument.
     *
     * @param streetCode street code value that is invalid
     */
    public InvalidStreetCodeException(String streetCode) {
        super(String.format("Invalid street code %s (%d chars)", streetCode, streetCode != null ? streetCode.length() : -1));
    }

    /**
     * Creates an instance using a default message built with the given street code argument.
     *
     * @param streetCode street code value that is invalid
     * @param validCodeMessage description of valid code format(s)
     */
    public InvalidStreetCodeException(String streetCode, String validCodeMessage) {
        super(String.format("Invalid street code: %s (%d chars). %s", streetCode, (streetCode != null ? streetCode.length() : -1), validCodeMessage));
    }
}
