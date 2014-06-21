package com.droidbrew.travelcheap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity {

	SupportMapFragment mapFragment;
	GoogleMap map;
	Button test;
	View dlg = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.fragment_map);
		map = mapFragment.getMap();
		if (map == null) {
			finish();
			return;
		}
		init();
	}

	private void init() {
		Double lat = 48.607151;
		Double lot = 22.262233;
		
		map.addMarker(new MarkerOptions()
				.position(new LatLng(lat+ 00.000025, lot-00.000050))
				.title("red"));
		
		
		map.addMarker(new MarkerOptions()
		.position(new LatLng(lat-+ 00.000025, lot+-00.000050))
		.title("Yellow")
		.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
		
		map.addMarker(new MarkerOptions()
	      .position(new LatLng(lat, lot))
	      .title("Green")
	      .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
		
		map.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker marker) {
				
				String[] values = new String[] { "Android Android Android Android Android Android Android "
						+ "Android Android Android Android Android Android Android Android Android ", 
						"Android Android Android Android Android Android Android "
						+ "Android Android Android Android Android Android Android Android Android ",
						"Android Android Android Android Android Android Android "
						+ "Android Android Android Android Android Android Android Android Android "};
				
				LayoutInflater inflater = MapActivity.this.getLayoutInflater();
				dlg = inflater.inflate(R.layout.dialog_on_marker_click, null);
				final Dialog dialog = new Dialog(MapActivity.this);
				dialog.setContentView(dlg);
				dialog.setTitle(marker.getTitle());
				
				Button btnOk = (Button) dialog.findViewById(R.id.btn_info_ok);
				Button btnDetal = (Button) dialog.findViewById(R.id.btn_info_detal);
				TextView dname = (TextView) dialog.findViewById(R.id.dialog_info_name);
				ListView lv = (ListView) dialog.findViewById(R.id.dialog_info_list_comment);
				
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(MapActivity.this,
		                android.R.layout.simple_list_item_1, values);
				lv.setAdapter(adapter);
				
				dialog.show();
				
				btnOk.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {dialog.dismiss();}
				});
				
				btnDetal.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MapActivity.this, DetailedInfoActivity.class);
						startActivity(intent);
					}
				});
				
                return true;
			}
		});
	}

}