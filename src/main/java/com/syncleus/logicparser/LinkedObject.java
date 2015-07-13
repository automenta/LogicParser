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

public abstract class LinkedObject<B extends Linked<?,?,?>, A extends Linked<?,?,?>, V> implements Linked<B,A,V> {
    private B before;
    private A after;
    private V value;

    public LinkedObject(final B before, final A after, final V value) {
        this.before = before;
        this.after = after;
        this.value = value;
    }

    @Override
    public B getBefore() {
        return before;
    }

    @Override
    public A getAfter() {
        return after;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public void setBefore(final B before) {
        this.before = before;
    }

    @Override
    public void setAfter(final A after) {
        this.after = after;
    }

    @Override
    public void setValue(final V value) {
        this.value = value;
    }
}
