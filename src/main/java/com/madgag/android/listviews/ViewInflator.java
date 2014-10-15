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


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class ViewInflator implements ViewCreator {
    private final LayoutInflater inflater;
    private final int resId;

    public ViewInflator(LayoutInflater inflater, int resId) {
        this.inflater = inflater;
        this.resId = resId;
    }

    public View createBlankView(int positionType) {
        return inflater.inflate(resId, null);
    }

    public static ViewInflator viewInflatorFor(Context context, int resId) {
        return new ViewInflator(LayoutInflater.from(context), resId);

    }
}
