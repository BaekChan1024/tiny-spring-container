package org.container.aop;

public interface PointcutAdvisor extends Advisor {

    Pointcut getPointcut();
}
