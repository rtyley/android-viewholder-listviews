/*
 * Copyright (c) 2011 Roberto Tyley
 *
 * This file is part of 'android-viewholder-listviews'.
 *
 * android-viewholder-listviews is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * android-viewholder-listviews is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.madgag.android.listviews;

import android.view.View;

public class ViewFactory<T> {

    private final ViewCreator creator;
    private final ViewHolderFactory<T> viewHolderFactory;

    public ViewFactory(ViewCreator creator, ViewHolderFactory<T> viewHolderFactory) {
        this.creator = creator;
        this.viewHolderFactory = viewHolderFactory;
    }


    public View getView(View view, T item) {
		if (view==null) {
            view = creator.createBlankView();
            view.setTag(viewHolderFactory.createViewHolderFor(view));
        }

		ViewHolder<T> holder = (ViewHolder<T>) view.getTag();

		holder.updateViewFor(item);
		return view;
	}
}
