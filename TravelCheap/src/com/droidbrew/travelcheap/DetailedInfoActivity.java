package com.droidbrew.travelcheap;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import com.droidbrew.travelcheap.adapter.CommentAdapter;
import com.droidbrew.travelkeeper.model.entity.Place;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class DetailedInfoActivity extends Activity {

	Button btnOk, btnAddComment;
	TextView tvLike, tvDisLike, tvName;
	ListView lvComment;
	View dlg = null;
	private Place sendPlace;
	Double lat;
	Double lon;
	CommentAdapter adapter;

	private String title = "";
	private int like = 0;
	private int dislike = 0;

	DecimalFormat df = new DecimalFormat("##.00");

	private double format(double d) {
		return Double.parseDouble(df.format(d).replace(',', '.'));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailed_info);
		Bundle b = getIntent().getExtras();
		lat = b.getDouble("lat");
		lon = b.getDouble("lon");
		title = getIntent().getStringExtra("title");

		btnOk = (Button) findViewById(R.id.detailedOk);
		btnAddComment = (Button) findViewById(R.id.addComment);
		tvLike = (TextView) findViewById(R.id.tvLike);
		tvDisLike = (TextView) findViewById(R.id.tvDisLike);
		tvName = (TextView) findViewById(R.id.tvName);
		lvComment = (ListView) findViewById(R.id.lvComment);

		xmlElement();
	}

	public void xmlElement() {
		GetComments gc = new GetComments();
		gc.execute(format(lat), format(lon));
		adapter = new CommentAdapter(this, new ArrayList<String>());
		lvComment.setAdapter(adapter);

		tvName.setText("");

		btnOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		btnAddComment.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addNewComment();
			}
		});
	}

	public void addNewComment() {
		LayoutInflater inflater = DetailedInfoActivity.this.getLayoutInflater();
		dlg = inflater.inflate(R.layout.dialog_add_comment, null);
		final Dialog dialog = new Dialog(DetailedInfoActivity.this);
		dialog.setContentView(dlg);
		dialog.setTitle(getString(R.string.dialog_new_comment));

		final EditText newComment = (EditText) dialog
				.findViewById(R.id.newComment);
		Button cancel = (Button) dialog.findViewById(R.id.btnClose);
		Button addCommentLike = (Button) dialog
				.findViewById(R.id.AddNewCommentLike);
		Button addCommentDisLike = (Button) dialog
				.findViewById(R.id.AddNewCommentDisLike);

		dialog.show();

		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		addCommentLike.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SendPlace sp = new SendPlace();
				sp.execute(newComment.getText().toString(),
						String.valueOf(true));
				dialog.dismiss();
			}
		});

		addCommentDisLike.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SendPlace sp = new SendPlace();
				sp.execute(newComment.getText().toString(),
						String.valueOf(false));
				dialog.dismiss();
			}
		});
	}

	public String getIMEI() {
		TelephonyManager tm = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (tm == null)
			return null;
		return tm.getDeviceId();
	}

	class GetComments extends AsyncTask<Double, Void, Void> {

		boolean hasComment = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected Void doInBackground(Double... params) {
			try {
				List<Place> list = ((TravelApp) getApplication())
						.getPlaceManager().getPlace(
								format(params[0]) + ":" + format(params[1])
										+ title);
				adapter.clear();
				for (Place pl : list) {
					adapter.add(pl.getComment());
					if (pl.getIMEI().equals(getIMEI()))
						hasComment = true;
				}
				title = list.get(0).getTitle();
				like = ((TravelApp) getApplication()).getPlaceManager()
						.getLikes(list);
				dislike = ((TravelApp) getApplication()).getPlaceManager()
						.getDislikes(list);
			} catch (Exception e) {
				Log.e(MapActivity.class.getName(), e.getMessage(), e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			adapter.notifyDataSetChanged();

			tvName.setText(title);
			tvLike.setText("" + like);
			tvDisLike.setText("" + dislike);
			if (hasComment)
				btnAddComment.setEnabled(false);
		}
	}

	class SendPlace extends AsyncTask<String, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected Void doInBackground(String... params) {
			try {
				sendPlace = new Place();
				sendPlace.setLatitude(lat);
				sendPlace.setLongitude(lon);
				sendPlace.setPlaceId(format(lat) + ":" + format(lon) + title);
				sendPlace.setTitle(title);
				sendPlace.setComment(params[0]);
				sendPlace.setLike(Boolean.parseBoolean(params[1]));
				sendPlace.setPicture(getIMEI());
				sendPlace.setIMEI(getIMEI());

				((TravelApp) getApplication()).getPlaceManager().createPlace(
						sendPlace);
			} catch (Exception e) {
				Log.e(RecommendActivity.class.getName(), e.getMessage(), e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			GetComments gc = new GetComments();
			gc.execute(format(lat), format(lon));

		}
	}

}
