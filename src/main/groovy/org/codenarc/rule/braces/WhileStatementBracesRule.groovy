/*
 * Copyright 2009 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codenarc.rule.braces

import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.IfStatement
import org.codenarc.rule.AbstractAstVisitor
import org.codenarc.rule.AbstractAstVisitorRule
import org.codehaus.groovy.ast.stmt.WhileStatement


/**
 * Rule that checks that while statements use braces rather than a single statement.
 *
 * @author Chris Mair
 * @version $Revision: 24 $ - $Date: 2009-01-31 07:47:09 -0500 (Sat, 31 Jan 2009) $
 */
class WhileStatementBracesRule extends AbstractAstVisitorRule {
    String name = 'WhileStatementBraces'
    int priority = 2
    Class astVisitorClass = WhileStatementBracesAstVisitor
}

class WhileStatementBracesAstVisitor extends AbstractAstVisitor  {
    void visitWhileLoop(WhileStatement whileStatement) {
        if (!isBlock(whileStatement.loopBlock)) {
            addViolation(whileStatement)
        }
        super.visitWhileLoop(whileStatement)
    }

}