package com.angkorteam.framework.extension.wicket;

import org.apache.wicket.util.io.IClusterable;

import java.util.function.BiConsumer;

public interface WicketBiConsumer<T, U> extends BiConsumer<T, U>, IClusterable {
}