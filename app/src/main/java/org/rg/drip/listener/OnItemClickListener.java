package org.rg.drip.listener;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by eee
 * on 2018/3/20.
 */
public interface OnItemClickListener {
    void onItemClick(int position, View view, RecyclerView.ViewHolder vh);
}