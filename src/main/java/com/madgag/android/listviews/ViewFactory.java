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
        ViewHolder<T> holder;

		if (view==null) {
            view = creator.createBlankView();
            holder = viewHolderFactory.createViewHolderFor(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder<T>) view.getTag();

            // validate holder is of correct type - could it have been produced by our factory?
            if (!holder.getClass().equals(viewHolderFactory.getHolderClass())) {
                view = creator.createBlankView();
                holder = viewHolderFactory.createViewHolderFor(view);
                view.setTag(holder);
            }
        }

		holder.updateViewFor(item);
		return view;
	}
}
