package com.itzstonlex.executionlib.declare.provider;

import java.lang.reflect.Field;

public interface DeclaredField extends DeclaredType {

    Field asField();

    void set(Object value);
}
