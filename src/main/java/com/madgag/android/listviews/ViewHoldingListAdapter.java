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
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class ViewHoldingListAdapter<T> extends BaseAdapter {

	private List<T> itemList;
    private final ViewFactory<T> viewFactory, dropDownViewFactory;

    public ViewHoldingListAdapter(List<T> itemList, ViewFactory<T> viewFactory, ViewFactory<T> dropDownViewFactory) {
        this.itemList = itemList;
        this.viewFactory = viewFactory;
        this.dropDownViewFactory = dropDownViewFactory;
    }

    public ViewHoldingListAdapter(List<T> itemList, ViewFactory<T> viewFactory) {
		this(itemList, viewFactory, viewFactory);
    }

    public ViewHoldingListAdapter(List<T> itemList, ViewCreator c, ViewHolderFactory<T> vhf) {
		this(itemList, new ViewFactory<T>(c, vhf));
    }


	@Override
	public boolean hasStableIds() {
		return true;
	}
    
	public void setList(List<T> itemList) {
		this.itemList=itemList;
		notifyDataSetChanged();
	}

	public int getCount() {
		return itemList.size();
	}

	public T getItem(int index) {
		return itemList.get(index);
	}

	public long getItemId(int i) {
		return getItem(i).hashCode();
	}
	
	public View getView(int index, View convertView, ViewGroup parent) {
        return viewFactory.getView(convertView, itemList.get(index));
	}

    /**
     * Declared by SpinnerAdapter, this method allows your spinner to show
     * different views in your drop down vs the 'closed' spinned box.
     */
    public View getDropDownView(int index, View convertView, ViewGroup parent) {
        return dropDownViewFactory.getView(convertView, itemList.get(index));
    }
}