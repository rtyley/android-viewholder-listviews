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

import java.util.Collection;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * View holding adapter for arrays and collections
 * 
 * @param <T>
 *            element type
 */
public class ViewHoldingArrayAdapter<T> extends BaseAdapter {

	private final T[] items;
	private final ViewFactory<T> viewFactory;

	/**
	 * Create adapter
	 * 
	 * @param items
	 * @param viewFactory
	 */
	@SuppressWarnings("unchecked")
	public ViewHoldingArrayAdapter(Collection<T> items,
			ViewFactory<T> viewFactory) {
		this((T[]) items.toArray(), viewFactory);
	}

	/**
	 * Create adapter
	 * 
	 * @param items
	 * @param viewFactory
	 */
	public ViewHoldingArrayAdapter(T[] items, ViewFactory<T> viewFactory) {
		this.items = items;
		this.viewFactory = viewFactory;
	}

	/**
	 * Create adapter
	 * 
	 * @param items
	 * @param c
	 * @param vhf
	 */
	public ViewHoldingArrayAdapter(T[] items, ViewCreator c,
			ViewHolderFactory<T> vhf) {
		this.items = items;
		viewFactory = new ViewFactory<T>(c, vhf);
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	public int getCount() {
		return items.length;
	}

	public T getItem(int index) {
		return items[index];
	}

	public long getItemId(int i) {
		return getItem(i).hashCode();
	}

	public View getView(int index, View view, ViewGroup parent) {
		return viewFactory.getView(view, items[index]);
	}
}
