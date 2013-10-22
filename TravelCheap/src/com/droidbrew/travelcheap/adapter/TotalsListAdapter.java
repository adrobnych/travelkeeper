package com.droidbrew.travelcheap.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.droidbrew.travelcheap.R;
import com.droidbrew.travelcheap.valueobject.ExpenseDayTotal;

public class TotalsListAdapter extends BaseAdapter {

    private class ViewHolder {
    	public ImageView imageView;
        public TextView textView;
    }
    
    private ExpenseDayTotal[] totals;
    private LayoutInflater  mInflater;
    
    public TotalsListAdapter(Context context, ExpenseDayTotal[] _totals) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        totals = _totals;
    }
    
    @Override
    public int getCount() {
        if (totals != null) {
            return totals.length;
        }
        
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (totals != null && position >= 0 && position < getCount()) {
            return totals[position];
        }
        
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (totals != null && position >= 0 && position < getCount()) {
            return totals[position].getId();
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
        
        ExpenseDayTotal total = totals[position];
        
        viewHolder.imageView.setImageResource(total.getPicture());
        viewHolder.textView.setText(total.getType() + ": " + total.getAmount());
        
        return view;
    }

}
