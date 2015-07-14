/**
 * Copyright: (c) Syncleus, Inc.
 *
 * You may redistribute and modify this source code under the terms and
 * conditions of the Open Source Community License - Type C version 1.0
 * or any later version as published by Syncleus, Inc. at www.syncleus.com.
 * There should be a copy of the license included with this file. If a copy
 * of the license is not included you are granted no right to distribute or
 * otherwise use this file except through a legal and valid license. You
 * should also contact Syncleus, Inc. at the information below if you cannot
 * find a license:
 *
 * Syncleus, Inc.
 * 2604 South 12th Street
 * Philadelphia, PA 19148
 */
package com.syncleus.logicparser;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SymbolParserTest {
    private final static String FULL_LOGIC_STRING = "foo=(you|me|I)&&(bar>foo+20||something=x|you|z)";

    @Test
    public void testNothing() {
        final LogicParserTester parser = new LogicParserTester(FULL_LOGIC_STRING);
        parser.test();
    }

    private static class LogicParserTester extends LogicParser {
        public LogicParserTester(final String logicString) {
            super(logicString);
        }

        public void test() {
            System.out.println("the base string: " + this.toBaseString());
            System.out.println("The head is: " + this.getHead().toString());
            System.out.println("The head base is: " + this.getHead().toBaseString());
            System.out.println("Is the heads after null? " + (this.getHead().getAfter() == null));
            System.out.println("Head after: " + this.getHead().getAfter().toBaseString());
        }
    }
}
