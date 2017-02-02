package com.angkorteam.framework.extension.wicket.extensions.markup.html.repeater.data.table.filter;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by socheat on 12/7/16.
 */
public class ItemValidator implements IValidator<String> {
    private ItemClass columnClass;
    private String format;

    public ItemValidator(ItemClass columnClass) {
        this.columnClass = columnClass;
        if (columnClass == ItemClass.Date) {
            this.format = "yyyy-MM-dd";
        } else if (columnClass == ItemClass.Time) {
            this.format = "HH:mm:ss";
        } else if (columnClass == ItemClass.DateTime) {
            this.format = "yyyy-MM-dd HH:mm:ss";
        }
    }

    private void doValidate(IValidatable<String> validatable, String value) throws IllegalArgumentException {
        if (this.columnClass == ItemClass.Boolean) {
            if (StringUtils.equalsIgnoreCase(value, "true")
                    || StringUtils.equalsIgnoreCase(value, "false")
                    || StringUtils.equalsIgnoreCase(value, "yes")
                    || StringUtils.equalsIgnoreCase(value, "no")
                    || StringUtils.equalsIgnoreCase(value, "on")
                    || StringUtils.equalsIgnoreCase(value, "off")
                    || StringUtils.equalsIgnoreCase(value, "1")
                    || StringUtils.equalsIgnoreCase(value, "0")) {
            } else {
                throw new IllegalArgumentException("invalid expression");
            }
        } else if (this.columnClass == ItemClass.Byte) {
            try {
                Byte.valueOf(value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("invalid expression");
            }
        } else if (this.columnClass == ItemClass.Short) {
            try {
                Short.valueOf(value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("invalid expression");
            }
        } else if (this.columnClass == ItemClass.Integer) {
            try {
                Integer.valueOf(value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("invalid expression");
            }
        } else if (this.columnClass == ItemClass.Long) {
            try {
                Long.valueOf(value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("invalid expression");
            }
        } else if (this.columnClass == ItemClass.Float) {
            try {
                Float.valueOf(value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("invalid expression");
            }
        } else if (this.columnClass == ItemClass.Double) {
            try {
                Double.valueOf(value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("invalid expression");
            }
        } else if (this.columnClass == ItemClass.Time
                || this.columnClass == ItemClass.Date
                || this.columnClass == ItemClass.DateTime) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(this.format);
                dateFormat.parse(value);
            } catch (ParseException e) {
                throw new IllegalArgumentException("invalid expression");
            }
        }
    }

    @Override
    public void validate(IValidatable<String> validatable) {
        String filter = validatable.getValue();
        try {
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
                        throw new IllegalArgumentException("invalid expression");
                    } else {
                        doValidate(validatable, expression.getFirstOperand());
                    }
                } else if (expression.getOperator() == Operator.Between) {
                    if (expression.getFirstOperand() == null || "".equals(expression.getFirstOperand()) || expression.getFirstOperand() == null || "".equals(expression.getFirstOperand())) {
                        throw new IllegalArgumentException("invalid expression");
                    } else {
                        doValidate(validatable, expression.getFirstOperand());
                        doValidate(validatable, expression.getSecondOperand());
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            validatable.error(new ValidationError(e.getMessage()));
        }
    }
}
