/*
 * Copyright 2011 Roberto Tyley
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
