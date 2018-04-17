package org.rg.drip.fragment.reading;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.qmuiteam.qmui.span.QMUITouchableSpan;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUICollapsingTopBarLayout;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.textview.QMUISpanTouchFixTextView;

import org.rg.drip.R;
import org.rg.drip.base.BaseSubFragment;
import org.rg.drip.constant.WordConstant;
import org.rg.drip.data.contract.WordContract;
import org.rg.drip.data.model.cache.Word;
import org.rg.drip.entity.Article;
import org.rg.drip.utils.LoggerUtil;
import org.rg.drip.utils.RepositoryUtil;
import org.rg.drip.utils.ToastUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by TankGq
 * on 2018/3/20.
 */
public class ReadingDetailFragment extends BaseSubFragment implements View.OnTouchListener,
                                                                      PopupWindow.OnDismissListener,
                                                                      WordContract.Repository.onTimeoutListener {
	private static final String ARG_ITEM = "arg_item";

	private Article mArticle;

	@BindView(R.id.toolbar_layout) QMUICollapsingTopBarLayout mCollapsingTopBarLayout;
	@BindView(R.id.scrollView) NestedScrollView mScrollView;
	@BindView(R.id.top_bar) QMUITopBar mTopBar;
	@BindView(R.id.search_view) MaterialSearchView mSearchView;
	@BindView(R.id.img_detail) ImageView mImgDetail;
	@BindView(R.id.tv_content) QMUISpanTouchFixTextView mContentTv;
	@BindView(R.id.position) View mPosition;

	private Disposable mQuery;

	private QMUIPopup mExplainPopup;
	private TextView mExplainTv;

	private WordContract.Repository mWordRepository;

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
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.serach_menu, menu);
		MenuItem item = menu.findItem(R.id.action_search);
		mSearchView.setMenuItem(item);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mArticle = getArguments().getParcelable(ARG_ITEM);
		setHasOptionsMenu(true);
	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.reading_fragment_detail;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {
		if(mWordRepository == null) {
			mWordRepository = RepositoryUtil.getWordRepository();
			mWordRepository.setOnTimeoutListener(this);
		}
		highlightTextNormalColor = getResources().getColor(R.color.qmui_config_color_pure_black, null);
		highlightTextPressedColor = getResources().getColor(R.color.white, null);
		highlightBgNormalColor = getResources().getColor(R.color.transparent, null);
		highlightBgPressedColor = getResources().getColor(R.color.colorAccent, null);

		mTopBar.addLeftBackImageButton()
		       .setOnClickListener(v -> _mActivity.onBackPressed());
		mCollapsingTopBarLayout.setTitle(mArticle.getTitle());
		mImgDetail.setImageResource(mArticle.getImgRes());
		mContentTv.setMovementMethodDefault();
		mContentTv.setText(generateSp(getString(R.string.large_text)));
		mContentTv.setOnTouchListener(this);
//		mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
//			@Override
//			public boolean onQueryTextSubmit(String query) {
//				ToastUtil.showCustumToast(getContext(), "onQueryTextSubmit(query= " + query + ")");
//				return false;
//			}
//
//			@Override
//			public boolean onQueryTextChange(String newText) {
//				ToastUtil.showCustumToast(getContext(), "onQueryTextChange(newText= " + newText + ")");
//				return false;
//			}
//		});
//		mSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
//			@Override
//			public void onSearchViewShown() {
//				ToastUtil.showCustumToast(getContext(), "onSearchViewShown");
//			}
//
//			@Override
//			public void onSearchViewClosed() {
//				ToastUtil.showCustumToast(getContext(), "onSearchViewClosed");
//			}
//		});
		if(mExplainPopup == null) {
			mExplainPopup = new QMUIPopup(getContext(), QMUIPopup.DIRECTION_TOP);
			mExplainTv = new TextView(getContext());
			mExplainTv.setLayoutParams(mExplainPopup.generateLayoutParam(WRAP_CONTENT, WRAP_CONTENT));
			int padding = QMUIDisplayHelper.dp2px(getContext(), 16);
			mExplainTv.setPadding(padding, padding, padding, padding);
			mExplainTv.setGravity(View.TEXT_ALIGNMENT_CENTER);
			mExplainTv.setTextColor(getResources().getColor(R.color.colorPrimary, null));
			mExplainPopup.setContentView(mExplainTv);
			mExplainPopup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_CENTER);
			mExplainPopup.setOnDismissListener(this);
		}
	}

	private String mCurrentClickWord;

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
				sp.setSpan(new QMUITouchableSpan(highlightTextNormalColor,
				                                 highlightTextPressedColor,
				                                 highlightBgNormalColor,
				                                 highlightBgPressedColor) {
					@Override
					public void onSpanClick(View widget) {
						if(mQuery != null && ! mQuery.isDisposed()) {
							mQuery.dispose();
						}
						mExplainTv.setText(getString(R.string.tip_in_the_query));
						mPosition.setX(mTouchX);
						mPosition.setY(mTouchY - QMUIDisplayHelper.dp2px(getContext(), 16));
						mExplainPopup.show(mPosition);
						ToastUtil.showCustumToast(getContext(), text.substring(op, ed));
						LoggerUtil.d(text.substring(op, ed));
						mQuery = RepositoryUtil.getWordRepository()
						                       .getWord(text.substring(op, ed))
						                       .observeOn(AndroidSchedulers.mainThread())
						                       .subscribeOn(Schedulers.io())
						                       .subscribe(word -> {
							                       if(mExplainPopup.isShowing() && word != WordConstant.empty) {
								                       mExplainTv.setText(word.getExplain());
							                       }
						                       });
					}
				}, start, indicator, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
			}
			start = indicator + 1;
		}
		return sp;
	}

	private float mTouchX, mTouchY;

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch(event.getAction()) {
			/*
			 * 点击的开始位置
			 */
			case MotionEvent.ACTION_DOWN:
				/*
				 * 触屏实时位置
				 */
			case MotionEvent.ACTION_MOVE:
			/*
			  离开屏幕的位置
			 */
			case MotionEvent.ACTION_UP:
				mTouchX = event.getRawX();
				mTouchY = event.getRawY();
				break;

			default:
				break;
		}
		return false;
	}

	@Override
	public void onDismiss() {
		if(mQuery != null && ! mQuery.isDisposed()) {
			mQuery.dispose();
		}
	}

	@Override
	public void onGetWordTimeout() {
		if(mExplainPopup.isShowing()) {
			mExplainTv.setText(R.string.tip_query_failed);
		}
	}
}
