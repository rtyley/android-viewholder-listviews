/*
 * Copyright 2011 Kevin Sawicki <kevinsawicki@gmail.com>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
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

    public static <T> ReflectiveHolderFactory<T> reflectiveFactoryFor(
            Class<? extends ViewHolder<T>> holderClass,
            Object... args) {
        return new ReflectiveHolderFactory<T>(holderClass, args);
    }

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
	private ReflectiveHolderFactory(final Class<? extends ViewHolder<T>> holder,
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
