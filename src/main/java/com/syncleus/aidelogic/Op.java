package com.syncleus.aidelogic;


abstract public class Op extends Evaluable {

    abstract public void init(Evaluable... subterms);

    public abstract static class BinaryOp extends Op {

        public Evaluable a;
        public Evaluable b;

        @Override
        public void init(Evaluable... subterms) {

            if (subterms.length!=2)
                throw new RuntimeException(this + " requires 2 subterms");

            this.a = subterms[0];
            this.b = subterms[1];
        }

    }

    public static class Or extends BinaryOp {

    }

    public static class And extends BinaryOp {

    }

}
