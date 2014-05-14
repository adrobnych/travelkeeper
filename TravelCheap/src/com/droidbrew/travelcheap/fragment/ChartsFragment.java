package com.droidbrew.travelcheap.fragment;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.droidbrew.travelcheap.HistoryActivity;
import com.droidbrew.travelcheap.LangManager;
import com.droidbrew.travelcheap.R;
import com.droidbrew.travelcheap.TravelApp;
import com.droidbrew.travelcheap.valueobject.ExpenseDayTotal;
import com.droidbrew.travelcheap.valueobject.ExpenseTripTotal;

public class ChartsFragment extends Fragment {

	private static final int CH_DAY = 0x01;
	private static final int CH_WEEK = 0x02;
	private static final int CH_TOTAL = 0x03;

	private final int[] colors = new int[] { Color.BLUE, Color.GREEN,
			Color.MAGENTA, Color.YELLOW, Color.CYAN, Color.DKGRAY };
	private Date date;

	private int check;

	private Activity activity;

	private TextView dateView;
	private TextView totalView;
	private Button prevBut;
	private Button nextBut;

	private GraphicalView gView;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		activity = getActivity();
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_charts, container, false);

		date = new Date();
		check = ChartsFragment.CH_DAY;

		setButtons(view, false);

		dateView = (TextView) view.findViewById(R.id.dateText);
		totalView = (TextView) view.findViewById(R.id.totalText);

		prevBut = (Button) view.findViewById(R.id.button_prev);
		prevBut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.DAY_OF_MONTH, -7);
				date = cal.getTime();
				drawGraph(getView());
				setTextInfo();
			}
		});
		nextBut = (Button) view.findViewById(R.id.button_next);
		nextBut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.DAY_OF_MONTH, 7);
				date = cal.getTime();
				drawGraph(getView());
				setTextInfo();
			}
		});

		RadioButton rBur = (RadioButton) view.findViewById(R.id.radio_day);
		rBur.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				onRadioButtonClicked(view);
			}
		});
		rBur = (RadioButton) view.findViewById(R.id.radio_week);
		rBur.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				onRadioButtonClicked(view);
			}
		});
		rBur = (RadioButton) view.findViewById(R.id.radio_total);
		rBur.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				onRadioButtonClicked(view);
			}
		});

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		drawGraph(this.getView());
		setTextInfo();
	}

	public void onRadioButtonClicked(View view) {

		switch (view.getId()) {
		case R.id.radio_day:
			check = ChartsFragment.CH_DAY;
			setButtons(this.getView(), false);
			break;
		case R.id.radio_week:
			check = ChartsFragment.CH_WEEK;
			setButtons(this.getView(), true);
			break;
		case R.id.radio_total:
			check = ChartsFragment.CH_TOTAL;
			setButtons(this.getView(), false);
			break;
		}

		drawGraph(this.getView());
		setTextInfo();
	}

	private void setButtons(View view, boolean b) {
		prevBut = (Button) view.findViewById(R.id.button_prev);
		prevBut.setEnabled(b);
		prevBut.setVisibility((b) ? View.VISIBLE : View.INVISIBLE);

		nextBut = (Button) view.findViewById(R.id.button_next);
		nextBut.setEnabled(b);
		nextBut.setVisibility((b) ? View.VISIBLE : View.INVISIBLE);
	}

	private void setTextInfo() {

		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		try {
			switch (check) {
			case ChartsFragment.CH_DAY:
				dateView.setText(df.format(date));
				totalView.setText(getString(R.string.Total_spent)
						+ ": "
						+ ((TravelApp) this.getActivity().getApplication())
								.getExpenseManager().sumAmountByDateAndTrip(
										date.getTime(), ((TravelApp) this.getActivity()
												.getApplication())
												.getTripManager()
												.getDefaultTripId())
						/ 100.0
						+ " "
						+ ((TravelApp) getActivity().getApplication())
								.getCurrencyManager().getReportCurrency());
				break;
			case ChartsFragment.CH_WEEK:
				long[] dates = this.getDates(date);
				dateView.setText(df.format(new Date(dates[0])) + " - "
						+ df.format(new Date(dates[6])));
				totalView.setText(getString(R.string.Total_spent)
						+ ": "
						+ ((TravelApp) this.getActivity().getApplication())
								.getExpenseManager().getBeetwenDays(
										((TravelApp) this.getActivity()
												.getApplication())
												.getTripManager()
												.getDefaultTripId(), dates[0],
										dates[6])
						/ 100.0
						+ " "
						+ ((TravelApp) getActivity().getApplication())
								.getCurrencyManager().getReportCurrency());
				break;
			case ChartsFragment.CH_TOTAL:
				dateView.setText(R.string.Total_trips);
				totalView.setText(getString(R.string.Total_spent)
						+ ": "
						+ ((TravelApp) this.getActivity().getApplication())
								.getExpenseManager().sumAmountByTrip(
										((TravelApp) this.getActivity()
												.getApplication())
												.getTripManager()
												.getDefaultTripId())
						/ 100.0
						+ " "
						+ ((TravelApp) getActivity().getApplication())
								.getCurrencyManager().getReportCurrency());
				break;
			}
		} catch (SQLException ex) {
			Log.e(ChartsFragment.class.getName(), ex.getMessage(), ex);
		}

	}

	private void drawGraph(View view) {
		LinearLayout layout = (LinearLayout) view.findViewById(R.id.chart);

		if (gView != null) {
			layout.removeView(gView);
			gView = null;
		}
			if (check == ChartsFragment.CH_DAY) {
				List<ExpenseDayTotal> totals = ((TravelApp) activity
						.getApplication()).getVoFactory().getExpenceDayTotals(
						new Date().getTime());
				gView = ChartFactory.getPieChartView(view.getContext(),
						getCategorySeries1(totals), getPieRenderer(totals.size()));
			}
			if (check == ChartsFragment.CH_TOTAL) {
				List<ExpenseTripTotal> totals = ((TravelApp) activity
						.getApplication()).getVoFactory().getExpenceTripTotals();
				gView = ChartFactory.getPieChartView(view.getContext(),
						getCategorySeries2(totals), getPieRenderer(6));
			}
			if (check == ChartsFragment.CH_WEEK)
				gView = getViewWeek();
			
		
		layout.addView(gView);
	}

	private long[] getDates(Date date) {
		long[] dates = new long[7];

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayofweek > 2 && dayofweek < 8)
			cal.add(Calendar.DAY_OF_MONTH, -1 * (dayofweek - 2));
		if (dayofweek == 1)
			cal.add(Calendar.DAY_OF_MONTH, -6);

		for (int i = 0; i < 7; i++) {
			dates[i] = cal.getTimeInMillis();
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}

		return dates;
	}

	private XYMultipleSeriesRenderer getXYMSRenderer() {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		final String[] dayss = getResources().getStringArray(R.array.dayss);

		for (int i = 0; i < 6; i++) {
			XYSeriesRenderer ren = new XYSeriesRenderer();
			ren.setColor(colors[i]);
			ren.setFillPoints(false);
			ren.setLineWidth(1);
			ren.setDisplayChartValues(false);
			renderer.addSeriesRenderer(ren);
		}
		renderer.setZoomButtonsVisible(false);
		renderer.setShowGridX(true);
		renderer.setLabelsTextSize(15);
		for (int i = 0; i < dayss.length; i++) {
			renderer.addXTextLabel(i, dayss[i]);
		}

		return renderer;
	}

	private XYMultipleSeriesDataset getXYDataSet(long[] dates) {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

		XYSeries[] series = this.getXYSeries(dates);
		for (int i = 5; i >= 0; i--)
			dataset.addSeries(series[i]);

		return dataset;
	}

	private XYSeries[] getXYSeries(long[] dates) {
		final String[] mChoose = getResources().getStringArray(R.array.mChoose1);
		XYSeries[] ser = new XYSeries[mChoose.length];
		for (int i = 0; i < mChoose.length; i++)
			ser[i] = new XYSeries(mChoose[i]);

		for (int i = 0; i < 7; i++) {
			List<ExpenseDayTotal> list = ((TravelApp) activity.getApplication())
					.getVoFactory().getExpenceDayTotals(dates[i]);
			double sum = 0;
			for (int y = 0; y < 6; y++) {
				sum += list.get(y).getAmount();
				ser[y].add(i, sum);
			}
		}

		return ser;
	}

	private GraphicalView getViewWeek() {
		GraphicalView view = null;

		long[] dates = this.getDates(date);
		XYMultipleSeriesDataset dataset = this.getXYDataSet(dates);
		XYMultipleSeriesRenderer ren = this.getXYMSRenderer();

		view = ChartFactory.getBarChartView(getView().getContext(), dataset,
				ren, Type.STACKED);

		return view;
	}

	private CategorySeries getCategorySeries1(List<ExpenseDayTotal> totals) {
		CategorySeries series = new CategorySeries("Pie Chart");
		double sum = 0;
		for (ExpenseDayTotal e : totals) {
			series.add(e.getType(), e.getAmount());
			sum += e.getAmount();
		}
		if(sum==0)
			series.set(0, getString(R.string.Nothing), 1);
		return series;
	}

	private CategorySeries getCategorySeries2(List<ExpenseTripTotal> totals) {
		CategorySeries series = new CategorySeries("Pie Chart");
		double sum = 0;
		for (int i = 0; i < 6; i++) {
			series.add(totals.get(i).getType(), totals.get(i).getAmount());
			sum +=  totals.get(i).getAmount();
		}
		if(sum==0)
			series.set(0, getString(R.string.Nothing), 1);
		return series;
	}

	private DefaultRenderer getPieRenderer(int size) {
		DefaultRenderer dr = new DefaultRenderer();

		for (int v = 0; v < size; v++) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(colors[v]);
			dr.addSeriesRenderer(r);
		}
		dr.setLabelsTextSize(25);
		dr.setDisplayValues(true);
		dr.setShowLegend(false);

		return dr;
	}

}
