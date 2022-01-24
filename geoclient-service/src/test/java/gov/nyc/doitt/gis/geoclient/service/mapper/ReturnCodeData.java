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
package gov.nyc.doitt.gis.geoclient.service.mapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReturnCodeData {
    public final String SUCCESS_RETURN_CODE = "00";
    public final String SUCCESS_REASON_CODE = "";
    public final String SUCCESS_MESSAGE = "";
    public final String WARNING_RETURN_CODE = "01";
    public final String WARNING_REASON_CODE = "V";
    public final String WARNING_MESSAGE = "280 RIVERSIDE DRIVE IS ON LEFT SIDE OF W 100 ST";
    public final String ERROR_RETURN_CODE = "50";
    public final String ERROR_REASON_CODE = "2"; // Number (1-4) of similar names returned
    public final String ERROR_MESSAGE = "FASHION AVENUE IS AN INVALID STREET NAME FOR THIS LOCATION";

    interface Stateful {
        default boolean isTerminal() {
            return this.getClass().isAssignableFrom(ReturnCodeData.Terminal.class);
        }

        default boolean isChange() {
            return this.getClass().isAssignableFrom(ReturnCodeData.Change.class);
        }

        default boolean isNoOp() {
            return this.getClass().isAssignableFrom(ReturnCodeData.NoOp.class);
        }
    }

    enum Change implements ReturnCodeData.Stateful {
        SUCCESS, WARNING, ERROR
    }

    enum NoOp implements ReturnCodeData.Stateful {
        WITH("_");
        private String value;

        private NoOp(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

    }

    enum Terminal implements ReturnCodeData.Stateful {
        RETURN_CODE, REASON_CODE, MESSAGE
    }

    private List<ReturnCodeData.Stateful> state;
    private List<String> values;

    public ReturnCodeData() {
        this.state = new ArrayList<>();
        this.values = new ArrayList<>();
    }

    public ReturnCodeData success() {
        this.state.add(Change.SUCCESS);
        return this;
    }

    public ReturnCodeData successCodes() {
        this.success().with().returnCode().then().success().with().reasonCode().then().success().with().message();
        return this;
    }

    public ReturnCodeData warning() {
        this.state.add(Change.WARNING);
        return this;
    }

    public ReturnCodeData warningCodes() {
        this.warning().returnCode().then().warning().reasonCode().then().warning().message();
        return this;
    }

    public ReturnCodeData error() {
        this.state.add(Change.ERROR);
        return this;
    }

    public ReturnCodeData errorCodes() {
        this.error().returnCode().then().error().reasonCode().then().error().message();
        return this;
    }

    private boolean lastElementIs(Stateful stateful) {
        if (this.state.isEmpty()) {
            return false;
        }
        return stateful.equals(this.state.get(this.state.size() - 1));
    }

    public ReturnCodeData with() {
        this.state.add(NoOp.WITH);
        return this;
    }

    public ReturnCodeData returnCode() {
        if (!lastElementIs(NoOp.WITH)) {
            with();
        }
        this.state.add(Terminal.RETURN_CODE);
        return this;
    }

    public ReturnCodeData reasonCode() {
        if (!lastElementIs(NoOp.WITH)) {
            with();
        }
        this.state.add(Terminal.REASON_CODE);
        return this;
    }

    public ReturnCodeData message() {
        if (!lastElementIs(NoOp.WITH)) {
            with();
        }
        this.state.add(Terminal.MESSAGE);
        return this;
    }

    public ReturnCodeData then() {
        doTerminalState();
        return this;
    }

    public String value() {
        doTerminalState();
        if (this.values.size() < 1) {
            return null;
        }
        String result = this.values.get(this.values.size() - 1);
        this.values.clear();
        return result;
    }

    public String[] values() {
        doTerminalState();
        if (this.values.size() < 1) {
            return new String[] {};
        }
        String[] result = this.values.toArray(new String[] {});
        this.values.clear();
        return result;
    }

    private void doTerminalState() throws RuntimeException {
        if (state.isEmpty() || state.size() != 3) {
            throw new IllegalStateException(String.format(
                    "Grammar requires state [Change, NoOp, Terminal] when value() called. Instead was %s",
                    state.toString()));
        }
        try {
            StringBuffer buffer = new StringBuffer();
            ReturnCodeData.Change c = (ReturnCodeData.Change) this.state.get(0);
            buffer.append(c);
            ReturnCodeData.NoOp n = (ReturnCodeData.NoOp) this.state.get(1);
            buffer.append(n);
            ReturnCodeData.Terminal t = (ReturnCodeData.Terminal) this.state.get(2);
            buffer.append(t);
            String constName = buffer.toString();
            String result = getFieldValue(constName);
            this.values.add(result);
            this.state.clear();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getFieldValue(String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = ReturnCodeData.class.getDeclaredField(fieldName);
        Object value = field.get(this);
        return value.toString();
    }
}