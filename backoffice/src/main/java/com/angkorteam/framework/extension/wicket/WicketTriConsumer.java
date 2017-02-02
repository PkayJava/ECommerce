package com.angkorteam.framework.extension.wicket;

import org.apache.wicket.util.io.IClusterable;

import java.util.Objects;

/**
 * Represents an operation that accepts two input arguments and returns no
 * result.  This is the two-arity specialization of {@link Consumer}.
 * Unlike most other functional interfaces, {@code BiConsumer} is expected
 * to operate via side-effects.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(Object, Object)}.
 *
 * @param <A> the type of the first argument to the operation
 * @param <B> the type of the second argument to the operation
 * @see java.util.function.Consumer
 * @since 1.8
 */
@FunctionalInterface
public interface WicketTriConsumer<A, B, C> extends IClusterable {

    /**
     * Performs this operation on the given arguments.
     *
     * @param a the first input argument
     * @param b the second input argument
     * @param c the third input argument
     */
    void accept(A a, B b, C c);

    /**
     * Returns a composed {@code BiConsumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code BiConsumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    default WicketTriConsumer<A, B, C> andThen(WicketTriConsumer<? super A, ? super B, ? super C> after) {
        Objects.requireNonNull(after);

        return (l, r, p) -> {
            accept(l, r, p);
            after.accept(l, r, p);
        };
    }
}