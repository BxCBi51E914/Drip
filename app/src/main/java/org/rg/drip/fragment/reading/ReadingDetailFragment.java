package org.rg.drip.fragment.reading;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;

import com.qmuiteam.qmui.span.QMUITouchableSpan;
import com.qmuiteam.qmui.widget.QMUICollapsingTopBarLayout;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.textview.QMUISpanTouchFixTextView;

import org.rg.drip.R;
import org.rg.drip.base.BaseSubFragment;
import org.rg.drip.entity.Article;
import org.rg.drip.utils.LoggerUtil;
import org.rg.drip.utils.ToastUtil;

import butterknife.BindView;

/**
 * Created by TankGq
 * on 2018/3/20.
 */
public class ReadingDetailFragment extends BaseSubFragment {
	private static final String ARG_ITEM = "arg_item";

	private Article mArticle;

	@BindView(R.id.toolbar_layout) QMUICollapsingTopBarLayout mCollapsingTopBarLayout;
	@BindView(R.id.scrollView) NestedScrollView mScrollView;
	@BindView(R.id.top_bar) QMUITopBar mTopBar;
	@BindView(R.id.img_detail) ImageView mImgDetail;
	@BindView(R.id.tv_content) QMUISpanTouchFixTextView mContentTv;

	private int highlightTextNormalColor;
	private int highlightTextPressedColor;
	private int highlightBgNormalColor;
	private int highlightBgPressedColor;

	public static ReadingDetailFragment newInstance(Article article) {
		Bundle args = new Bundle();
		args.putParcelable(ARG_ITEM, article);
		ReadingDetailFragment fragment = new ReadingDetailFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mArticle = getArguments().getParcelable(ARG_ITEM);
	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.zhihu_fragment_first_detail;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {
		highlightTextNormalColor = getResources().getColor(R.color.qmui_config_color_pure_black, null);
		highlightTextPressedColor = getResources().getColor(R.color.white, null);
		highlightBgNormalColor = getResources().getColor(R.color.transparent, null);
		highlightBgPressedColor = getResources().getColor(R.color.colorAccent, null);

		mTopBar.addLeftBackImageButton().setOnClickListener(v -> _mActivity.onBackPressed());
		mCollapsingTopBarLayout.setTitle(mArticle.getTitle());
		mImgDetail.setImageResource(mArticle.getImgRes());
		mContentTv.setMovementMethodDefault();
		mContentTv.setText(generateSp(getString(R.string.large_text)));
	}

	/**
	 * 添加点击事件
	 */
	private SpannableString generateSp(final String text) {
		SpannableString sp = new SpannableString(text);
		int start = 0, end = text.length(), indicator, character;
		while(start < end) {
			indicator = start;
			while(true) {
				character = text.charAt(indicator);
				if(indicator + 1 != end &&
				   (Character.isLetter(character) || Character.isDigit(character) || character == '-')) {
					++ indicator;
				} else {
					break;
				}
			}
			if(indicator != start) {
				final int op = start, ed = indicator;
				sp.setSpan(new QMUITouchableSpan(highlightTextNormalColor, highlightTextPressedColor,
				                                 highlightBgNormalColor, highlightBgPressedColor) {
					@Override
					public void onSpanClick(View widget) {
						ToastUtil.showCustumToast(getContext(), text.substring(op, ed));
						LoggerUtil.d(text.substring(op, ed));
					}
				}, start, indicator, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
			}
			start = indicator + 1;
		}
		return sp;
	}
}
