package org.rg.drip.fragment.lexical;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

import org.rg.drip.R;
import org.rg.drip.base.BaseSubFragment;
import org.rg.drip.utils.Rotatable;

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

	@OnClick({R.id.front, R.id.bar_chart})
	void onClick(View v) {
		switch(v.getId()) {
			case R.id.front:
			case R.id.back:

				break;
		}
	}
	
	public static LexicalItemDetailFragment newInstance() {
		LexicalItemDetailFragment fragment = new LexicalItemDetailFragment();
		Bundle bundle = new Bundle();
		fragment.setArguments(bundle);
		return fragment;
	}

	public void cardTurnover() {
		if (View.VISIBLE == mBack.getVisibility()) {
			ViewHelper.setRotationY(mFront, 180f);//先翻转180，转回来时就不是反转的了
			Rotatable rotatable = new Rotatable.Builder(mContainer)
					                      .sides(R.id.back, R.id.front)
					                      .direction(Rotatable.ROTATE_Y)
					                      .rotationCount(1)
					                      .build();
			rotatable.setTouchEnable(false);
			rotatable.rotate(Rotatable.ROTATE_Y, -180, 1500);
		} else if (View.VISIBLE == mFront.getVisibility()) {
			Rotatable rotatable = new Rotatable.Builder(mContainer)
					                      .sides(R.id.back, R.id.front)
					                      .direction(Rotatable.ROTATE_Y)
					                      .rotationCount(1)
					                      .build();
			rotatable.setTouchEnable(false);
			rotatable.rotate(Rotatable.ROTATE_Y, 0, 1500);
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
	}

	@Override
	public boolean onBackPressedSupport() {
		pop();
		return true;
	}
}
