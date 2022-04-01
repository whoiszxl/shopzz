package com.whoiszxl.ddd;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Component
public @interface DomainAbility {

    @AliasFor(annotation = Component.class, attribute = "value") String value() default "";

    /**
     * 所属业务域.
     */
    String domain();

    /**
     * 能力名称.
     */
    String name() default "";

}
