package com.syncleus.logicparser;


import com.syncleus.aidelogic.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParserTest {

    private final static String FULL_LOGIC_STRING = "foo=(you|me|I)&(bar>foo+20)|(something=x|you|z)";
    private AideLogicParser p;

    // Foo=(some|thing|here)&&(Bar>20||Bar<Foo+2)

    // a=(x|y|z) is like (a=x)||(a=y)||(a=z)

    @Before public void setup() {
        p = AideLogicParser.get();
    }

    @Test
    public void testRule() {

        for (String yEqualsXStr : new String[] { "y=x", "y = x", "y= x"  }) {
            final AideRule yEqualsX = p.parseRule(yEqualsXStr);

            assertEquals(Assignment.class,
                    yEqualsX.getClass());
            assertEquals("y",
                    ((Assignment) yEqualsX).assigned.toString());
            assertEquals("x",
                    ((Assignment) yEqualsX).value.toString());
        }
    }

    @Test
    public void testVariable() {

        assertEquals(Variable.class, p.parseExpression("x").getClass());
        assertEquals("x", ((Variable)p.parseExpression("x")).name);

    }

    @Test
    public void testBinary() {

        for (String z : new String[] { "z=x&y", "z=(x&y)", "z = x & y", "z=(x)&(y)"  }) {
            final Assignment zEquals = p.parseRule(z);
            assertEquals(Op.And.class, zEquals.value.getClass());
            assertEquals("x", ((Op.And) zEquals.value).a.toString());
            assertEquals("y", ((Op.And) zEquals.value).b.toString());
        }


        assertEquals(Op.Or.class, ((Assignment)p.parseRule("z=(x|y)")).value.getClass());



        //assertEquals(Op.And.class, p.parseExpression("x&y").getClass());

    }

}
