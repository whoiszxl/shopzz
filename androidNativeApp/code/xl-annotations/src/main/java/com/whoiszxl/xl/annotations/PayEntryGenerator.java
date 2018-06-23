package com.whoiszxl.xl.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author whoiszxl
 */
@Target(ElementType.TYPE) //作用在類上
@Retention(RetentionPolicy.SOURCE) //源碼階段處理
public @interface PayEntryGenerator {

    String packageName();

    Class<?> payEntryTemplate();
}
