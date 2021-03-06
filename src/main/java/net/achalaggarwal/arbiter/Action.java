/*
 * Copyright 2015-2016 Etsy
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
 *
 * -----------------------------------------------------------------------
 *
 * This file has been modified from its original licensed form.
 * Modifications are Copyright (C) 2016 Achal Aggarwal (achalaggarwal.net).
 */

package net.achalaggarwal.arbiter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.achalaggarwal.arbiter.config.Prepare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an Oozie Action
 *
 * @author Andrew Johnson
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Action extends YamlElement {
    private String comment;
    private String cred;
    private String forceOk;
    private String forceError;
    private Map<String, List<String>> positionalArgs;
    private Map<String, String> namedArgs;
    private Map<String, String> properties;
    private String onlyIf;
    private List<ConditionalKill> killIf;
    private Prepare prepare;
    private String retryMax;
    private String retryInterval;

    private LinkedHashMap<String, LinkedHashMap<String, String>> elem;

    public String getActualName() {
        return onlyIf == null ? getName() : "q-" + getName();
    }

    public void setProperty(String name, String value) {
        if (namedArgs == null) {
            namedArgs = new HashMap<>();
        }
        namedArgs.put(name, value);
    }

    public void setProperty(String name, ArrayList<String> value) {
        if (positionalArgs == null) {
            positionalArgs = new HashMap<>();
        }

        positionalArgs.put(name, value);
    }

    public static Action getStartAction() {
        return new Action() { {
            setName("start");
            setType("start");
        } };
    }

    public static Action getEndAction() {
        return new Action() { {
            setName("end");
            setType("end");
        } };
    }

    public static Action getKillAction(final String name, final String message) {
        return new Action() { {
            setName(name);
            setType("kill");
            setProperty("message", message);
        } };
    }
}
