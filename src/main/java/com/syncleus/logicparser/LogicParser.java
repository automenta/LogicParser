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

public class LogicParser {
    private final Linked<Linked<?,?,?>,Linked<?,?,?>,?> head;

    public LogicParser(final String logicString) {
        this.head = new LinkedString<>(null, null, logicString);

        this.parse();
    }

    private void parse() {
    }

    private String toBaseString() {
        Linked<?,?,?> currentLink = this.head;
        final StringBuilder baseBuilder = new StringBuilder();
        do {
            baseBuilder.append(this.head.toBaseString());
            currentLink = currentLink.getAfter();
        } while(currentLink != null);
        return baseBuilder.toString();
    }

    public String toString() {
        Linked<?,?,?> currentLink = this.head;
        final StringBuilder baseBuilder = new StringBuilder();
        do {
            baseBuilder.append(this.head.toString());
            currentLink = currentLink.getAfter();
        } while(currentLink != null);
        return baseBuilder.toString();
    }
}
