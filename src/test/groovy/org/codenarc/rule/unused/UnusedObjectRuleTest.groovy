/*
 * Copyright 2010 the original author or authors.
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
package org.codenarc.rule.unused

import org.codenarc.rule.AbstractRuleTestCase
import org.codenarc.rule.Rule

/**
 * Tests for UnusedObjectRule
 *
 * @author Your Name Here
 * @version $Revision$ - $Date$
 */
class UnusedObjectRuleTest extends AbstractRuleTestCase {

    void testRuleProperties() {
        assert rule.priority == 2
        assert rule.name == "UnusedObject"
    }

    void testApplyTo_ObjectAssigned_NoViolations() {
        final SOURCE = '''
        	def v1 = new Object()
            URL v2 = new URL("www.google.com")
            println new BigDecimal("23.45")
        '''
        assertNoViolations(SOURCE)
    }

    void testApplyTo_ObjectNotAssigned_ButLastStatementWithinAMethod_NoViolations() {
        final SOURCE = '''
            println new BigDecimal("23.45")
        	new Object()
        '''
        assertNoViolations(SOURCE)
    }

    void testApplyTo_ObjectNotAssigned_ButLastStatementWithinAClosure_NoViolations() {
        final SOURCE = '''
            def closure = { new Date() }
        '''
        assertNoViolations(SOURCE)
    }

    void testApplyTo_ObjectNotAssigned_Violations() {
        final SOURCE = '''
        	new Object()
            new URL("www.google.com")
            println "ok"
        '''
        assertViolations(SOURCE,
                [lineNumber: 2, sourceLineText: 'new Object()'],
                [lineNumber: 3, sourceLineText: 'new URL("www.google.com")'])
    }

    void testApplyTo_ObjectNotAssigned_WithinClosure_Violations() {
        final SOURCE = '''
            def myClosure = { ->
            	new Object()
                doSomething()
            }
        '''
        assertViolations(SOURCE, [lineNumber: 3, sourceLineText: 'new Object()'])
    }

    void testApplyTo_SuperConstructorCall_NoViolations() {
        final SOURCE = '''
            class MyClass {
                MyClass() {
                    super()
                    doSomething()
                }
                MyClass(String name) {
                    super(name)
                    doSomething()
                }
            }
        '''
        assertNoViolations(SOURCE)
    }

    void testApplyTo_ThisConstructorCall_NoViolations() {
        final SOURCE = '''
            class MyClass {
                MyClass() {
                    this()
                    doSomething()
                }
                MyClass(String name) {
                    this(name)
                    doSomething()
                }
            }
        '''
        assertNoViolations(SOURCE)
    }

    protected Rule createRule() {
        return new UnusedObjectRule()
    }

}
