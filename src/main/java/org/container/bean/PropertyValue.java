package org.container.bean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PropertyValue {

    private final String name;
    private final Object value;
}