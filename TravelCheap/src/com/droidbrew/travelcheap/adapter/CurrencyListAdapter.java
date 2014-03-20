package com.droidbrew.travelcheap.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.droidbrew.travelcheap.R;
import com.droidbrew.travelkeeper.model.entity.TKCurrency;

public class CurrencyListAdapter extends BaseAdapter {

    private class ViewHolder {
        public TextView textView;
    }
    
    private List<TKCurrency> currencies;
    private LayoutInflater  inflater;
    
    public CurrencyListAdapter(Context context, List<TKCurrency> currencies) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.currencies = currencies;
    }
    
    @Override
    public int getCount() {
        if (currencies != null) {
            return currencies.size();
        }
        
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (currencies != null && position >= 0 && position < getCount()) {
            return currencies.get(position);
        }
        
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (currencies != null && position >= 0 && position < getCount()) {
            return position;
        }
        
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        View       view = convertView; 
        ViewHolder viewHolder;
        
        SimpleDateFormat formatter = new SimpleDateFormat("hh : mm a");
        
        if (view == null) {
            view = inflater.inflate(R.layout.item_currencies_list, parent, false);
            
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.list_label);
            
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }
        
        TKCurrency currency = currencies.get(position);
        
        String currencyLabel = currency.getCode() + " : " + currency.getName();
        viewHolder.textView.setText(currencyLabel);
        
        return view;
    }

}

