package com.droidbrew.travelcheap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.droidbrew.travelkeeper.model.entity.Place;
import com.google.android.gms.plus.PlusShare;

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
	private Place sendPlace;
	private EditText name, comment = null;
	private CheckBox checkShare;
	private boolean hasCoordinates = false;
	private String imageName = "";
	private ImageView ivName = null, ivGps, ivMap;

	DecimalFormat df = new DecimalFormat("##.00");

	private double format(double d) {
		return Double.parseDouble(df.format(d).replace(',', '.'));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recommend);
		pd = new ProgressDialog(this);
		CheckConnection check = new CheckConnection();
		check.execute();
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Intent intent = getIntent();

		checkShare = (CheckBox) findViewById(R.id.check_share);
		ivName = (ImageView) findViewById(R.id.img_name);
		name = (EditText) findViewById(R.id.name_recommend);
		name.setText(intent.getStringExtra("name"));
		hasName();
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
		ivMap = (ImageView) findViewById(R.id.img_map);
		if (b.getDouble("lat") != 0.0 || b.getDouble("lot") != 0.0) {
			ivMap.setVisibility(ImageView.VISIBLE);
			ivMap.setImageResource(R.drawable.okk);
		} else {
		}

		String title = intent.getStringExtra("title");
		titlee = title;
		setTitle(titlee.toString());
		final String curs = intent.getStringExtra("curs");
		final String tag = intent.getStringExtra("tag");
		cat.setText(" " + tag);
		spent = (TextView) findViewById(R.id.spent_recommend);
		spent.setText(" " + dspent + " " + curs);
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		ivGps = (ImageView) findViewById(R.id.img_gps);
		Button btnSave = (Button) findViewById(R.id.btn_save_recommend);
		btnSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (checkShare.isChecked()) {
					if ((name).getText().toString().equals("")) {
						AlertDialog.Builder build = new AlertDialog.Builder(
								RecommendActivity.this);
						build.setTitle(getString(R.string.enter_name));
						build.setNegativeButton("Ok",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
									}
								});
						build.show();
						return;
					}
					if (loc == null) {
						if (latg == 0.0 && lotg == 0.0) {
							AlertDialog.Builder build = new AlertDialog.Builder(
									RecommendActivity.this);
							build.setTitle(getString(R.string.enter_coordinat));
							build.setNegativeButton("Ok",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									});
							build.show();
							return;
						}
					}
					hasName();
					
					postHTTP();
					shareCheck();
				} else {
					if ((name).getText().toString().equals("")) {
						AlertDialog.Builder build = new AlertDialog.Builder(
								RecommendActivity.this);
						build.setTitle(getString(R.string.enter_name));
						build.setNegativeButton("Ok",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
									}
								});
						build.show();
						return;
					}
					if (loc == null) {
						if (latg == 0.0 && lotg == 0.0) {
							AlertDialog.Builder build = new AlertDialog.Builder(
									RecommendActivity.this);
							build.setTitle(getString(R.string.enter_coordinat));
							build.setNegativeButton("Ok",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									});
							build.show();
							return;
						}
					}
					ivName.setVisibility(ImageView.VISIBLE);
					ivName.setImageResource(R.drawable.okk);
					postHTTP();
					// GoHome();
				}
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
				finish();
			}
		});
	}

	public void hasName() {
		name.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					if ((name).getText().toString().equals("")) {
						ivName.setVisibility(ImageView.VISIBLE);
						ivName.setImageResource(R.drawable.not);
						AlertDialog.Builder build = new AlertDialog.Builder(
								RecommendActivity.this);
						build.setTitle(getString(R.string.enter_name));
						build.setNegativeButton("Ok",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
									}
								});
						build.show();
					} else {
						ivName.setVisibility(ImageView.VISIBLE);
						ivName.setImageResource(R.drawable.okk);
					}
				}
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

	private void imageButtonClick() {
		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(cameraIntent, CAMERA_REQUEST);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
			Boolean isSDPresent = android.os.Environment
					.getExternalStorageState().equals(
							android.os.Environment.MEDIA_MOUNTED);

			if (isSDPresent) {
				File mydir = new File(Environment.getExternalStorageDirectory()
						+ "/TravelKeeper/");
				mydir.mkdirs();
				Bitmap photo = (Bitmap) data.getExtras().get("data");
				imageBtn.setImageBitmap(photo);
				String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
						.format(new Date());
				imageName = getIMEI(this) + timeStamp + ".png";
				FileOutputStream out = null;
				try {
					out = new FileOutputStream(
							Environment.getExternalStorageDirectory()
									+ "/TravelKeeper/" + imageName);
					photo.compress(Bitmap.CompressFormat.PNG, 90, out);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						out.close();
					} catch (Throwable ignore) {
					}
				}
			}
		} else if (requestCode == 0 && resultCode == RESULT_OK) {
			Intent intent = new Intent(RecommendActivity.this,
					HomeActivity.class);
			startActivity(intent);
			sendNotif();
			finish();
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

	private void shareCheck() {
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
			startActivityForResult(shareIntent, 0);
		} else {
			loc = new Location("");
			loc.setLatitude(latg);
			loc.setLongitude(lotg);
			pd.dismiss();
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
			startActivityForResult(shareIntent, 0);
		}
	}

	void sendNotif() {
		Uri notifSound = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this).setSmallIcon(R.drawable.buck_transp)
				.setContentTitle(titlee).setSound(notifSound)
				.setAutoCancel(true).setContentText(name.getText().toString());

		Intent resultIntent = new Intent(this, HomeActivity.class);
		resultIntent.putExtra("notif", "notif");
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(HomeActivity.class);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(1, mBuilder.build());

	}

	public String getIMEI(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (tm == null)
			return null;
		return tm.getDeviceId();
	}

	public void GoHome() {
		Intent intent = new Intent(RecommendActivity.this, HomeActivity.class);
		startActivity(intent);
		finish();
	}

	public void postHTTP() {
		if (loc == null) {
			loc = new Location("");
			loc.setLatitude(latg);
			loc.setLongitude(lotg);
		}
		SendPlace sp = new SendPlace();
		sp.execute();
		SendPicture spic = new SendPicture();
		spic.execute();

	}

	class SendPlace extends AsyncTask<Void, Void, Void> {

		int code = 0;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd.setTitle(getString(R.string.AdminDialogTitleUpd));
			pd.setMessage(getString(R.string.dialog_sending_massage));
			pd.setCancelable(false);
			pd.dismiss();
			pd.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				sendPlace = new Place();
				sendPlace.setLatitude(loc.getLatitude());
				sendPlace.setLongitude(loc.getLongitude());
				sendPlace.setPlaceId(format(loc.getLatitude()) + ":"
						+ format(loc.getLongitude())
						+ name.getText().toString());
				sendPlace.setTitle(name.getText().toString());
				sendPlace.setComment(comment.getText().toString());
				sendPlace
						.setLike((titlee.equals(getString(R.string.recommend))) ? true
								: false);
				sendPlace.setPicture(imageName);
				sendPlace.setCreatedOn(new Date().getTime());
				sendPlace.setIMEI(getIMEI(RecommendActivity.this));

				code = ((TravelApp) getApplication()).getPlaceManager()
						.createPlace(sendPlace);
			} catch (Exception e) {
				Log.e(RecommendActivity.class.getName(), e.getMessage(), e);
				code = 0;
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			pd.dismiss();
			if(checkShare.isChecked())
				return;
			AlertDialog.Builder dial = new AlertDialog.Builder(
					RecommendActivity.this);
			dial.setNegativeButton("ok", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent ontent = new Intent(RecommendActivity.this,
							HomeActivity.class);
					startActivity(ontent);
					finish();
				}
			});

			if (code == 200) {
				dial.setTitle("Ok");
				dial.setMessage("Place was created successfully");
			} else {
				dial.setTitle("Error");
				dial.setMessage("Problem saving data");
			}
			dial.create().show();
		}
	}

	class SendPicture extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				Log.e("FILE NAME", imageName);
				if (!"".equals(imageName)) {
					String path = Environment.getExternalStorageDirectory()
							+ "/TravelKeeper/";
					Log.e("FILE PATH + NAME", (path + imageName));
					((TravelApp) getApplication()).getPlaceManager()
							.uploadImage(path + imageName);
				}
			} catch (Exception e) {
				Log.e(RecommendActivity.class.getName(), e.getMessage(), e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
		}
	}

	class SearchC extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pd.setTitle("GPS");
			pd.setMessage(getString(R.string.recommend_btn_gps_search));
			pd.setCancelable(false);
			pd.setButton(DialogInterface.BUTTON_NEGATIVE,
					getString(R.string.AdminDialogNB),
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							locationManager.removeUpdates(locationListener);
							ivGps.setVisibility(ImageView.VISIBLE);
							ivGps.setImageResource(R.drawable.not);
							dialog.dismiss();
						}
					});
			pd.dismiss();
			pd.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			while (!hasCoordinates) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			if (loc != null) {
				ivGps.setVisibility(ImageView.VISIBLE);
				ivGps.setImageResource(R.drawable.okk);
				ivMap.setVisibility(ImageView.INVISIBLE);
			}
			pd.dismiss();
		}

	}

	class CheckConnection extends AsyncTask<Void, Void, Boolean> {

		private boolean check = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd.setTitle("Connection");
			pd.setMessage("Please wait");
			pd.setCancelable(false);
			pd.dismiss();
			pd.show();
		}

		@Override
		protected Boolean doInBackground(Void... params) {

			try {
				check = ((TravelApp) getApplication()).getPlaceManager()
						.checkConnection();
				Thread.sleep(1000);
			} catch (Exception e) {
				Log.e(RecommendActivity.class.getName(), e.getMessage(), e);
				check = false;
			}
			return check;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			pd.dismiss();
			if (!check) {
				AlertDialog.Builder dial = new AlertDialog.Builder(
						RecommendActivity.this);
				dial.setTitle("Error");
				dial.setMessage("Server unavailable");
				dial.setNegativeButton("ok",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Intent ontent = new Intent(
										RecommendActivity.this,
										HomeActivity.class);
								startActivity(ontent);
								finish();
							}
						});
				dial.create().show();
			}
		}

	}

}
