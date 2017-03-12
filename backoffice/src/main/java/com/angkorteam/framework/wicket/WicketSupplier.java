package com.angkorteam.framework.wicket;

import org.apache.wicket.util.io.IClusterable;

import java.util.function.Supplier;

public interface WicketSupplier<T> extends Supplier<T>, IClusterable {
}
