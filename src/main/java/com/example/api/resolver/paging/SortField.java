package com.example.api.resolver.paging;

import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotEmpty;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface SortField {

    @NotEmpty
    String name();

    @NotEmpty
    String field() default StringUtils.EMPTY;

}
