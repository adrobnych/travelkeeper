package com.droidbrew.travelcheap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
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
	public Marker marker;
	
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
		
		marker = map.addMarker(new MarkerOptions()
				.position(new LatLng(lat+ 00.000025, lot-00.000050))
				.title("red"));
		
		
		map.addMarker(new MarkerOptions()
		.position(new LatLng(lat-+ 00.000025, lot+-00.000050))
		.title("test"));
		
		map.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker marker) {
				AlertDialog.Builder build = new AlertDialog.Builder(MapActivity.this);
				build.setTitle(marker.getTitle());
				build.setNegativeButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});
				build.show();
				Toast.makeText(MapActivity.this, marker.getTitle(),1000).show();
                return true;
			}
		});
		
		
		map.addMarker(new MarkerOptions()
	      .position(new LatLng(lat, lot))
	      .title("My Test")
	      .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
	}

}