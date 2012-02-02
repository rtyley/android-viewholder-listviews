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

import static com.madgag.android.listviews.ReflectiveHolderFactory.reflectiveFactoryFor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import android.view.View;

import org.junit.Test;

/**
 * Unit tests of {@link ReflectiveHolderFactory}
 */
public class ReflectiveHolderFactoryTest {

	private static class StaticViewHolder implements ViewHolder<String> {

		public StaticViewHolder(View view) {
		}

		public void updateViewFor(String item) {
		}
	}

	private class MemberViewHolder implements ViewHolder<String> {

		public MemberViewHolder(View view) {
		}

		public void updateViewFor(String item) {
		}
	}

	private static class StringViewHolder implements ViewHolder<String> {

		final String argument;

		public StringViewHolder(View view, String argument) {
			this.argument = argument;
		}

		public void updateViewFor(String item) {
		}
	}

	private static class PrimitiveIntHolder implements ViewHolder<String> {

		final int argument;

		public PrimitiveIntHolder(View view, int argument) {
			this.argument = argument;
		}

		public void updateViewFor(String item) {
		}
	}

	private static class IntegerHolder implements ViewHolder<String> {

		final Integer argument;

		public IntegerHolder(View view, Integer argument) {
			this.argument = argument;
		}

		public void updateViewFor(String item) {
		}
	}

	private static class TwoStringViewHolder implements ViewHolder<String> {

		final String argument1;

		final CharSequence argument2;

		public TwoStringViewHolder(View view, String argument1,
				CharSequence argument2) {
			this.argument1 = argument1;
			this.argument2 = argument2;
		}

		public void updateViewFor(String item) {
		}
	}

	/**
	 * Create holder for class with single default constructor that takes a view
	 */
	@Test
	public void defaultConstructor() {
		ReflectiveHolderFactory<String> factory = reflectiveFactoryFor(
                StaticViewHolder.class, (Object[]) null);
		ViewHolder<String> holder = factory.createViewHolderFor(null);
		assertNotNull(holder);
	}

	/**
	 * Create holder for non-static member class
	 */
	@Test(expected = IllegalArgumentException.class)
	public void defaultConstructorNonStaticMemberClass() {
        reflectiveFactoryFor(MemberViewHolder.class);
	}

	/**
	 * Create holder for non-static member class
	 */
	@Test(expected = IllegalArgumentException.class)
	public void noConstructorMatchMissingParam() {
		ReflectiveHolderFactory<String> factory = reflectiveFactoryFor(
				StringViewHolder.class);
		factory.createViewHolderFor(null);
	}

	/**
	 * Create holder for non-static member class
	 */
	@Test(expected = IllegalArgumentException.class)
	public void noConstructorMatchInvalidParam() {
		ReflectiveHolderFactory<String> factory = reflectiveFactoryFor(
				StringViewHolder.class, new Object());
		factory.createViewHolderFor(null);
	}

	/**
	 * Create holder for class that takes a String constructor argument
	 */
	@Test
	public void constructorWithSingleArgument() {
		ReflectiveHolderFactory<String> factory = reflectiveFactoryFor(
				StringViewHolder.class, "abcd");
		ViewHolder<String> holder = factory.createViewHolderFor(null);
		assertNotNull(holder);
		assertEquals("abcd", ((StringViewHolder) holder).argument);
	}

	/**
	 * Create holder for class that takes a String and CharSequence constructor
	 * argument
	 */
	@Test
	public void constructorWithMultipleArgument() {
		ReflectiveHolderFactory<String> factory = reflectiveFactoryFor(
				TwoStringViewHolder.class, "a1", "b2");
		ViewHolder<String> holder = factory.createViewHolderFor(null);
		assertNotNull(holder);
		assertEquals("a1", ((TwoStringViewHolder) holder).argument1);
		assertEquals("b2", ((TwoStringViewHolder) holder).argument2);
	}

	/**
	 * Create holder for class that takes an int parameter
	 */
	@Test
	public void constructorWithPrimitiveInt() {
		ReflectiveHolderFactory<String> factory = reflectiveFactoryFor(
				PrimitiveIntHolder.class, 1);
		ViewHolder<String> holder = factory.createViewHolderFor(null);
		assertNotNull(holder);
		assertEquals(1, ((PrimitiveIntHolder) holder).argument);
	}

	/**
	 * Create holder for class that takes an {@link Integer} parameter
	 */
	@Test
	public void constructorWithInteger() {
		ReflectiveHolderFactory<String> factory = reflectiveFactoryFor(
				IntegerHolder.class, 1);
		ViewHolder<String> holder = factory.createViewHolderFor(null);
		assertNotNull(holder);
		assertEquals(Integer.valueOf(1), ((IntegerHolder) holder).argument);
	}
}
