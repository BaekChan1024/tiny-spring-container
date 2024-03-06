package org.container;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.container.io.ResourceLoader;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader{

    private final Map<String, BeanDefinition> registry;
    private final ResourceLoader resourceLoader;

    public AbstractBeanDefinitionReader(ResourceLoader resourceLoader) {
        this.registry = new HashMap<>();
        this.resourceLoader = resourceLoader;
    }
}
