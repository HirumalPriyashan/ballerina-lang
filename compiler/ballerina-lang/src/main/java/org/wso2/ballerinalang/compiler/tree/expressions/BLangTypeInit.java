/*
 *   Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.ballerinalang.compiler.tree.expressions;

import org.ballerinalang.model.tree.NodeKind;
import org.ballerinalang.model.tree.expressions.ExpressionNode;
import org.ballerinalang.model.tree.expressions.TypeInitNode;
import org.wso2.ballerinalang.compiler.tree.BLangNodeAnalyzer;
import org.wso2.ballerinalang.compiler.tree.BLangNodeTransformer;
import org.wso2.ballerinalang.compiler.tree.BLangNodeVisitor;
import org.wso2.ballerinalang.compiler.tree.types.BLangType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implementation of {@link TypeInitNode}.
 *
 * @since 0.94
 */
public class BLangTypeInit extends BLangExpression implements TypeInitNode {

    // BLangNodes
    public BLangType userDefinedType;
    public List<BLangExpression> argsExpr;
    public BLangInvocation initInvocation;

    public BLangTypeInit() {
        argsExpr = new ArrayList<>();
    }

    @Override
    public BLangType getType() {
        return userDefinedType;
    }

    @Override
    public List<? extends ExpressionNode> getExpressions() {
        return this.argsExpr;
    }

    @Override
    public NodeKind getKind() {
        return NodeKind.TYPE_INIT_EXPR;
    }

    @Override
    public void accept(BLangNodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <T> void accept(BLangNodeAnalyzer<T> analyzer, T props) {
        analyzer.visit(this, props);
    }

    @Override
    public <T, R> R apply(BLangNodeTransformer<T, R> modifier, T props) {
        return modifier.transform(this, props);
    }

    @Override
    public String toString() {
        return "BLangTypeInit: new " + userDefinedType +
                " (" + (argsExpr != null ? Arrays.toString(argsExpr.toArray()) : "") + ") ";
    }
}
