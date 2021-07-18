package math;


import net.objecthunter.exp4j.ExpressionBuilder;

import net.objecthunter.exp4j.Expression;

public class FormulaInterpretator {
    public static double calculate(String formula, double x){
        Expression calc = new ExpressionBuilder(formula)
                .variables("x")
                .build()
                .setVariable("x", x);
        return calc.evaluate();
    }
}
