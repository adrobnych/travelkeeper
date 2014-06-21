package com.droidbrew.travelcheap;

import com.google.android.gms.internal.di;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class DetailedInfoActivity extends Activity{

	Button btnOk, btnAddComment;
	TextView tvLike, tvDisLike, tvName;
	ListView lvComment;
	View dlg = null;
	
	 @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_detailed_info);
	    
	    btnOk = (Button)findViewById(R.id.detailedOk);
	    btnAddComment = (Button)findViewById(R.id.addComment);
	    tvLike = (TextView)findViewById(R.id.tvLike);
	    tvDisLike = (TextView)findViewById(R.id.tvDisLike);
	    tvName = (TextView)findViewById(R.id.tvName);
	    lvComment = (ListView)findViewById(R.id.lvComment);
	    
	    xmlElement();
	 }
	 
	 public void xmlElement(){
		 
		 String[] values = new String[] { "Android Android Android Android Android Android Android "
					+ "Android Android Android Android Android Android Android Android Android ", 
					"Android Android Android Android Android Android Android "
					+ "Android Android Android Android Android Android Android Android Android ",
					"Android Android Android Android Android Android Android "
					+ "Android Android Android Android Android Android Android Android Android "};
		 
		 ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	                android.R.layout.simple_list_item_1, values);
		 lvComment.setAdapter(adapter);
		 
		 tvName.setText("Troll");
		 
		 btnOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		 
		 btnAddComment.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addNewComment();
			}
		});
	 }
	 
	 public void addNewComment(){
		 LayoutInflater inflater = DetailedInfoActivity.this.getLayoutInflater();
			dlg = inflater.inflate(R.layout.dialog_add_comment, null);
			final Dialog dialog = new Dialog(DetailedInfoActivity.this);
			dialog.setContentView(dlg);
			dialog.setTitle("New Comment");
			
			EditText newComment= (EditText) dialog.findViewById(R.id.newComment);
			Button cancel = (Button) dialog.findViewById(R.id.btnClose);
			Button addComment = (Button)dialog.findViewById(R.id.AddNewComment);
			
			dialog.show();
			
			cancel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();}
			});
			
			addComment.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
				}
			});
	 }
}
