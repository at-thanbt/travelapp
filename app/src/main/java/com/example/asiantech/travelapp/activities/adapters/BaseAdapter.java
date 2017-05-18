package com.example.asiantech.travelapp.activities.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 18/05/2017.
 */
public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private final Context mContext;


    public BaseAdapter(@NonNull Context mContext) {
        this.mContext = mContext;
    }

    protected Context getContext() {
        return mContext;
    }

    protected Resources getResource() {
        return mContext.getResources();
    }
}
