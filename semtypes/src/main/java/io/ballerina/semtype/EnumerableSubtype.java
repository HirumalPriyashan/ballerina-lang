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

package io.ballerina.semtype;

import java.util.List;
import java.util.Objects;

/**
 * EnumerableSubtype with enumerable subtype ops.
 *
 * @since 2.0.0
 */
public class EnumerableSubtype {
    static final int LT = -1;
    static final int EQ = 0;
    static final int GT = 1;

    boolean allowed;
    List<? extends EnumerableType> values;

    public static boolean enumerableSubtypeUnion(EnumerableSubtype t1, EnumerableSubtype t2,
                                                 List<? extends EnumerableType> result) {
        boolean b1 = t1.allowed;
        boolean b2 = t2.allowed;
        boolean allowed;
        if (b1 && b2) {
            enumerableListUnion(t1.values, t2.values, result);
            allowed = true;
        } else if (!b1 && !b2) {
            enumerableListIntersect(t1.values, t2.values, result);
            allowed = false;
        } else if (b1 && !b2) {
            enumerableListDiff(t2.values, t1.values, result);
            allowed = false;
        } else {
            // !b1 && b2
            enumerableListDiff(t1.values, t2.values, result);
            allowed = false;
        }
        return allowed;
    }

    public static boolean enumerableSubtypeIntersect(EnumerableSubtype t1, EnumerableSubtype t2,
                                                     List<? extends EnumerableType> result) {
        boolean b1 = t1.allowed;
        boolean b2 = t2.allowed;
        boolean allowed;
        if (b1 && b2) {
            enumerableListIntersect(t1.values, t2.values, result);
            allowed = true;
        } else if (!b1 && !b2) {
            enumerableListUnion(t1.values, t2.values, result);
            allowed = false;
        } else if (b1 && !b2) {
            enumerableListDiff(t1.values, t2.values, result);
            allowed = false;
        } else {
            // !b1 && b2
            enumerableListDiff(t2.values, t1.values, result);
            allowed = false;
        }
        return allowed;
    }

    public static void enumerableListUnion(List<? extends EnumerableType> v1, List<? extends EnumerableType> v2,
                                           List<? extends EnumerableType> resulte) {
        List<EnumerableType> result = (List<EnumerableType>) resulte;
        int i1 = 0;
        int i2 = 0;
        int len1 = v1.size();
        int len2 = v2.size();

        while (true) {
            if (i1 >= len1) {
                if (i2 >= len2) {
                    break;
                }
                result.add(v2.get(i2));
                i2 += 1;
            } else if (i2 >= len2) {
                result.add(v1.get(i1));
                i1 += 1;
            } else {
                EnumerableType s1 = v1.get(i1);
                EnumerableType s2 = v2.get(i2);
                switch (compareEnumerable(s1, s2)) {
                    case EQ:
                        result.add(s1);
                        i1 += 1;
                        i2 += 1;
                        break;
                    case LT:
                        result.add(s1);
                        i1 += 1;
                        break;
                    case GT:
                        result.add(s2);
                        i2 += 1;
                        break;
                }
            }
        }
    }

    public static void enumerableListIntersect(List<? extends EnumerableType> v1, List<? extends EnumerableType> v2,
                                               List<? extends EnumerableType> resulte) {
        List<EnumerableType> result = (List<EnumerableType>) resulte;
        int i1 = 0;
        int i2 = 0;
        int len1 = v1.size();
        int len2 = v2.size();

        while (true) {
            if (i1 >= len1 || i2 >= len2) {
                break;
            } else {
                EnumerableType s1 = v1.get(i1);
                EnumerableType s2 = v2.get(i2);
                switch (compareEnumerable(s1, s2)) {
                    case EQ:
                        result.add(s1);
                        i1 += 1;
                        i2 += 1;
                        break;
                    case LT:
                        i1 += 1;
                        break;
                    case GT:
                        i2 += 1;
                        break;
                }
            }
        }
    }

    public static void enumerableListDiff(List<? extends EnumerableType> v1, List<? extends EnumerableType> v2,
                                          List<? extends EnumerableType> resulte) {
        List<EnumerableType> result = (List<EnumerableType>) resulte;
        int i1 = 0;
        int i2 = 0;
        int len1 = v1.size();
        int len2 = v2.size();

        while (true) {
            if (i1 >= len1) {
                break;
            }
            if (i2 >= len2) {
                result.add(v1.get(i1));
                i1 += 1;
            } else {
                EnumerableType s1 = v1.get(i1);
                EnumerableType s2 = v2.get(i2);
                switch (compareEnumerable(s1, s2)) {
                    case EQ:
                        i1 += 1;
                        i2 += 1;
                        break;
                    case LT:
                        result.add(s1);
                        i1 += 1;
                        break;
                    case GT:
                        i2 += 1;
                        break;
                }
            }
        }
    }

    public static int compareEnumerable(EnumerableType v1, EnumerableType v2) {
        if (v1 instanceof EnumerableString) {
            String s2 = ((EnumerableString) v2).value;
            String s1 = ((EnumerableString) v1).value;
            return Objects.equals(s1, s2) ? EQ : s1.length() < s2.length() ? LT : GT;
        } else {
            double f1 = ((EnumerableFloat) v2).value;
            double f2 = ((EnumerableFloat) v2).value;
            if (f1 == f2) {
                return EQ;
            } else if (Double.isNaN(f1)) {
                return LT;
            } else if (Double.isNaN(f2)) {
                return GT;
            } else if (f1 < f2) {
                return LT;
            }
            return GT;
        }
    }
}
