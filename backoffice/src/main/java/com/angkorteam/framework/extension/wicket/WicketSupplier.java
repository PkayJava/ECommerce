package com.angkorteam.framework.extension.wicket;

import org.apache.wicket.util.io.IClusterable;

import java.util.function.Supplier;

public interface WicketSupplier<T> extends Supplier<T>, IClusterable {
}
