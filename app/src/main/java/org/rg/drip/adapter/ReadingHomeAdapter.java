package org.rg.drip.adapter;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.rg.drip.R;
import org.rg.drip.entity.Article;
import org.rg.drip.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TankGq
 * on 16/6/5.
 */
public class ReadingHomeAdapter extends RecyclerView.Adapter<ReadingHomeAdapter.VH> {

    private List<Article> mItems = new ArrayList<>();
    private LayoutInflater mInflater;

    private OnItemClickListener mClickListener;


    public ReadingHomeAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.reading_fragment_item, parent, false);
        final VH holder = new VH(view);
        holder.itemView.setOnClickListener(v -> {
            int position = holder.getAdapterPosition();
            if (mClickListener != null) {
                mClickListener.onItemClick(position, v, holder);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        Article item = mItems.get(position);

        // 把每个图片视图设置不同的Transition名称, 防止在一个视图内有多个相同的名称, 在变换的时候造成混乱
        // Fragment支持多个View进行变换, 使用适配器时, 需要加以区分
        ViewCompat.setTransitionName(holder.img, String.valueOf(position) + "_image");
        ViewCompat.setTransitionName(holder.tvTitle, String.valueOf(position) + "_tv");

        holder.img.setImageResource(item.getImgRes());
        holder.tvTitle.setText(item.getTitle());
    }

    public void setDatas(List<Article> items) {
        mItems.clear();
        mItems.addAll(items);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public Article getItem(int position) {
        return mItems.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public class VH extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public ImageView img;

        public VH(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_key);
            img = itemView.findViewById(R.id.img);
        }
    }
}
