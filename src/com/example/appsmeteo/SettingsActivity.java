package com.example.appsmeteo;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;



public class SettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		this.setTitle(R.string.title_about);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/*@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.b_opt_fav:
				this.startActivity(new Intent(SettingsActivity.this, SettingsActivity.class));
				return true;
			case R.id.b_opt_previ:
				Intent intent_previ = new Intent(SettingsActivity.this, PrevjoursActivity.class);
				startActivity(intent_previ);
				return true;
			case R.id.b_opt_main:
				this.startActivity(new Intent(SettingsActivity.this, MainActivity.class));
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}*/

}
