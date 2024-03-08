package org.container.aop;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;
import org.aspectj.weaver.tools.ShadowMatch;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;

public class AspectJExpressionPointcut implements Pointcut, ClassFilter, MethodMatcher {

    private final PointcutParser pointcutParser;
    private String expression;
    private PointcutExpression pointcutExpression;
    private static final Set<PointcutPrimitive> DEFAULT_SUPPORTED_PRIMITIVES = Set.of(
        PointcutPrimitive.EXECUTION, PointcutPrimitive.ARGS, PointcutPrimitive.REFERENCE, PointcutPrimitive.THIS,
            PointcutPrimitive.TARGET, PointcutPrimitive.WITHIN, PointcutPrimitive.AT_ANNOTATION, PointcutPrimitive.AT_WITHIN,
            PointcutPrimitive.AT_ARGS, PointcutPrimitive.AT_TARGET
    );

    public AspectJExpressionPointcut() {
        this(DEFAULT_SUPPORTED_PRIMITIVES);
    }

    public AspectJExpressionPointcut(Set<PointcutPrimitive> supportedPrimitives) {
        pointcutParser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingContextClassloaderForResolution(supportedPrimitives);
    }

    protected void checkReadyToMatch() {
        if (Objects.isNull(pointcutExpression)) {
            pointcutExpression = buildPointcutExpression();
        }
    }

    private PointcutExpression buildPointcutExpression() {
        return pointcutParser.parsePointcutExpression(expression);
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public boolean matches(Class<?> targetClass) {
        checkReadyToMatch();
        return pointcutExpression.couldMatchJoinPointsInType(targetClass);
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        checkReadyToMatch();
        ShadowMatch shadowMatch = pointcutExpression.matchesAdviceExecution(method);
        if (shadowMatch.alwaysMatches()) {
            return true;
        }
        if (shadowMatch.neverMatches()) {
            return false;
        }
        return false;
    }

    @Override
    public ClassFilter getClassFilter() {
        return this;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
