package com.syncleus.aidelogic;


import com.github.fge.grappa.Grappa;
import com.github.fge.grappa.annotations.Cached;
import com.github.fge.grappa.matchers.MatcherType;
import com.github.fge.grappa.matchers.NothingMatcher;
import com.github.fge.grappa.matchers.base.AbstractMatcher;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;
import com.github.fge.grappa.run.ListeningParseRunner;
import com.github.fge.grappa.run.ParseRunner;
import com.github.fge.grappa.run.ParsingResult;
import com.github.fge.grappa.run.context.MatcherContext;

public class AideLogicParser extends BaseParser<Object> {

    private static final ThreadLocal<AideLogicParser> parsers = new ThreadLocal();

    private final ParseRunner ruleParser = new ListeningParseRunner(AideRule());
    private final ParseRunner exprParser = new ListeningParseRunner(Expression());

    public static AideLogicParser get() {
        AideLogicParser p = parsers.get();
        if (p == null) parsers.set(p = Grappa.createParser(AideLogicParser.class));
        return p;
    }

    public static class InvalidParseException extends RuntimeException {
        public InvalidParseException(CharSequence s, ParsingResult pr) {
            super("Invalid parse: " + s + ", result: " + pr.getValueStack());
        }
    }
    public static <R extends AideRule> R parseRule(CharSequence s/*, Set<Variable> allowedVariables*/) {
        ParsingResult pr = get().ruleParser.run(s);
        if (pr.isSuccess()) {
            Object o = pr.getTopStackValue();
            if (o instanceof AideRule)
                return ((R) o);
        }

        throw new InvalidParseException(s, pr);
    }
    public static Evaluable parseExpression(CharSequence s/*, Set<Variable> allowedVariables*/) {
        ParsingResult pr = get().exprParser.run(s);

        //System.out.println("parse: '" + s + "' -> resulting stack: " + pr.getValueStack());

        if (pr.isSuccess()) {
            Object o = pr.getTopStackValue();
            if (o instanceof Evaluable)
                return ((Evaluable) o);
        }

        throw new InvalidParseException(s, pr);
    }

    public Rule AideRule() {
        //firstOf(...
        return Assignment();
    }

    public Rule Assignment() {
        return sequence(
                Variable(), s(), '=', s(), Expression(),
                swap(),
                push(new Assignment((Variable)pop(), (Evaluable)pop()))
        );
    }

    public Rule Expression() {
        return Expression(true);
    }

    public Rule Expression(boolean unwrapped) {
        return firstOf(
                unwrapped ? Infix('|', Op.Or.class) : nothing(),
                unwrapped ? Infix('&', Op.And.class) : nothing(),
                SubExpression(),
                Variable(),
                Numeric()
        );
    }


    public Rule SubExpression() {
        return sequence('(', s(), Expression(true), s(), ')');
    }

    @Cached
    public Rule Infix(char symbol, Class<? extends Op> binaryOperator) {
        return sequence(
                Expression(false), s(), symbol, s(), Expression(false),
                swap(),
                push(binary(binaryOperator, (Evaluable) pop(), (Evaluable) pop()) )
        );
    }

    public static Op binary(Class<? extends Op> operator, Evaluable a, Evaluable b) {

        try {
            Op o = operator.newInstance();
            o.init(a, b);
            return o;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Rule Numeric() {
        return Number();
    }

    Rule Integer() {
        return sequence(
                oneOrMore(digit()),
                push(Integer.parseInt(matchOrDefault("NaN")))
        );
    }

    Rule Number() {
        return sequence( Number(), push(
                //TODO use a Constant term for this
                new Variable(
                    Double.toString(  ((Number)pop()).doubleValue()  )
                )
        ));
    }
    Rule NumberRaw() {

        return sequence(
                sequence(
                        optional('-'),
                        oneOrMore(digit()),
                        optional('.', oneOrMore(digit()))
                ),
                push(Float.parseFloat(matchOrDefault("NaN")))
        );
    }


    public Rule Variable() {
        return sequence(
                new ValidVariableMatcher(),
                push(new Variable( match() ))
        );

    }

    Rule nothing() {
        return new NothingMatcher();
    }

    public final class ValidVariableMatcher extends AbstractMatcher
    {

        public ValidVariableMatcher() {
            super("'ValidVariableMatcher'");
        }

        public MatcherType getType()
        {
            return MatcherType.TERMINAL;
        }

        public <V> boolean match(MatcherContext<V> context) {
            int count = 0;
            int max= context.getInputBuffer().length() - context.getCurrentIndex();

            while (count < max && isValidAtomChar(count, context.getCurrentChar())) {
                context.advanceIndex(1);
                count++;
            }

            return count > 0;
        }
    }

    public static boolean isValidAtomChar(int position, char c) {
        if (position == 0) return Character.isAlphabetic(c);
        else {
            switch (c) {
                case ' ':
                case '\n':
                case '\t':
                case '=':
                case '&':
                case '|':
                case '(':
                case ')':
                    return false;

            }
        }
        return true;
    }

    Rule s() {
        return zeroOrMore(anyOf(" \t\f\n"));
    }


}
