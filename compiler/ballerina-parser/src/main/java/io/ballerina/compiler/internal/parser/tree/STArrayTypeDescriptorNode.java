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

import io.ballerina.compiler.syntax.tree.ArrayTypeDescriptorNode;
import io.ballerina.compiler.syntax.tree.Node;
import io.ballerina.compiler.syntax.tree.NonTerminalNode;
import io.ballerina.compiler.syntax.tree.SyntaxKind;

import java.util.Collection;
import java.util.Collections;

/**
 * This is a generated internal syntax tree node.
 *
 * @since 2.0.0
 */
public class STArrayTypeDescriptorNode extends STTypeDescriptorNode {
    public final STNode memberTypeDesc;
    public final STNode dimensions;

    STArrayTypeDescriptorNode(
            STNode memberTypeDesc,
            STNode dimensions) {
        this(
                memberTypeDesc,
                dimensions,
                Collections.emptyList());
    }

    STArrayTypeDescriptorNode(
            STNode memberTypeDesc,
            STNode dimensions,
            Collection<STNodeDiagnostic> diagnostics) {
        super(SyntaxKind.ARRAY_TYPE_DESC, diagnostics);
        this.memberTypeDesc = memberTypeDesc;
        this.dimensions = dimensions;

        addChildren(
                memberTypeDesc,
                dimensions);
    }

    public STNode modifyWith(Collection<STNodeDiagnostic> diagnostics) {
        return new STArrayTypeDescriptorNode(
                this.memberTypeDesc,
                this.dimensions,
                diagnostics);
    }

    public STArrayTypeDescriptorNode modify(
            STNode memberTypeDesc,
            STNode dimensions) {
        if (checkForReferenceEquality(
                memberTypeDesc,
                dimensions)) {
            return this;
        }

        return new STArrayTypeDescriptorNode(
                memberTypeDesc,
                dimensions,
                diagnostics);
    }

    public Node createFacade(int position, NonTerminalNode parent) {
        return new ArrayTypeDescriptorNode(this, position, parent);
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
