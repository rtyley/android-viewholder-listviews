/*
 * Copyright (c) 2011 Kevin Sawicki <kevinsawicki@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */
package com.madgag.android.listviews;

import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

/**
 * {@link ViewHolderFactory} that uses reflection to create new
 * {@link ViewHolder} instances with the given parameters that are injected into
 * each {@link ViewHolder} created.
 * 
 * @param <T>
 *            model class that views are bound to
 */
public class ReflectiveHolderFactory<T> implements ViewHolderFactory<T> {

	private static Constructor<?> findMatch(final Class<?> clazz,
			final Class<?>[] types) {
		Constructor<?>[] candidates = clazz.getConstructors();
		for (Constructor<?> candidate : candidates) {
			Class<?>[] params = candidate.getParameterTypes();
			if (params.length != types.length)
				continue;
			boolean match = true;
			for (int i = 0; i < types.length; i++)
				if (!params[i].isAssignableFrom(types[i])) {
					match = false;
					break;
				}
			if (match)
				return candidate;
		}
		return null;
	}

	private final Constructor<? extends ViewHolder<T>> constructor;

	private final Object[] args;

	/**
	 * Create holder factory for constructor and arguments
	 * 
	 * @param holder
	 * @param args
	 * @throws RuntimeException
	 */
	@SuppressWarnings("unchecked")
	public ReflectiveHolderFactory(final Class<? extends ViewHolder<T>> holder,
			Object... args) {
		if (args == null)
			args = new Object[0];

		if (holder.isMemberClass() && !Modifier.isStatic(holder.getModifiers()))
			throw new IllegalArgumentException(
					"Non-static member classes not supported");

		Class<?>[] types = new Class[args.length + 1];
		// First argument must be the view itself
		types[0] = View.class;
		for (int i = 0; i < args.length; i++)
			types[i + 1] = args[i].getClass();

		constructor = (Constructor<? extends ViewHolder<T>>) findMatch(holder,
				types);
		if (constructor == null)
			throw new IllegalArgumentException(
					"No constructor found to bind arguments to");
		this.args = new Object[args.length + 1];
		System.arraycopy(args, 0, this.args, 1, args.length);
	}

	public ViewHolder<T> createViewHolderFor(final View view) {
		try {
			args[0] = view;
			return constructor.newInstance(args);
		} catch (InstantiationException e) {
			throw new IllegalArgumentException(e);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		} catch (InvocationTargetException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
