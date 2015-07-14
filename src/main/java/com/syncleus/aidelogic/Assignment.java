package com.syncleus.aidelogic;

/**
 * Created by me on 7/14/15.
 */
public class Assignment extends AideRule {
    public final Variable assigned;
    public final Evaluable value;

    public Assignment(Variable assigned, Evaluable expr) {
        this.assigned = assigned;
        this.value = expr;
    }
}
