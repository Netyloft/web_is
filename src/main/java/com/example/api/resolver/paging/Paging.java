package com.example.api.resolver.paging;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Paging {

    String OFFSET_DEFAULT_PARAMETER = "startIndex";
    int OFFSET_DEFAULT_VALUE = 0;

    String LIMIT_DEFAULT_PARAMETER = "pageSize";
    int LIMIT_DEFAULT_MIN = 1;
    int LIMIT_DEFAULT_MAX = 50;
    int LIMIT_DEFAULT_VALUE = 20;

    @NotEmpty
    String offsetParam() default OFFSET_DEFAULT_PARAMETER;

    @NotNull
    int offsetDefault() default OFFSET_DEFAULT_VALUE;

    @NotEmpty
    String limitParam() default LIMIT_DEFAULT_PARAMETER;

    @NotNull
    int limitDefault() default LIMIT_DEFAULT_VALUE;

    @NotNull
    int limitMin() default LIMIT_DEFAULT_MIN;

    @NotNull
    int limitMax() default LIMIT_DEFAULT_MAX;

    @NotEmpty
    Sorting sorting();

}
