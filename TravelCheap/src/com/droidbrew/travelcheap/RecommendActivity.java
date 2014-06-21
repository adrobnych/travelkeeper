package com.droidbrew.travelcheap;

import java.net.URL;

import com.google.android.gms.plus.PlusShare;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class RecommendActivity extends Activity {
	public TextView cat, spent;
	private LocationManager locationManager;
	public Location loc = null;
	StringBuilder sbGPS = new StringBuilder();
	NotificationManager nm;
	private static final int CAMERA_REQUEST = 1888;
	private ImageButton imageBtn;
	private ProgressDialog pd;
	public Double latg, lotg;
	public String titlee;

	private boolean hasCoordinates = false;

	private EditText name, comment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recommend);
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Intent intent = getIntent();

		pd = new ProgressDialog(this);

		name = (EditText) findViewById(R.id.name_recommend);
		name.setText(intent.getStringExtra("name"));

		comment = (EditText) findViewById(R.id.comments);
		comment.setText(intent.getStringExtra("comment"));

		imageBtn = (ImageButton) this.findViewById(R.id.image);

		cat = (TextView) findViewById(R.id.cat_recommend);

		Bundle b = getIntent().getExtras();
		final Double dspent = b.getDouble("key");

		Double lat = b.getDouble("lat");
		latg = lat;
		Double lot = b.getDouble("lot");
		lotg = lot;

		String title = intent.getStringExtra("title");
		titlee = title;
		setTitle(titlee.toString());

		final String curs = intent.getStringExtra("curs");
		final String tag = intent.getStringExtra("tag");
		cat.setText(" " + tag);

		spent = (TextView) findViewById(R.id.spent_recommend);
		spent.setText(" " + dspent + " " + curs);

		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		Button btnShare = (Button) findViewById(R.id.btn_share);
		btnShare.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				shareButtonClick();
			}
		});

		Button btnSave = (Button) findViewById(R.id.btn_save_recommend);
		btnSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sendNotif();
			}
		});

		imageBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				imageButtonClick();
			}
		});

		Button btnGPS = (Button) findViewById(R.id.btn_gps);
		btnGPS.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (hasGPS())
					searchCoordinates();
			}
		});

		Button btnMaps = (Button) findViewById(R.id.btn_maps);
		btnMaps.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent placeMapIntent = new Intent(RecommendActivity.this,
						PlaceOnMapActivity.class);
				placeMapIntent.putExtra("iname", name.getText().toString());
				placeMapIntent.putExtra("icomment", comment.getText()
						.toString());
				placeMapIntent.putExtra("title_map", titlee);
				placeMapIntent.putExtra("dspent_map", dspent);
				placeMapIntent.putExtra("curs_map", curs);
				placeMapIntent.putExtra("tag_map", tag);
				startActivity(placeMapIntent);
			}
		});
	}

	private boolean hasGPS() {
		LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(getString(R.string.dialog_gps_title));
			builder.setMessage(getString(R.string.dialog_gps_massage));
			builder.setPositiveButton(getString(R.string.dialog_gps_yes),
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent i = new Intent(
									Settings.ACTION_LOCATION_SOURCE_SETTINGS);
							startActivity(i);
						}
					});
			builder.setNegativeButton(getString(R.string.dialog_gps_no),
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					});
			builder.create().show();
			return false;
		}
		return true;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
			Bitmap photo = (Bitmap) data.getExtras().get("data");
			imageBtn.setImageBitmap(photo);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (hasGPS() && loc == null) {
		}
	}

	private void searchCoordinates() {
		hasCoordinates = false;
		SearchC sc = new SearchC();
		sc.execute();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				1000 * 1, 10, locationListener);
	}

	private LocationListener locationListener = new LocationListener() {

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

	private void showLocation(Location location) {

		if (location == null)
			return;
		hasCoordinates = true;
		loc = location;
		if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
		}
	}

	private void imageButtonClick() {
		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(cameraIntent, CAMERA_REQUEST);
	}

	private void shareButtonClick() {
		if (loc != null) {
			Intent shareIntent = new PlusShare.Builder(RecommendActivity.this)
					.setType("text/plain")
					.setText(
							getString(R.string.recommend).toString()
									+ "\n"
									+ name.getText().toString()
									+ "\n"
									+ comment.getText().toString()
									+ "\n"
									+ Uri.parse("https://www.google.com.ua/maps/@"
											+ loc.getLatitude()
											+ ","
											+ loc.getLongitude() + ",16z?hl=ru"))
					.setContentUrl(
							Uri.parse("https://play.google.com/store/apps/details?id=com.droidbrew.travelcheap"))
					.getIntent();
			Log.d("LOC", "!= null");
			startActivityForResult(shareIntent, 0);
		} else {
			Intent shareIntent = new PlusShare.Builder(RecommendActivity.this)
					.setType("text/plain")
					.setText(
							getString(R.string.recommend).toString()
									+ "\n"
									+ name.getText().toString()
									+ "\n"
									+ comment.getText().toString()
									+ "\n"
									+ Uri.parse("https://www.google.com.ua/maps/@"
											+ latg + "," + lotg + ",16z?hl=ru"))
					.setContentUrl(
							Uri.parse("https://play.google.com/store/apps/details?id=com.droidbrew.travelcheap"))
					.getIntent();
			Log.d("LOC", "== null");
			startActivityForResult(shareIntent, 0);
		}
	}

	void sendNotif() {
		Notification notif = new Notification(R.drawable.buck_transp,
				getString(R.string.notif_title), System.currentTimeMillis());
		Intent intent = new Intent(this, HomeActivity.class);
		intent.putExtra("notif", "notif");
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
		notif.setLatestEventInfo(this, titlee,
				name.getText().toString(), pIntent);
		notif.flags |= Notification.FLAG_AUTO_CANCEL;
		nm.notify(1, notif);
		
	}

	class SearchC extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd.setTitle("GPS");
			pd.setMessage(getString(R.string.recommend_btn_gps_search));
			pd.setCancelable(false);
			pd.dismiss();
			pd.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			while (!hasCoordinates) {
				try {
					Thread.sleep(1000);
					Log.i("GPS", "Location " + loc + " is - " + hasCoordinates);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			pd.dismiss();
		}
	}

}
