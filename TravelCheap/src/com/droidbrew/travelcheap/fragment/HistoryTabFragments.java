package com.droidbrew.travelcheap.fragment;

import com.droidbrew.travelcheap.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HistoryTabFragments extends Fragment {

	private static final int HISTORY_STATE = 0x1;
	private static final int STAT_STATE = 0x2;
	private static final int CHARTS_STATE = 0x3;

	private int mTabState;
	Button totalsViewTab;
	Button recordViewTab;
	Button chartsViewTab;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.history_tab_fragments, container,
				false);

		totalsViewTab = (Button) view.findViewById(R.id.history_view);
		totalsViewTab.setBackgroundColor(Color.LTGRAY);
		recordViewTab = (Button) view.findViewById(R.id.stat_view);
		recordViewTab.setBackgroundColor(Color.DKGRAY);
		chartsViewTab = (Button) view.findViewById(R.id.charts_view);
		chartsViewTab.setBackgroundColor(Color.DKGRAY);

		totalsViewTab.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				gotoHistoryView();
			}
		});

		recordViewTab.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				gotoStatView();
			}
		});

		chartsViewTab.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				gotoChartsView();
			}
		});

		return view;
	}

	public void gotoHistoryView() {
		TextView tv = (TextView) getActivity().findViewById(R.id.StatusLine1);
		tv.setText(R.string.historyTabFtagmentText);
		totalsViewTab.setBackgroundColor(Color.LTGRAY);
		recordViewTab.setBackgroundColor(Color.DKGRAY);
		chartsViewTab.setBackgroundColor(Color.DKGRAY);

		if (mTabState != HISTORY_STATE) {
			mTabState = HISTORY_STATE;

			FragmentManager fm = getFragmentManager();

			if (fm != null) {
				FragmentTransaction ft = fm.beginTransaction();
				ft.replace(R.id.fragment_content1, new HistoryFragment());
				ft.commit();
			}
		}
	}

	public void gotoStatView() {
		TextView tv = (TextView) getActivity().findViewById(R.id.StatusLine1);
		tv.setText("");
		totalsViewTab.setBackgroundColor(Color.DKGRAY);
		recordViewTab.setBackgroundColor(Color.LTGRAY);
		chartsViewTab.setBackgroundColor(Color.DKGRAY);

		if (mTabState != STAT_STATE) {
			mTabState = STAT_STATE;

			FragmentManager fm = getFragmentManager();

			if (fm != null) {
				FragmentTransaction ft = fm.beginTransaction();
				ft.replace(R.id.fragment_content1, new StatFragment());
				ft.commit();
			}
		}
	}

	public void gotoChartsView() {
		TextView tv = (TextView) getActivity().findViewById(R.id.StatusLine1);
		tv.setText(null);
		totalsViewTab.setBackgroundColor(Color.DKGRAY);
		recordViewTab.setBackgroundColor(Color.DKGRAY);
		chartsViewTab.setBackgroundColor(Color.LTGRAY);

		if (mTabState != CHARTS_STATE) {
			mTabState = CHARTS_STATE;

			FragmentManager fm = getFragmentManager();

			if (fm != null) {
				FragmentTransaction ft = fm.beginTransaction();
				ft.replace(R.id.fragment_content1, new ChartsFragment());
				ft.commit();
			}
		}
	}
}