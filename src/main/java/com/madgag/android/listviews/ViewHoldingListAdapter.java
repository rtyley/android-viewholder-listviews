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
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class ViewHoldingListAdapter<T> extends BaseAdapter {

	private List<T> itemList;
    private final ViewFactory<T> viewFactory;


    public ViewHoldingListAdapter(List<T> itemList, ViewFactory<T> viewFactory) {
		this.itemList = itemList;
        this.viewFactory = viewFactory;
    }

    public ViewHoldingListAdapter(List<T> itemList, ViewCreator c, ViewHolderFactory<T> vhf) {
		this.itemList = itemList;
        this.viewFactory = new ViewFactory<T>(c, vhf);
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
	
	public View getView(int index, View view, ViewGroup parent) {
        return viewFactory.getView(view, itemList.get(index));
	}
}