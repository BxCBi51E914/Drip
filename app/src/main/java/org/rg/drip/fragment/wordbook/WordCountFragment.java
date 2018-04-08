package org.rg.drip.fragment.wordbook;

import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.rg.drip.R;
import org.rg.drip.base.BaseFragment;
import org.rg.drip.base.BaseSubFragment;
import org.rg.drip.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Author : TankGq
 * Time : 2018/4/5
 */
public class WordCountFragment extends BaseSubFragment implements BaseFragment.OnEnterAnimator {

	@BindView(R.id.bar_chart) BarChart mBarChart;

	public static WordCountFragment newInstance() {
		WordCountFragment fragment = new WordCountFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.wordbook_fragment_word_count;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {
		ToastUtil.showCustumToast(getContext(), "Wc initView()");
		mBarChart.getDescription().setEnabled(false);

		// if more than 60 entries are displayed in the chart, no values will be
		// drawn
		mBarChart.setMaxVisibleValueCount(40);

		// scaling can now only be done on x- and y-axis separately
		mBarChart.setPinchZoom(false);

		mBarChart.setDrawGridBackground(false);
		mBarChart.setDrawBarShadow(false);

		mBarChart.setDrawValueAboveBar(false);
		mBarChart.setHighlightFullBarEnabled(false);

		// change the position of the y-labels
		YAxis leftAxis = mBarChart.getAxisLeft();
		leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
		leftAxis.setTextColor(getResources().getColor(R.color.colorPrimary));
		mBarChart.getAxisRight().setEnabled(false);

		XAxis xLabels = mBarChart.getXAxis();
		xLabels.setTextColor(getResources().getColor(R.color.colorPrimary));
		xLabels.setPosition(XAxis.XAxisPosition.BOTTOM);

		// mBarChart.setDrawXLabels(false);
		// mBarChart.setDrawYLabels(false);

		// setting data
		setData(12, 100);

		Legend l = mBarChart.getLegend();
		l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
		l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
		l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
		l.setDrawInside(false);
		l.setFormSize(8f);
		l.setFormToTextSpace(4f);
		l.setXEntrySpace(6f);
	}

	private void setData(int x, int y) {
		ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

		for(int i = 0; i < x + 1; i++) {
			float mult = (y + 1);
			float val1 = (float) (Math.random() * mult) + mult / 3;
			float val2 = (float) (Math.random() * mult) + mult / 3;
			float val3 = (float) (Math.random() * mult) + mult / 3;

			yVals1.add(new BarEntry(i,
			                        new float[] { val1, val2, val3 },
			                        getResources().getDrawable(R.drawable.star)));
		}

		BarDataSet set1;

		if(mBarChart.getData() != null &&
		   mBarChart.getData().getDataSetCount() > 0) {
			set1 = (BarDataSet) mBarChart.getData().getDataSetByIndex(0);
			set1.setValues(yVals1);
			mBarChart.getData().notifyDataChanged();
			mBarChart.notifyDataSetChanged();
		} else {
			set1 = new BarDataSet(yVals1, "");
			set1.setDrawIcons(false);
			set1.setColors(getColors());
			set1.setStackLabels(new String[] { "Births", "Divorces", "Marriages" });

			ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
			dataSets.add(set1);

			BarData data = new BarData(dataSets);
			data.setValueTextColor(getResources().getColor(R.color.colorPrimary));

			mBarChart.setData(data);
		}

		mBarChart.setFitBars(true);
		mBarChart.invalidate();
	}

	private int[] getColors() {

		int stacksize = 3;

		// have as many colors as stack-values per entry
		int[] colors = new int[stacksize];

		for(int i = 0; i < colors.length; i++) {
			colors[i] =
					ColorTemplate.VORDIPLOM_COLORS[ColorTemplate.VORDIPLOM_COLORS.length - i - 1];
		}

		return colors;
	}

	@Override
	public void onEnterAnimator() {
		if(mBarChart == null) {
			return;
		}
		mBarChart.animateXY(1500, 1500);
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if(isVisibleToUser) {
			onEnterAnimator();
		}
	}
}
