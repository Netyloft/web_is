package com.example.jpa.configuration.hibernate;

import org.hibernate.QueryException;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.BooleanType;
import org.hibernate.type.Type;

import java.util.List;

public class PostgreSQLJsonbSearchFunction implements SQLFunction {

    @Override
    public String render(Type firstArgumentType, List arguments, SessionFactoryImplementor factory) throws QueryException {
        if (arguments.size() != 2) {
            throw new IllegalArgumentException("The function must be passed 2 arguments");
        }

        String jsonbField = (String) arguments.get(0);
        String jsonbSearch = (String) arguments.get(1);

        return jsonbField + " @> " + jsonbSearch;
    }

    @Override
    public Type getReturnType(Type columnType, Mapping mapping) throws QueryException {
        return new BooleanType();
    }

    @Override
    public boolean hasArguments() {
        return true;
    }

    @Override
    public boolean hasParenthesesIfNoArguments() {
        return false;
    }

}
