package org.container.aop;

import org.aopalliance.aop.Advice;

public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor{

    private final AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    private Advice advice;

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    public void setExpression(String expression) {
        this.pointcut.setExpression(expression);
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }
}
