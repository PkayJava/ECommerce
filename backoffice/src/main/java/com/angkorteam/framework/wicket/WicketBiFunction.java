package com.angkorteam.framework.wicket;

import org.apache.wicket.util.io.IClusterable;

import java.util.function.BiFunction;

public interface WicketBiFunction<T, U, R> extends BiFunction<T, U, R>, IClusterable {
}
