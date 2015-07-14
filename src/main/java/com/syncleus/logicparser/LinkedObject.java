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

public abstract class LinkedObject implements Linked {
    private Linked before;
    private Linked after;
    private Object value;

    public LinkedObject(final Linked before, final Linked after, final Object value) {
        this.before = before;
        this.after = after;
        this.value = value;
    }

    @Override
    public Linked getBefore() {
        return before;
    }

    @Override
    public Linked getAfter() {
        return after;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setBefore(final Linked before) {
        this.before = before;
    }

    @Override
    public void setAfter(final Linked after) {
        this.after = after;
    }

    @Override
    public void setValue(final Object value) {
        this.value = value;
    }
}
