package com.angkorteam.framework.extension.wicket.validation;

import com.angkorteam.framework.extension.wicket.extensions.markup.html.repeater.data.table.filter.Expression;
import com.angkorteam.framework.extension.wicket.extensions.markup.html.repeater.data.table.filter.Operator;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import java.lang.reflect.InvocationTargetException;
import java.security.Timestamp;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Validator<H> implements IValidator<H> {

    private Class<?> clazz;

    private String format;

    public Validator(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Validator(Class<?> clazz, String format) {
        this.clazz = clazz;
        this.format = format;
    }

    @Override
    public void validate(IValidatable<H> validatable) {
        String filter = (String) validatable.getValue();

        if (filter != null && !"".equals(filter)) {
            filter = filter.trim();
            Expression expression = Expression.parse(filter);
            if (expression.getOperator() == Operator.Equal
                    || expression.getOperator() == Operator.Like
                    || expression.getOperator() == Operator.LessThan
                    || expression.getOperator() == Operator.LessThanOrEqual
                    || expression.getOperator() == Operator.GreaterThan
                    || expression.getOperator() == Operator.GreaterThanOrEqual) {
                if (expression.getFirstOperand() == null || "".equals(expression.getFirstOperand())) {
                    validatable.error(new ValidationError("invalid expression"));
                } else {
                    if (Number.class.isAssignableFrom(clazz)) {
                        try {
                            try {
                                MethodUtils.invokeStaticMethod(clazz, "valueOf", expression.getFirstOperand());
                            } catch (NoSuchMethodException e) {
                            } catch (IllegalAccessException e) {
                            } catch (InvocationTargetException e) {
                                if (e.getTargetException() instanceof NumberFormatException) {
                                    throw (NumberFormatException) e.getTargetException();
                                }
                            }
                        } catch (NumberFormatException e) {
                            validatable.error(new ValidationError("invalid expression"));
                        }
                    } else if (clazz == Timestamp.class || clazz == Time.class || clazz == Date.class || clazz == Date.class) {
                        try {
                            SimpleDateFormat dateFormat = new SimpleDateFormat(this.format);
                            dateFormat.parse(expression.getFirstOperand());
                        } catch (ParseException e) {
                            validatable.error(new ValidationError("invalid expression"));
                        }
                    }
                }
            } else if (expression.getOperator() == Operator.Between) {
                if (expression.getFirstOperand() == null || "".equals(expression.getFirstOperand()) || expression.getFirstOperand() == null || "".equals(expression.getFirstOperand())) {
                    validatable.error(new ValidationError("invalid expression"));
                } else {
                    if (Number.class.isAssignableFrom(clazz)) {
                        try {
                            try {
                                MethodUtils.invokeStaticMethod(clazz, "valueOf", expression.getFirstOperand());
                                MethodUtils.invokeStaticMethod(clazz, "valueOf", expression.getSecondOperand());
                            } catch (NoSuchMethodException e) {
                            } catch (IllegalAccessException e) {
                            } catch (InvocationTargetException e) {
                                if (e.getTargetException() instanceof NumberFormatException) {
                                    throw (NumberFormatException) e.getTargetException();
                                }
                            }
                        } catch (NumberFormatException e) {
                            validatable.error(new ValidationError("invalid expression"));
                        }
                    } else if (clazz == Timestamp.class || clazz == Time.class || clazz == Date.class || clazz == Date.class) {
                        try {
                            SimpleDateFormat dateFormat = new SimpleDateFormat(this.format);
                            dateFormat.parse(expression.getFirstOperand());
                            dateFormat.parse(expression.getSecondOperand());
                        } catch (ParseException e) {
                            validatable.error(new ValidationError("invalid expression"));
                        }
                    }
                }
            }
        }
    }
}