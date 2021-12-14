package com.tl.annotiion;

import java.lang.annotation.*;
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})

@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Qualifier {
    String value() default "";
}
