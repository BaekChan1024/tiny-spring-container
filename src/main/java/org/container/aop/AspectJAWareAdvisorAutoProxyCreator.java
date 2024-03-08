package org.container.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.container.bean.BeanPostProcessor;
import org.container.bean.factory.AbstractBeanFactory;
import org.container.bean.factory.BeanFactory;

import java.util.List;

public class AspectJAWareAdvisorAutoProxyCreator implements BeanPostProcessor, BeanFactoryAware {
    private AbstractBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = (AbstractBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
        if (bean instanceof AspectJExpressionPointcutAdvisor) {
            return bean;
        }
        if (bean instanceof MethodInterceptor) {
            return bean;
        }
        List<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeanForType(AspectJExpressionPointcutAdvisor.class);
        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            if (advisor.getPointcut().getClassFilter().matches(bean.getClass())) {
                ProxyFactory advisedSupport = new ProxyFactory();
                advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
                advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());

                TargetSource targetSource = new TargetSource(bean, bean.getClass(), bean.getClass().getInterfaces());
                advisedSupport.setTargetSource(targetSource);
                return advisedSupport.getProxy();
            }
        }
        return bean;
    }
}
