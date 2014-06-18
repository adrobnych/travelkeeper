package com.droidbrew.travelcheap;

import java.util.Date;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

public class MapActivity extends FragmentActivity {

	SupportMapFragment mapFragment;
	private LocationManager locationManager;
	TextView tvLocationGPS;
	GoogleMap map;
	final String TAG = "myLogs";
	public Location loc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		tvLocationGPS = (TextView) findViewById(R.id.tvLocationGPS);
		mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.fragment_map);
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
			      tvLocationGPS.setText(formatLocation(location));
			    }
			  }

	private void init() {
		map.setOnMapClickListener(new OnMapClickListener() {

			@Override
			public void onMapClick(LatLng latLng) {
				Log.d(TAG, "onMapClick: " + latLng.latitude + ","
						+ latLng.longitude);
			}
		});

		map.setOnMapLongClickListener(new OnMapLongClickListener() {

			@Override
			public void onMapLongClick(LatLng latLng) {
				Log.d(TAG, "onMapLongClick: " + latLng.latitude + ","
						+ latLng.longitude);
			}
		});

		map.setOnCameraChangeListener(new OnCameraChangeListener() {

			@Override
			public void onCameraChange(CameraPosition camera) {
				Log.d(TAG, "onCameraChange: " + camera.target.latitude + ","
						+ camera.target.longitude);
			}
		});
	}

	public void onClickTest(View view) {
				
		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(new LatLng(loc.getLatitude(), loc.getLongitude())).zoom(18)
				.build();
		CameraUpdate cameraUpdate = CameraUpdateFactory
				.newCameraPosition(cameraPosition);
		map.animateCamera(cameraUpdate);
		 map.addMarker(new MarkerOptions()
	      .position(new LatLng(loc.getLatitude(), loc.getLongitude()))
	      .title("My home"));
	}
}