package com.example.api.resolver.filter;

import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Filter {

    @NotNull
    FilterSource source() default FilterSource.QUERY;

    @NotEmpty
    String name();

    @NotEmpty
    String field();

    @NotNull
    FilterValueType type() default FilterValueType.STRING;

    @NotNull
    FilterOperatorType operation() default FilterOperatorType.IN;

    Class<? extends Enum>[] enumerate() default {};

    String property() default "";

    boolean required() default true;

    boolean manual() default false;

    @NotEmpty
    String example();

    String description() default StringUtils.EMPTY;

    boolean isConditionGroup() default false;

    int conditionGroupId() default 0;

}
