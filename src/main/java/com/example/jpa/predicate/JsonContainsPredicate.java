package com.example.jpa.predicate;

import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.ParameterRegistry;
import org.hibernate.query.criteria.internal.Renderable;
import org.hibernate.query.criteria.internal.compile.RenderingContext;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.AbstractSimplePredicate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;

public class JsonContainsPredicate<E> extends AbstractSimplePredicate {

    private final Expression<String> expression;
    private final Path<E> path;
    private final String functionName;

    public JsonContainsPredicate(CriteriaBuilder cb, Path<E> path, Expression<String> expression, String functionName) {
        super((CriteriaBuilderImpl) cb);
        this.path = path;
        this.expression = expression;
        this.functionName = functionName;
    }

    @Override
    public void registerParameters(ParameterRegistry registry) {
        Helper.possibleParameter(path, registry);
        Helper.possibleParameter(expression, registry);
    }

    @Override
    public String render(boolean isNegated, RenderingContext renderingContext) {
        String field = ((Renderable) path).render(renderingContext);
        String value = ((LiteralExpression<String>) expression).getLiteral();

        return "function ( '" + functionName + "', " + field + ", " + value + " ) = true";
    }
}
