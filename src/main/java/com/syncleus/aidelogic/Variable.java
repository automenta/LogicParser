package com.syncleus.aidelogic;

/**
 * Created by me on 7/14/15.
 */
public class Variable extends Evaluable {

    public final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    //    public static class BooleanVariable extends Variable {
//
//        public BooleanVariable(String name) {
//            super(name);
//        }
//    }
//
//    public static class NumericVariable extends Variable {
//
//        public NumericVariable(String name) {
//            super(name);
//        }
//    }

    //EnumVariable

    //..

}
