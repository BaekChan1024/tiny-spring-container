package org.container.aop;

import org.container.bean.factory.BeanFactory;

public interface BeanFactoryAware {

    void setBeanFactory(BeanFactory beanFactory);
}
