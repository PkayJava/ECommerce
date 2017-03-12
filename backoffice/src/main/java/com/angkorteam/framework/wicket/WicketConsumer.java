package com.angkorteam.framework.wicket;

import org.apache.wicket.util.io.IClusterable;

import java.util.function.Consumer;

public interface WicketConsumer<T> extends Consumer<T>, IClusterable {
}
