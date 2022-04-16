package com.example.api.resolver.paging;

import javax.validation.constraints.NotEmpty;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Sorting {

    String SORTING_DEFAULT_NAME_PARAMETER = "sortField";
    String SORTING_DEFAULT_DIRECTION_PARAMETER = "sortDirection";
    String SORTING_DEFAULT_DIRECTION_VALUE = "asc";

    @NotEmpty
    String nameParam() default SORTING_DEFAULT_NAME_PARAMETER;

    @NotEmpty
    String directionParam() default SORTING_DEFAULT_DIRECTION_PARAMETER;

    @NotEmpty
    String defaultName();

    @NotEmpty
    String defaultDirection() default SORTING_DEFAULT_DIRECTION_VALUE;

    SortField[] availableFields() default {};

}
