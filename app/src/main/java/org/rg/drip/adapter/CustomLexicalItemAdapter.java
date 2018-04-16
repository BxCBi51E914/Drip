package org.rg.drip.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.rg.drip.R;
import org.rg.drip.constant.LexicalItemConstant;
import org.rg.drip.data.model.cache.LexicalItem;
import org.rg.drip.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class CustomLexicalItemAdapter extends RecyclerView.Adapter<CustomLexicalItemAdapter.MyViewHolder> {
	private List<LexicalItem> mItems = new ArrayList<>();
	private LayoutInflater mInflater;
	
	private OnItemClickListener mClickListener;
	
	public CustomLexicalItemAdapter(Context context) {
		this.mInflater = LayoutInflater.from(context);
	}
	
	public void setData(List<LexicalItem> items) {
		mItems.clear();
		mItems.addAll(items);
	}
	
	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = mInflater.inflate(R.layout.lexical_fragment_lexical_item, parent, false);
		final MyViewHolder holder = new MyViewHolder(view);
		holder.itemView.setOnClickListener(v -> {
			int position = holder.getAdapterPosition();
			if(mClickListener != null) {
				mClickListener.onItemClick(position, v, holder);
			}
		});
		return holder;
	}
	
	@Override
	public void onBindViewHolder(MyViewHolder holder, int position) {
		LexicalItem item = mItems.get(position);
		if(item.getId() == LexicalItemConstant.DEFAULT_ID) {
			holder.mAddIv.setVisibility(View.VISIBLE);
		} else {
			holder.mKeyTv.setText(item.getKey());
			holder.mValueTv.setText(item.getValue());
		}
	}
	
	@Override
	public int getItemCount() {
		return mItems.size();
	}
	
	public LexicalItem getItem(int position) {
		return mItems.get(position);
	}
	
	class MyViewHolder extends RecyclerView.ViewHolder {
		private TextView mKeyTv;
		private TextView mValueTv;
		private ImageView mAddIv;
		
		public MyViewHolder(View itemView) {
			super(itemView);
			mKeyTv = itemView.findViewById(R.id.tv_key);
			mValueTv = itemView.findViewById(R.id.tv_value);
			mAddIv = itemView.findViewById(R.id.iv_add);
		}
	}
	
	public void setOnItemClickListener(OnItemClickListener itemClickListener) {
		this.mClickListener = itemClickListener;
	}
}
