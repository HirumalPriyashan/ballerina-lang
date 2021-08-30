/*
 *  Copyright (c) 2021, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package io.ballerina.semtype.subtypedata;

import io.ballerina.semtype.EnumerableFloat;
import io.ballerina.semtype.EnumerableSubtype;
import io.ballerina.semtype.PredefinedType;
import io.ballerina.semtype.ProperSubtypeData;
import io.ballerina.semtype.SemType;
import io.ballerina.semtype.SubtypeData;
import io.ballerina.semtype.UniformTypeCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represent FloatSubtype.
 *
 * @since 2.0.0
 */
public class FloatSubtype extends EnumerableSubtype implements ProperSubtypeData {

    public final boolean allowed;
    public final List<EnumerableFloat> values;

    public FloatSubtype(boolean allowed, EnumerableFloat value) {
        this.allowed = allowed;
        this.values = new ArrayList<>();
        values.add(value);
    }

    public FloatSubtype(boolean allowed, List<EnumerableFloat> values) {
        this.allowed = allowed;
        this.values = new ArrayList<>(values);
    }

    public static SemType floatConst(EnumerableFloat value) {
        return PredefinedType.uniformSubtype(UniformTypeCode.UT_FLOAT, new FloatSubtype(true, value));
    }

    public static Optional<EnumerableFloat> floatSubtypeSingleValue(SubtypeData d) {
        if (d instanceof AllOrNothingSubtype) {
            return Optional.empty();
        }

        FloatSubtype f = (FloatSubtype) d;
        if (f.allowed) {
            return Optional.empty();
        }

        List<EnumerableFloat> values = f.values;
        if (values.size() != 1) {
            return Optional.empty();
        }
        return Optional.of(values.get(0));
    }

    public static boolean floatSubtypeContains(SubtypeData d, EnumerableFloat f) {
        if (d instanceof AllOrNothingSubtype) {
            return ((AllOrNothingSubtype) d).isAllSubtype();
        }

        FloatSubtype v = (FloatSubtype) d;
        for (EnumerableFloat val : v.values) {
            if (val == f) {
                return v.allowed;
            }
        }
        return !v.allowed;
    }

    public static SubtypeData createFloatSubtype(boolean allowed, List<EnumerableFloat> values) {
        if (values.isEmpty()) {
            return new AllOrNothingSubtype(!allowed);
        }
        return new FloatSubtype(allowed, values);
    }
}