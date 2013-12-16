package com.droidbrew.travelcheap.adapter;


import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.droidbrew.travelcheap.R;
import com.droidbrew.travelcheap.TravelApp;
import com.droidbrew.travelcheap.valueobject.ExpenseDayTotal;

public class TotalsListAdapter extends BaseAdapter {

    private class ViewHolder {
    	public ImageView imageView;
        public TextView textView;
    }
    
    private List<ExpenseDayTotal> totals;
    private LayoutInflater  mInflater;
    Context context;
    
    public TotalsListAdapter(Context context, List<ExpenseDayTotal> totals) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.totals = totals;
        this.context = context;
    }
    
    @Override
    public int getCount() {
        if (totals != null) {
            return totals.size();
        }
        
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (totals != null && position >= 0 && position < getCount()) {
            return totals.get(position);
        }
        
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (totals != null && position >= 0 && position < getCount()) {
            return totals.get(position).getId();
        }
        
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        View       view = convertView;
        ViewHolder viewHolder;
        
        if (view == null) {
            view = mInflater.inflate(R.layout.item_totals_list, parent, false);
            
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.total_image);
            viewHolder.textView = (TextView) view.findViewById(R.id.list_label);
            
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }
        
        ExpenseDayTotal total = totals.get(position);
        
        viewHolder.imageView.setImageResource(total.getPicture());
        
        String reportCurrency = "";
        try {
			reportCurrency = ((TravelApp)context.getApplicationContext()).getCurrencyManager().getReportCurrency();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        viewHolder.textView.setText(total.getType() + ": " + total.getAmount() + " " + reportCurrency);
        
        return view;
    }

}
