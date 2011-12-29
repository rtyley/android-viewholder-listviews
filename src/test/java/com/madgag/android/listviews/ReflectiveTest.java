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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import android.view.View;

import org.junit.Test;

/**
 * Unit tests of {@link ReflectiveHolderFactory}
 */
public class ReflectiveTest {

	private static class StaticViewHolder extends BaseViewHolder<String> {

		public StaticViewHolder(View view) {
			super(view);
		}

		public void updateViewFor(String item) {
		}
	}

	private class MemberViewHolder extends BaseViewHolder<String> {

		public MemberViewHolder(View view) {
			super(view);
		}

		public void updateViewFor(String item) {
		}
	}

	private static class StringViewHolder extends BaseViewHolder<String> {

		final String argument;

		public StringViewHolder(View view, String argument) {
			super(view);
			this.argument = argument;
		}

		public void updateViewFor(String item) {
		}
	}

	private static class TwoStringViewHolder extends BaseViewHolder<String> {

		final String argument1;

		final CharSequence argument2;

		public TwoStringViewHolder(View view, String argument1,
				CharSequence argument2) {
			super(view);
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
		ReflectiveHolderFactory<String> factory = new ReflectiveHolderFactory<String>(
				StaticViewHolder.class, (Object[]) null);
		ViewHolder<String> holder = factory.createViewHolderFor(null);
		assertNotNull(holder);
	}

	/**
	 * Create holder for non-static member class
	 */
	@Test(expected = IllegalArgumentException.class)
	public void defaultConstructorNonStaticMemberClass() {
		new ReflectiveHolderFactory<String>(MemberViewHolder.class);
	}

	/**
	 * Create holder for non-static member class
	 */
	@Test(expected = IllegalArgumentException.class)
	public void noConstructorMatchMissingParam() {
		ReflectiveHolderFactory<String> factory = new ReflectiveHolderFactory<String>(
				StringViewHolder.class);
		factory.createViewHolderFor(null);
	}

	/**
	 * Create holder for non-static member class
	 */
	@Test(expected = IllegalArgumentException.class)
	public void noConstructorMatchInvalidParam() {
		ReflectiveHolderFactory<String> factory = new ReflectiveHolderFactory<String>(
				StringViewHolder.class, new Object());
		factory.createViewHolderFor(null);
	}

	/**
	 * Create holder for class that takes a String constructor argument
	 */
	@Test
	public void constructorWithSingleArgument() {
		ReflectiveHolderFactory<String> factory = new ReflectiveHolderFactory<String>(
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
		ReflectiveHolderFactory<String> factory = new ReflectiveHolderFactory<String>(
				TwoStringViewHolder.class, "a1", "b2");
		ViewHolder<String> holder = factory.createViewHolderFor(null);
		assertNotNull(holder);
		assertEquals("a1", ((TwoStringViewHolder) holder).argument1);
		assertEquals("b2", ((TwoStringViewHolder) holder).argument2);
	}
}
