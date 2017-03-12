package com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class Expression implements Serializable {

    private String firstOperand;

    private Operator operator;

    private String secondOperand;

    private String filter;

    public String getFirstOperand() {
        return firstOperand;
    }

    public Operator getOperator() {
        return operator;
    }

    public String getSecondOperand() {
        return secondOperand;
    }

    public String getFilter() {
        return filter;
    }

    private Expression() {
    }

    public static Expression parse(String filter) {
        Operator operator = null;
        Expression expression = new Expression();
        expression.filter = filter;
        filter = StringUtils.trimToEmpty(filter);
        if (StringUtils.contains(filter, "= ")) {
            operator = Operator.Equal;
            expression.firstOperand = StringUtils.trimToEmpty(StringUtils.substring(filter, 2));
        } else if (StringUtils.contains(filter, "!= ")) {
            operator = Operator.NotEqual;
            expression.firstOperand = StringUtils.trimToEmpty(StringUtils.substring(filter, 3));
        } else if (StringUtils.startsWithIgnoreCase(filter, "like ")) {
            operator = Operator.Like;
            expression.firstOperand = StringUtils.trimToEmpty(StringUtils.substring(filter, 5));
        } else if (StringUtils.contains(filter, "> ")) {
            operator = Operator.GreaterThan;
            expression.firstOperand = StringUtils.trimToEmpty(StringUtils.substring(filter, 2));
        } else if (StringUtils.contains(filter, ">= ")) {
            operator = Operator.GreaterThanOrEqual;
            expression.firstOperand = StringUtils.trimToEmpty(StringUtils.substring(filter, 2));
        } else if (StringUtils.contains(filter, "< ")) {
            operator = Operator.LessThan;
            expression.firstOperand = StringUtils.trimToEmpty(StringUtils.substring(filter, 2));
        } else if (StringUtils.contains(filter, "<= ")) {
            operator = Operator.LessThanOrEqual;
            expression.firstOperand = StringUtils.trimToEmpty(StringUtils.substring(filter, 3));
        } else if (StringUtils.containsIgnoreCase(filter, " to ")) {
            int mid = StringUtils.indexOfIgnoreCase(filter, " to ");
            operator = Operator.Between;
            expression.firstOperand = StringUtils.trimToEmpty(StringUtils.substring(filter, 0, mid));
            expression.secondOperand = StringUtils.trimToEmpty(StringUtils.substring(filter, mid + 4));
        } else if (StringUtils.containsIgnoreCase(filter, " and ")) {
            int mid = StringUtils.indexOfIgnoreCase(filter, " and ");
            operator = Operator.Between;
            expression.firstOperand = StringUtils.trimToEmpty(StringUtils.substring(filter, 0, mid));
            expression.secondOperand = StringUtils.trimToEmpty(StringUtils.substring(filter, mid + 5));
        } else {
            if (StringUtils.contains(filter, "%")) {
                operator = Operator.Like;
                expression.firstOperand = filter;
            } else {
                operator = Operator.Equal;
                expression.firstOperand = filter;
            }
        }
        expression.operator = operator;
        return expression;
    }
}