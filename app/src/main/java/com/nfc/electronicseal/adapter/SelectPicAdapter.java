/*
 * Copyright 2016 Eric Liu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nfc.electronicseal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.liuguangqiang.ipicker.adapters.BaseAdapter;
import com.nfc.electronicseal.R;

import java.util.List;

/**
 * Created by Eric on 16/9/12.
 */
public class SelectPicAdapter extends BaseAdapter<String, SelectPicAdapter.ViewHolder> {

    public SelectPicAdapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater, parent, false);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bindData(data.get(position));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivPhoto;

        public ViewHolder(LayoutInflater layoutInflater, ViewGroup parent, boolean attachToRoot) {
            super(layoutInflater.inflate(R.layout.item_pic_selected, parent, attachToRoot));
            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
        }

        public void bindData(String path) {
            Glide.with(itemView.getContext()).load(path).into(ivPhoto);
        }

    }

}
