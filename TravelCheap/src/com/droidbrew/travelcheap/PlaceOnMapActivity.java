package com.droidbrew.travelcheap;

import java.util.Date;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PlaceOnMapActivity extends FragmentActivity {

	SupportMapFragment mapFragment;
	private LocationManager locationManager;
	GoogleMap map;
	final String TAG = "myLogs";
	public Location loc;
	public Double lat, lot, dspent;
	public String name, comment, title, curs, tag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place_on_map);

		Intent intent = getIntent();
		String iname = intent.getStringExtra("iname");
		String icomment = intent.getStringExtra("icomment");
		String title_map = intent.getStringExtra("title_map");
		Bundle b = getIntent().getExtras();
		Double dspent_map = b.getDouble("dspent_map");
		String curs_map = intent.getStringExtra("curs_map");
		String tag_map = intent.getStringExtra("tag_map");

		
		curs = curs_map;
		tag = tag_map;
		dspent = dspent_map;
		title =title_map;		
		name = iname;
		comment = icomment;
		
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.fragment_place_on_map);
		map = mapFragment.getMap();
		if (map == null) {
			finish();
			return;
		}
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				1000 * 10, 10, locationListener);
		init();
	}

	public LocationListener locationListener = new LocationListener() {

		@Override
		public void onLocationChanged(Location location) {
			showLocation(location);
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
			showLocation(locationManager.getLastKnownLocation(provider));
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}
	};

	public String formatLocation(Location location) {
		if (location == null)
			return "";
		loc = location;
		return String.format(
				"Coordinates: lat = %1$.4f, lon = %2$.4f, time = %3$tF %3$tT",
				location.getLatitude(), location.getLongitude(), new Date(
						location.getTime()));
	}

	private void showLocation(Location location) {
		if (location == null)
			return;
		if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
		}
	}

	private void init() {
		map.setOnMapLongClickListener(new OnMapLongClickListener() {

			@Override
			public void onMapLongClick(LatLng latLng) {
				
				map.addMarker(new MarkerOptions()
				.position(new LatLng(latLng.latitude, latLng.longitude))
				.title(name).snippet(comment));
				
				lat = latLng.latitude;
				lot = latLng.longitude;
				
				Intent intent = new Intent(PlaceOnMapActivity.this, RecommendActivity.class);
				intent.putExtra("lat", lat);
				intent.putExtra("lot", lot);
				intent.putExtra("title", title);
				intent.putExtra("key", dspent);
				intent.putExtra("curs", curs);
				intent.putExtra("tag", tag);
				intent.putExtra("name", name);
				intent.putExtra("comment", comment);
				
				startActivity(intent);
				finish();
				Log.d(TAG, "onMapLongClick: " + latLng.latitude + ","
						+ latLng.longitude);
			}
		});
	}

}