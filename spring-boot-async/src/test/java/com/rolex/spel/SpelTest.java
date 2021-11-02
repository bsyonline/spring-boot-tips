package com.rolex.spel;

import com.sun.org.apache.xalan.internal.extensions.ExpressionContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Date;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class SpelTest {

    public static void main(String[] args) {
        // 构造解析器
        ExpressionParser parser = new SpelExpressionParser();
        // 解析器解析字符串表达式
        Expression exp = parser.parseExpression("'aaa'");
        // 获取表达式的值
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("count", 5);
        String message = exp.getValue().toString();
        System.out.println(message);
    }

}
