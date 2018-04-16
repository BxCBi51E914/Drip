package org.rg.drip.listener;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by TankGq
 * on 2018/3/20.
 */
public interface OnItemLongClickListener {
    boolean onItemLongClick(int position, View view, RecyclerView.ViewHolder vh);
}