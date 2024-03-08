package org.container.aop;

public interface ClassFilter {

    boolean matches(Class<?> targetClass);
}
