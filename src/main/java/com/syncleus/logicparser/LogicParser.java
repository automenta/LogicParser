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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogicParser {
    private final static Pattern TOKEN_PATTERN = Pattern.compile("([a-zA-Z0-9]{1,})");
    private Linked head;

    public LogicParser(final String logicString) {
        this.head = new LinkedString(null, null, removeWhitespace(logicString));

        this.parse();
    }

    private static String removeWhitespace(final String input) {
        return input.replaceAll("\\s+","");
    }

    private void parse() {
        Matcher match = TOKEN_PATTERN.matcher(toBaseString());
        while(match.find()) {
            this.extractToken(match.start(), match.end());
            match = TOKEN_PATTERN.matcher(toBaseString());
        }
    }

    private void extractToken(final int start, final int end) {
        Linked currentLink = this.head;
        int currentIndex = 0;
        while(currentLink.toBaseString().length() + currentIndex < start) {
            currentIndex += currentLink.toBaseString().length();
            currentLink = currentLink.getAfter();
            if(currentLink == null)
                throw new IllegalArgumentException("start argument is out of bounds");
        }

        if(!(currentLink instanceof LinkedString))
            throw new IllegalStateException("Current link is not a LinkedString!");

        final String baseString = currentLink.toBaseString();
        if(baseString.length() < end - currentIndex)
            throw new IllegalArgumentException("end argument is out of bounds.");

        final int relativeStart = start - currentIndex;
        final int relativeEnd = end - currentIndex;
        final String beforeString = (relativeStart == 0 ? null : baseString.substring(0, relativeStart));
        final String tokenString = baseString.substring(relativeStart, relativeEnd);
        final String afterString = (relativeEnd == baseString.length() ? null : baseString.substring(relativeEnd, baseString.length()));

        final LinkedToken tokenLink = new LinkedToken(null, null, new StringToken(tokenString));
        LinkedString afterLink;
        if( afterString != null) {
            afterLink = new LinkedString(tokenLink, currentLink.getAfter(), afterString);
            if( currentLink.getAfter() != null )
                currentLink.getAfter().setBefore(afterLink);
            tokenLink.setAfter(afterLink);
        }
        else {
            if( currentLink.getAfter() != null )
                currentLink.getAfter().setBefore(tokenLink);
            tokenLink.setAfter(currentLink.getAfter());
        }

        if(beforeString != null) {
            currentLink.setAfter(tokenLink);
            tokenLink.setBefore(currentLink);
            currentLink.setValue(beforeString);
        }
        else {
            if( currentLink.getBefore() == null ) {
                tokenLink.setBefore(null);
                this.head = tokenLink;
            }
            else {
                tokenLink.setBefore(currentLink.getBefore());
                currentLink.getBefore().setAfter(tokenLink);
            }
        }
    }

    protected Linked getHead() {
        return head;
    }

    protected String toBaseString() {
        Linked currentLink = this.head;
        final StringBuilder baseBuilder = new StringBuilder();
        do {
            baseBuilder.append(currentLink.toBaseString());
            currentLink = currentLink.getAfter();
        } while(currentLink != null);
        return baseBuilder.toString();
    }

    public String toString() {
        Linked currentLink = this.head;
        final StringBuilder baseBuilder = new StringBuilder();
        do {
            baseBuilder.append(this.head.toString());
            currentLink = currentLink.getAfter();
        } while(currentLink != null);
        return baseBuilder.toString();
    }
}
