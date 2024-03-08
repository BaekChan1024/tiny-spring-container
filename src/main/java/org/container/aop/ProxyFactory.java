package org.container.aop;

public class ProxyFactory extends AdvisedSupport implements AopProxy{

    @Override
    public Object getProxy() {
        return createAopProxy().getProxy();
    }

    protected final AopProxy createAopProxy() {
        return new CglibToAopProxy(this);
    }
}
