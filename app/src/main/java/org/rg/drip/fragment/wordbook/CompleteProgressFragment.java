package org.rg.drip.fragment.wordbook;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import org.rg.drip.R;
import org.rg.drip.base.BaseFragment;
import org.rg.drip.base.BaseSubFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Author : TankGq
 * Time : 2018/4/5
 */
public class CompleteProgressFragment extends BaseSubFragment
		implements BaseFragment.OnEnterAnimator {

	String[] mParties = new String[] {
			"Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
			"Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
			"Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
			"Party Y", "Party Z"
	};

	@BindView(R.id.pie_chart) PieChart mPieChart;

	public static CompleteProgressFragment newInstance() {
		CompleteProgressFragment fragment = new CompleteProgressFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.wordbook_fragment_complete_progress;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {
		mPieChart.setUsePercentValues(true);
		mPieChart.getDescription().setEnabled(false);
		Legend l = mPieChart.getLegend();
		l.setEnabled(true);
		l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
		l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
		l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
		mPieChart.setExtraOffsets(5, 5, 5, 5);
		mPieChart.setDragDecelerationFrictionCoef(0.8f);

//		mPieChart.setCenterTextTypeface(mTfLight);
		mPieChart.setCenterText(generateCenterSpannableText(9999));
		mPieChart.setDrawHoleEnabled(true);
		mPieChart.setHoleColor(Color.WHITE);
		mPieChart.setEntryLabelColor(getResources().getColor(R.color.colorAccent));

		mPieChart.setTransparentCircleColor(Color.WHITE);
		mPieChart.setTransparentCircleAlpha(0x7F);

		mPieChart.setHoleRadius(48f);
		mPieChart.setTransparentCircleRadius(51f);

		mPieChart.setDrawCenterText(true);

		mPieChart.setRotationAngle(0);
		// enable rotation of the chart by touch
		mPieChart.setRotationEnabled(true);
		mPieChart.setHighlightPerTapEnabled(true);

		// mPieChart.setUnit(" €");
		// mPieChart.setDrawUnitsInChart(true);

		// add a selection listener
//		mPieChart.setOnChartValueSelectedListener(this);

		setData(4, 100);
	}

	private void setData(int count, float range) {

		ArrayList<PieEntry> entries = new ArrayList<>();

		// NOTE: The order of the entries when being added to the entries array determines their position around the center of
		// the chart.
		float[] radios = { 10f, 20f, 30f, 39.5f };
		for(int i = 0; i < count; i++) {
			entries.add(new PieEntry(radios[i],
			                         mParties[i % mParties.length],
			                         getResources().getDrawable(R.drawable.star)));
		}
		entries.add(new PieEntry(0.5f,
		                         mParties[count % mParties.length],
		                         getResources().getDrawable(R.drawable.star)));
		PieDataSet dataSet = new PieDataSet(entries, "");

		dataSet.setDrawIcons(false);

		dataSet.setSliceSpace(3f);
		dataSet.setIconsOffset(new MPPointF(0, 40));
		dataSet.setSelectionShift(10f);

		// add a lot of colors

		ArrayList<Integer> colors = new ArrayList<Integer>();

		for(int c : ColorTemplate.VORDIPLOM_COLORS) {
			colors.add(c);
		}

		for(int c : ColorTemplate.JOYFUL_COLORS) {
			colors.add(c);
		}

		for(int c : ColorTemplate.COLORFUL_COLORS) {
			colors.add(c);
		}

		for(int c : ColorTemplate.LIBERTY_COLORS) {
			colors.add(c);
		}

		for(int c : ColorTemplate.PASTEL_COLORS) {
			colors.add(c);
		}

		colors.add(ColorTemplate.getHoloBlue());

		dataSet.setColors(colors);

		PieData data = new PieData(dataSet);
		data.setValueFormatter(new PercentFormatter());
		data.setValueTextSize(13f);
		data.setValueTextColor(getResources().getColor(R.color.colorPrimary));
		mPieChart.setData(data);

		// undo all highlights
		mPieChart.highlightValues(null);

		mPieChart.invalidate();

		// setUserVisibleHint 的第一次调用比初始化 UI 还要早,
		// 所以第一个 tab 界面得在初始化 UI 的时候再调用一次
		onEnterAnimator();
	}

	private SpannableString generateCenterSpannableText(int wordCount) {
		SpannableString s = new SpannableString("单词总数\n" + wordCount);
		s.setSpan(new RelativeSizeSpan(1.5f), 0, 4, 0);
		s.setSpan(new StyleSpan(Typeface.BOLD), 5, s.length(), 0);
		s.setSpan(new RelativeSizeSpan(2f), 5, s.length(), 0);
		s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), 5, s.length(), 0);
		return s;
	}

	@Override
	public void onEnterAnimator() {
		if(mPieChart == null) {
			return;
		}
		mPieChart.animateXY(1500, 1500);
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if(isVisibleToUser) {
			onEnterAnimator();
		}
	}
}
