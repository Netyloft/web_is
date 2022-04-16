package com.example.jpa.configuration.hibernate;

import org.hibernate.dialect.PostgreSQL95Dialect;

public class PostgreSQLPromoDialect extends PostgreSQL95Dialect {

    public static final String FUNCTION_JSONB_SEARCH = "jsonb_search";
    public static final String FUNCTION_JSONB_SEARCH_STRING = "jsonb_search_string";
    public static final String FUNCTION_JSONB_GENERIC_PROPERTIES = "jsonb_generic_properties";

    public PostgreSQLPromoDialect() {
        super();
        registerFunction(FUNCTION_JSONB_SEARCH, new PostgreSQLJsonbSearchFunction());
        registerFunction(FUNCTION_JSONB_SEARCH_STRING, new PostgreSQLJsonbSearchStringFunction());
        registerFunction(FUNCTION_JSONB_GENERIC_PROPERTIES, new PostgreSQLJsonbGenericPropertiesFunction());
    }

}
