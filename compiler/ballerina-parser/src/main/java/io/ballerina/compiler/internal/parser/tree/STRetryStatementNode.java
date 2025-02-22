/*
 *  Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
package io.ballerina.compiler.internal.parser.tree;

import io.ballerina.compiler.syntax.tree.Node;
import io.ballerina.compiler.syntax.tree.NonTerminalNode;
import io.ballerina.compiler.syntax.tree.RetryStatementNode;
import io.ballerina.compiler.syntax.tree.SyntaxKind;

import java.util.Collection;
import java.util.Collections;

/**
 * This is a generated internal syntax tree node.
 *
 * @since 2.0.0
 */
public class STRetryStatementNode extends STStatementNode {
    public final STNode retryKeyword;
    public final STNode typeParameter;
    public final STNode arguments;
    public final STNode retryBody;
    public final STNode onFailClause;

    STRetryStatementNode(
            STNode retryKeyword,
            STNode typeParameter,
            STNode arguments,
            STNode retryBody,
            STNode onFailClause) {
        this(
                retryKeyword,
                typeParameter,
                arguments,
                retryBody,
                onFailClause,
                Collections.emptyList());
    }

    STRetryStatementNode(
            STNode retryKeyword,
            STNode typeParameter,
            STNode arguments,
            STNode retryBody,
            STNode onFailClause,
            Collection<STNodeDiagnostic> diagnostics) {
        super(SyntaxKind.RETRY_STATEMENT, diagnostics);
        this.retryKeyword = retryKeyword;
        this.typeParameter = typeParameter;
        this.arguments = arguments;
        this.retryBody = retryBody;
        this.onFailClause = onFailClause;

        addChildren(
                retryKeyword,
                typeParameter,
                arguments,
                retryBody,
                onFailClause);
    }

    public STNode modifyWith(Collection<STNodeDiagnostic> diagnostics) {
        return new STRetryStatementNode(
                this.retryKeyword,
                this.typeParameter,
                this.arguments,
                this.retryBody,
                this.onFailClause,
                diagnostics);
    }

    public STRetryStatementNode modify(
            STNode retryKeyword,
            STNode typeParameter,
            STNode arguments,
            STNode retryBody,
            STNode onFailClause) {
        if (checkForReferenceEquality(
                retryKeyword,
                typeParameter,
                arguments,
                retryBody,
                onFailClause)) {
            return this;
        }

        return new STRetryStatementNode(
                retryKeyword,
                typeParameter,
                arguments,
                retryBody,
                onFailClause,
                diagnostics);
    }

    public Node createFacade(int position, NonTerminalNode parent) {
        return new RetryStatementNode(this, position, parent);
    }

    @Override
    public void accept(STNodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <T> T apply(STNodeTransformer<T> transformer) {
        return transformer.transform(this);
    }
}
