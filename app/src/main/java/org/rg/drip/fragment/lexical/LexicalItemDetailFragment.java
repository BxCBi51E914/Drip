package org.rg.drip.fragment.lexical;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

import org.rg.drip.GlobalData;
import org.rg.drip.R;
import org.rg.drip.base.BaseSubFragment;
import org.rg.drip.data.model.cache.LexicalItem;
import org.rg.drip.utils.Rotatable;
import org.rg.drip.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by TankGq
 * on 2018/3/20.
 */
public class LexicalItemDetailFragment extends BaseSubFragment {
	
	@BindView(R.id.container) FrameLayout mContainer;
	@BindView(R.id.front) CardView mFront;
	@BindView(R.id.back) CardView mBack;
	@BindView(R.id.tv_key) TextView mKeyTv;
	@BindView(R.id.tv_value) TextView mValue;
	
	@OnClick({ R.id.front, R.id.back })
	void onClick(View v) {
		switch(v.getId()) {
			case R.id.front:
			case R.id.back:
				cardTurnover();
				break;
		}
	}
	
	private Rotatable mRotatable;
	
	public static LexicalItemDetailFragment newInstance() {
		LexicalItemDetailFragment fragment = new LexicalItemDetailFragment();
		Bundle bundle = new Bundle();
		fragment.setArguments(bundle);
		return fragment;
	}
	
	public void cardTurnover() {
		if(mRotatable == null) {
			return;
		}
		if (View.VISIBLE == mBack.getVisibility()) {
			mRotatable.rotate(Rotatable.ROTATE_Y, 0);
		} else if (View.VISIBLE == mFront.getVisibility()) {
			mRotatable.rotate(Rotatable.ROTATE_Y, 180);
		}
	}
	
	private void setCameraDistance() {
		int distance = 10000;
		float scale = getResources().getDisplayMetrics().density * distance;
		mContainer.setCameraDistance(scale);
	}
	
	@Override
	protected int getContentViewLayoutID() {
		return R.layout.lexical_fragment_lexical_item_detail;
	}
	
	@Override
	protected void initView(Bundle savedInstanceState) {
		mFront.setVisibility(View.VISIBLE);
		mBack.setVisibility(View.INVISIBLE);
		setCameraDistance();
		mRotatable = new Rotatable.Builder(mContainer).sides(R.id.front, R.id.back)
		                                              .direction(Rotatable.ROTATE_Y)
		                                              .build();
		List<LexicalItem> lexicalItems = GlobalData.getCurrentLexicalItemList();
		int size = lexicalItems.size();
		LexicalItem lexicalItem = null;
		for(int idx = 0; idx < size; ++ idx) {
			if(lexicalItems.get(idx).getId() == GlobalData.currentLexicalItemId) {
				lexicalItem = lexicalItems.get(idx);
				break;
			}
		}
		if(lexicalItem != null) {
			mKeyTv.setText(lexicalItem.getKey());
			mValue.setText(lexicalItem.getValue());
		} else {
			String error = getString(R.string.error);
			mKeyTv.setText(error);
			mValue.setText(error);
			ToastUtil.showCustumToast(getContext(), error);
		}
	}
	
	@Override
	public boolean onBackPressedSupport() {
		pop();
		return true;
	}
}
