package org.rg.drip.fragment.wordbook;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.rg.drip.R;
import org.rg.drip.base.BaseFragment;
import org.rg.drip.base.BaseSubFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Author : TankGq
 * Time : 2018/4/5
 */
public class LearningProgressFragment extends BaseSubFragment
		implements BaseFragment.OnEnterAnimator {

	@BindView(R.id.line_chart) LineChart mLineChart;

	public static LearningProgressFragment newInstance() {
		LearningProgressFragment fragment = new LearningProgressFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.wordbook_fragment_learning_progress;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {
		mLineChart.setViewPortOffsets(0, 0, 0, 0);
		mLineChart.setBackgroundColor(Color.WHITE);
		mLineChart.getDescription().setEnabled(false);

		// enable touch gestures
		mLineChart.setTouchEnabled(true);

		// enable scaling and dragging
		mLineChart.setDragEnabled(true);
		mLineChart.setScaleEnabled(true);

		// if disabled, scaling can be done on x- and y-axis separately
		mLineChart.setPinchZoom(false);

		mLineChart.setDrawGridBackground(false);
		mLineChart.setMaxHighlightDistance(300);

		XAxis x = mLineChart.getXAxis();
		x.setEnabled(true);
		x.setLabelCount(5, false);
		x.setTextColor(getResources().getColor(R.color.colorPrimary));
		x.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
		x.setTextSize(13f);
		x.setYOffset(10f);
		x.setDrawGridLines(false);
		x.setAxisLineColor(Color.rgb(255, 208, 140));

		YAxis y = mLineChart.getAxisLeft();
		y.setLabelCount(5, false);
		y.setTextColor(Color.rgb(255, 140, 157));
		y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
		y.setTextSize(13f);
		y.setDrawGridLines(false);
		y.setAxisLineColor(getResources().getColor(R.color.colorPrimary));

		mLineChart.getAxisRight().setEnabled(false);

		// add data
		setData(45, 100);

		mLineChart.getLegend().setEnabled(false);

		// dont forget to refresh the drawing
		mLineChart.invalidate();
	}

	private void setData(int count, float range) {

		ArrayList<Entry> yVals = new ArrayList<Entry>();

		for(int i = 0; i < count; i++) {
			float mult = (range + 1);
			float val = (float) (Math.random() * mult) + 20;// + (float)
			// ((mult *
			// 0.1) / 10);
			yVals.add(new Entry(i, val));
		}

		LineDataSet set1;

		if(mLineChart.getData() != null &&
		   mLineChart.getData().getDataSetCount() > 0) {
			set1 = (LineDataSet) mLineChart.getData().getDataSetByIndex(0);
			set1.setValues(yVals);
			mLineChart.getData().notifyDataChanged();
			mLineChart.notifyDataSetChanged();
		} else {
			// create a dataset and give it a type
			set1 = new LineDataSet(yVals, "DataSet 1");

			set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
			set1.setCubicIntensity(0.2f);
			set1.setDrawFilled(true);
			set1.setDrawCircles(true);
			set1.setLineWidth(1.8f);
			set1.setDrawValues(true);
			set1.setCircleRadius(2f);
			set1.setCircleColorHole(getResources().getColor(R.color.colorAccent));
			set1.setCircleColor(getResources().getColor(R.color.colorAccent));
			set1.setHighLightColor(Color.rgb(244, 117, 117));
			set1.setColor(Color.rgb(104, 241, 175));
			set1.setFillColor(Color.rgb(104, 241, 175));
			set1.setFillAlpha(200);
			set1.setDrawHorizontalHighlightIndicator(false);
			set1.setFillFormatter((dataSet, dataProvider) -> - 10);

			// create a data object with the datasets
			LineData data = new LineData(set1);
			data.setValueTextSize(9f);
			data.setValueTextColor(getResources().getColor(R.color.colorPrimary));
			data.setDrawValues(true);

			// set data
			mLineChart.setData(data);
		}
	}

	@Override
	public void onEnterAnimator() {
		if(mLineChart == null) {
			return;
		}
		mLineChart.animateXY(1500, 1500);
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if(isVisibleToUser) {
			onEnterAnimator();
		}
	}
}
