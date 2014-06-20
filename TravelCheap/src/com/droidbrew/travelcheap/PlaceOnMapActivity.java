package com.droidbrew.travelcheap;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PlaceOnMapActivity extends FragmentActivity {

	SupportMapFragment mapFragment;
	GoogleMap map;
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
		title = title_map;
		name = iname;
		comment = icomment;

		mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.fragment_place_on_map);
		map = mapFragment.getMap();
		if (map == null) {
			finish();
			return;
		}
		init();
	}

	private void init() {
		
		final Button btnOpenPopup = (Button)findViewById(R.id.openpopup);
        btnOpenPopup.setOnClickListener(new Button.OnClickListener(){

   @Override
   public void onClick(View arg0) {
    LayoutInflater layoutInflater 
     = (LayoutInflater)getBaseContext()
      .getSystemService(LAYOUT_INFLATER_SERVICE);  
    View popupView = layoutInflater.inflate(R.layout.popup, null);  
             final PopupWindow popupWindow = new PopupWindow(
               popupView, 
               LayoutParams.WRAP_CONTENT,  
                     LayoutParams.WRAP_CONTENT);  
             
             Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
             btnDismiss.setOnClickListener(new Button.OnClickListener(){

     @Override
     public void onClick(View v) {
      popupWindow.dismiss();
     }});
               
             popupWindow.showAsDropDown(btnOpenPopup);
         
   }});
		
		map.setOnMapLongClickListener(new OnMapLongClickListener() {

			@Override
			public void onMapLongClick(LatLng latLng) {

				map.addMarker(new MarkerOptions()
						.position(new LatLng(latLng.latitude, latLng.longitude))
						.title(name).snippet(comment));

				lat = latLng.latitude;
				lot = latLng.longitude;

				Intent intent = new Intent(PlaceOnMapActivity.this,
						RecommendActivity.class);
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

			}
		});
	}

}