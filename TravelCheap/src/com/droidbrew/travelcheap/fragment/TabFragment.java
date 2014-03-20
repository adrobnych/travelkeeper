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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        
        // Grab the tab buttons from the layout and attach event handlers. The code just uses standard
        // buttons for the tab widgets. These are bad tab widgets, design something better, this is just
        // to keep the code simple. 
        totalsViewTab = (Button) view.findViewById(R.id.list_view_tab);
        totalsViewTab.setBackgroundColor(Color.LTGRAY);
        recordViewTab = (Button) view.findViewById(R.id.grid_view_tab);
        recordViewTab.setBackgroundColor(Color.DKGRAY);
        
        totalsViewTab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Switch the tab content to display the list view.
                gotoListView();
            }
        });
        
        recordViewTab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Switch the tab content to display the grid view.
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
    	
        // mTabState keeps track of which tab is currently displaying its contents.
        // Perform a check to make sure the list tab content isn't already displaying.
        
        if (mTabState != LIST_STATE) {
            // Update the mTabState 
            mTabState = LIST_STATE;
            
            // Fragments have access to their parent Activity's FragmentManager. You can
            // obtain the FragmentManager like this.
            FragmentManager fm = getFragmentManager();
            
            if (fm != null) {
                // Perform the FragmentTransaction to load in the list tab content.
                // Using FragmentTransaction#replace will destroy any Fragments
                // currently inside R.id.fragment_content and add the new Fragment
                // in its place.
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_content, new TotalsListFragment());
                ft.commit();
            }
        }
    }
    
    public void gotoGridView() {
    	TextView tv = (TextView) getActivity().findViewById(R.id.StatusLine);
    	tv.setText("Click to remove");
    	totalsViewTab.setBackgroundColor(Color.DKGRAY);
        recordViewTab.setBackgroundColor(Color.LTGRAY);
        // See gotoListView(). This method does the same thing except it loads
        // the grid tab.
        
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
