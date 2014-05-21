package com.droidbrew.travelcheap.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.droidbrew.travelcheap.R;

public class TabFragment extends Fragment {

	private static final int LIST_STATE = 0x1;
	private static final int GRID_STATE = 0x2;

	private int mTabState;
	Button totalsViewTab;
	Button recordViewTab;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_tab, container, false);

		totalsViewTab = (Button) view.findViewById(R.id.list_view_tab);
		totalsViewTab.setBackgroundColor(Color.LTGRAY);
		recordViewTab = (Button) view.findViewById(R.id.grid_view_tab);
		recordViewTab.setBackgroundColor(Color.DKGRAY);

		totalsViewTab.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				gotoListView();
			}
		});

		recordViewTab.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				gotoGridView();
			}
		});

		return view;
	}

	public void gotoListView() {
		TextView tv = (TextView) getActivity().findViewById(R.id.StatusLine);
		tv.setText("");
		totalsViewTab.setBackgroundColor(Color.LTGRAY);
		recordViewTab.setBackgroundColor(Color.DKGRAY);

		if (mTabState != LIST_STATE) {
			mTabState = LIST_STATE;

			FragmentManager fm = getFragmentManager();

			if (fm != null) {
				FragmentTransaction ft = fm.beginTransaction();
				ft.replace(R.id.fragment_content, new TotalsListFragment());
				ft.commit();
			}
		}
	}

	public void gotoGridView() {
		TextView tv = (TextView) getActivity().findViewById(R.id.StatusLine);
		tv.setText(R.string.GridViewSetText);
		totalsViewTab.setBackgroundColor(Color.DKGRAY);
		recordViewTab.setBackgroundColor(Color.LTGRAY);

		if (mTabState != GRID_STATE) {
			mTabState = GRID_STATE;
			FragmentManager fm = getFragmentManager();
			if (fm != null) {
				FragmentTransaction ft = fm.beginTransaction();
				ft.replace(R.id.fragment_content, new RecordsListFragment());
				ft.commit();
			}
		}
	}
}
