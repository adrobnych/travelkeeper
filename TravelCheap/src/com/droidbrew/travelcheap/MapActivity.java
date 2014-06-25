package com.droidbrew.travelcheap;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.droidbrew.travelcheap.adapter.CommentAdapter;
import com.droidbrew.travelkeeper.model.entity.Place;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity {

	SupportMapFragment mapFragment;
	GoogleMap map;
	Button test;
	View dlg = null;
	private ProgressDialog pd;
	CommentAdapter adapter;
	DecimalFormat df = new DecimalFormat("##.00");
	private double format(double d) {
		return Double.parseDouble(df.format(d).replace(',', '.'));
	}

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
		pd = new ProgressDialog(this);
		init();
		getAllPlaces all = new getAllPlaces();
		all.execute();
	}

	private void init() {
		map.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(final Marker marker) {

				GetComments gc = new GetComments();
				gc.execute(format(marker.getPosition().latitude) + ":" + 
						format(marker.getPosition().longitude) + 
						marker.getTitle());
				LayoutInflater inflater = MapActivity.this.getLayoutInflater();
				dlg = inflater.inflate(R.layout.dialog_on_marker_click, null);
				final Dialog dialog = new Dialog(MapActivity.this);
				dialog.setContentView(dlg);
				dialog.setTitle(marker.getTitle());

				Button btnOk = (Button) dialog.findViewById(R.id.btn_info_ok);
				Button btnDetal = (Button) dialog
						.findViewById(R.id.btn_info_detal);
				ListView lv = (ListView) dialog
						.findViewById(R.id.dialog_info_list_comment);

				adapter = new CommentAdapter(
						MapActivity.this,
						new ArrayList<String>());
								lv.setAdapter(adapter);

				dialog.show();

				btnOk.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

				btnDetal.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MapActivity.this,
								DetailedInfoActivity.class);
						intent.putExtra("lat", marker.getPosition().latitude);
						intent.putExtra("lon", marker.getPosition().longitude);
						intent.putExtra("title", marker.getTitle());
						startActivity(intent);
					}
				});

				return true;
			}
		});
	}
	


	class getAllPlaces extends AsyncTask<Void, Void, Void> {
		
		Map<String, List<Place>> places = null;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pd.setTitle(getString(R.string.AdminDialogTitleUpd));
			pd.setMessage(getString(R.string.recommend_dialog_maps));
			pd.setCancelable(false);
			pd.dismiss();
			pd.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				places = ((TravelApp)getApplication()).getPlaceManager().getPlaces();
				
			} catch (Exception e) {
				Log.e(MapActivity.class.getName(), e.getMessage(), e);
			} 
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if(places != null)
			for (String c : places.keySet()) {
				int rating = ((TravelApp) getApplication())
						.getPlaceManager().getLikes(places.get(c)) -
						((TravelApp) getApplication())
						.getPlaceManager().getDislikes(places.get(c));
				BitmapDescriptor bd;
				if (rating > 0)
					bd = BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
				else if (rating == 0)
					bd = BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
				else
					bd = BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_RED);
				map.addMarker(new MarkerOptions()
						.position(new LatLng(places.get(c).get(0).getLatitude(), 
								places.get(c).get(0).getLongitude()))
						.title(places.get(c).get(0).getTitle()).icon(bd));
			}
			pd.dismiss();
		}
	}
	
	class GetComments extends AsyncTask<String, Void, Void> {
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected Void doInBackground(String... params) {
			try {
				List<Place> list = ((TravelApp) getApplication())
						.getPlaceManager().getPlace(
								params[0]);
				for (Place pl : list) {
					adapter.add(pl.getComment());
				}
			} catch (Exception e) {
				Log.e(MapActivity.class.getName(), e.getMessage(), e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			adapter.notifyDataSetChanged();
		}
	}

}