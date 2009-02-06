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

import org.codenarc.rule.AbstractRuleTest
import org.codenarc.rule.Rule

/**
 * Tests for WhileStatementBracesRule
 *
 * @author Chris Mair
 * @version $Revision: 24 $ - $Date: 2009-01-31 07:47:09 -0500 (Sat, 31 Jan 2009) $
 */
class WhileStatementBracesRuleTest extends AbstractRuleTest {

    void testRuleProperties() {
        assert rule.priority == 2
        assert rule.name == 'WhileStatementBraces'
    }

    void testApplyTo_Violation() {
        final SOURCE = '''
            class MyClass {
                def myMethod() {
                    while (x==23) println '23'
                    println 'ok'
                    while (alreadyInitialized())
                        println 'initialized'
                }
            }
        '''
        assertTwoViolations(SOURCE, 4, 'while (x==23)', 6, 'while (alreadyInitialized())')
    }

    void testApplyTo_Violation_IfStatementWithCommentOnly() {
        final SOURCE = '''
            while (isReady) {
                // TODO Should do something here
            }
        '''
        assertNoViolations(SOURCE)
    }

    void testApplyTo_NoViolations() {
        final SOURCE = '''class MyClass {
                def myMethod() {
                    while (isReady) {
                        println "ready"
                    }
                    while (x==23) { println '23' }
                }
            }'''
        assertNoViolations(SOURCE)
    }

    protected Rule createRule() {
        return new WhileStatementBracesRule()
    }

}