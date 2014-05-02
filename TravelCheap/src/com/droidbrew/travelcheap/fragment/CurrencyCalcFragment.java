package com.droidbrew.travelcheap.fragment;

import java.sql.SQLException;

import com.droidbrew.travelcheap.CurrencyActivity;
import com.droidbrew.travelcheap.CurrencyCalculatorActivity;
import com.droidbrew.travelcheap.R;
import com.droidbrew.travelcheap.TravelApp;
import com.droidbrew.travelkeeper.model.entity.TKCurrency;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class CurrencyCalcFragment extends Fragment {
 
	private static final int RESULT_SETTINGS = 2;
	SharedPreferences pref;
	private boolean refresh = false;
	
	public void setRefresh(boolean bool) {
		refresh = bool;
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_currency, container, false);
	    pref = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
	    
	    final Button cBut = (Button) view.findViewById(R.id.button_currency1);
	    cBut.setOnClickListener(new Button.OnClickListener() {
			@Override	public void onClick(View arg0) {onCurrencyClick(cBut);}
	    });

	    final Button btn1 = (Button) view.findViewById(R.id.btn1);
	    btn1.setOnClickListener(new Button.OnClickListener() {
			@Override	public void onClick(View arg0) {onCurrencyClick(arg0);}
	    });

	    final Button btn2 = (Button) view.findViewById(R.id.btn2);
	    btn2.setOnClickListener(new Button.OnClickListener() {
			@Override	public void onClick(View arg0) {onCurrencyClick(arg0);}
	    });

	    final Button btn3 = (Button) view.findViewById(R.id.btn3);
	    btn3.setOnClickListener(new Button.OnClickListener() {
			@Override	public void onClick(View arg0) {onCurrencyClick(arg0);}
	    });

	    final Button btn4 = (Button) view.findViewById(R.id.btn4);
	    btn4.setOnClickListener(new Button.OnClickListener() {
			@Override	public void onClick(View arg0) {onCurrencyClick(arg0);}
	    });

	    final Button btn5 = (Button) view.findViewById(R.id.btn5);
	    btn5.setOnClickListener(new Button.OnClickListener() {
			@Override	public void onClick(View arg0) {onCurrencyClick(arg0);}
	    });


	    EditText eField = (EditText) view.findViewById(R.id.input_convert);
	    eField.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable arg0) {}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) { if(refresh) refresh();	}
	    });
        return view;
    }
    
    public void onStart() {
        super.onStart();
		Button cButton = (Button) this.getActivity().findViewById(R.id.button_currency1);
		Button btn1 = (Button) this.getActivity().findViewById(R.id.btn1);
		btn1.setText(pref.getString("convert1", "USD"));
		Button btn2 = (Button) this.getActivity().findViewById(R.id.btn2);
		btn2.setText(pref.getString("convert2", "EUR"));
		Button btn3 = (Button) this.getActivity().findViewById(R.id.btn3);
		btn3.setText(pref.getString("convert3", "CNY"));
		Button btn4 = (Button) this.getActivity().findViewById(R.id.btn4);
		btn4.setText(pref.getString("convert4", "JPY"));
		Button btn5 = (Button) this.getActivity().findViewById(R.id.btn5);
		btn5.setText(pref.getString("convert5", "GBP"));
        try {
			cButton.setText(
				((TravelApp)this.getActivity().getApplication()).getCurrencyManager().getEntranceCurrency()
			);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        setStart();
        if(refresh) refresh();
      }
    
    private void setStart() {
    	try {
    	TKCurrency cur = ((TravelApp)this.getActivity().getApplication())
		.getCurrencyManager().find("EUR");
    	if(cur != null)
    		setRefresh(true);
    	}catch(SQLException ex) {}
    }
    
	 private void refresh() {
			try {
				String str = ((TextView) this.getActivity().findViewById(R.id.input_convert))
						.getText().toString();
				if(str.equals(""))
					str = "0";
				double amount = 100*Double.parseDouble(str.toString());
				String code = ((TravelApp)this.getActivity().getApplication())
						.getCurrencyManager().getEntranceCurrency();
				double course = ((TravelApp)this.getActivity().getApplication())
						.getCurrencyManager().find(code).getCourse();
				long usdamount = Math.round(amount/(course/1000000.0));
				
				TextView tv = (TextView) this.getActivity().findViewById(R.id.amount1);
				tv.setText(""+Math.round(usdamount * 
						((TravelApp)this.getActivity().getApplication()).getCurrencyManager()
						.find(pref.getString("convert1", "USD"))
						.getCourse() / 1000000.0)/100.0);
				
				tv = (TextView) this.getActivity().findViewById(R.id.amount2);
				tv.setText(""+Math.round(usdamount * 
						((TravelApp)this.getActivity().getApplication()).getCurrencyManager()
						.find(pref.getString("convert2", "EUR"))
						.getCourse() / 1000000.0)/100.0);
				
				tv = (TextView) this.getActivity().findViewById(R.id.amount3);
				tv.setText(""+Math.round(usdamount * 
						((TravelApp)this.getActivity().getApplication()).getCurrencyManager()
						.find(pref.getString("convert3", "CNY"))
						.getCourse() / 1000000.0)/100.0);
				
				tv = (TextView) this.getActivity().findViewById(R.id.amount4);
				tv.setText(""+Math.round(usdamount * 
						((TravelApp)this.getActivity().getApplication()).getCurrencyManager()
						.find(pref.getString("convert4", "JPY"))
						.getCourse() / 1000000.0)/100.0);
				
				tv = (TextView) this.getActivity().findViewById(R.id.amount5);
				tv.setText(""+Math.round(usdamount * 
						((TravelApp)this.getActivity().getApplication()).getCurrencyManager()
						.find(pref.getString("convert5", "GBP"))
						.getCourse() / 1000000.0)/100.0);
				
			}catch(SQLException ex) {
				Log.e("CurrencyCalculatorActivity", ex.getMessage());
			}
		 
	 }
	 public void onCurrencyClick(View view){
		 int id = view.getId();
			Intent i = new Intent(this.getActivity(), CurrencyActivity.class);
		 
		 switch(id) {
		 case R.id.btn1:
			 i.putExtra("convert", "convert1");
			 break;
		 case R.id.btn2:
			 i.putExtra("convert", "convert2");
			 break;
		 case R.id.btn3:
			 i.putExtra("convert", "convert3");
			 break;
		 case R.id.btn4:
			 i.putExtra("convert", "convert4");
			 break;
		 case R.id.btn5:
			 i.putExtra("convert", "convert5");
			 break;
		 }

		 this.getActivity().startActivity(i);
		 
	 }

		@Override
		public void onResume() {
			super.onResume();

			Button cButton = (Button) this.getActivity().findViewById(R.id.button_currency1);
			Button btn1 = (Button) this.getActivity().findViewById(R.id.btn1);
			btn1.setText(pref.getString("convert1", "USD"));
			Button btn2 = (Button) this.getActivity().findViewById(R.id.btn2);
			btn2.setText(pref.getString("convert2", "EUR"));
			Button btn3 = (Button) this.getActivity().findViewById(R.id.btn3);
			btn3.setText(pref.getString("convert3", "CNY"));
			Button btn4 = (Button) this.getActivity().findViewById(R.id.btn4);
			btn4.setText(pref.getString("convert4", "JPY"));
			Button btn5 = (Button) this.getActivity().findViewById(R.id.btn5);
			btn5.setText(pref.getString("convert5", "GBP"));
	        try {
				cButton.setText(
					((TravelApp)this.getActivity().getApplication()).getCurrencyManager().getEntranceCurrency()
				);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	        
	        if(refresh) refresh();
		}

}
