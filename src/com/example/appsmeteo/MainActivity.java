package com.example.appsmeteo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.example.appsmeteo.bdd.City;
import com.example.appsmeteo.bdd.Favoris;
import com.example.appsmeteo.controller.ParserXMLTask;
import com.example.appsmeteo.web.api.Openweathermaps;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class MainActivity extends Myactivity{
	private TextView textView;
	private ProgressBar progress;
	private Myactivity MainActivity = this;
	protected Favoris fav = null;
	private LinearLayout listfav;
	private boolean rech = false;
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		
		textView= (TextView) findViewById(R.id.aff_site);
		progress= (ProgressBar) findViewById(R.id.progressBar_connection);
		
		this.rech = false;
		
		
		OnClickListener ButtonConnnect = new OnClickListener() {
			EditText villerecherche = (EditText) findViewById(R.id.fieldsearchville);
			public void onClick(View actuelView) {
				if(!rech){
					rech = true;
					act_button_recherche(villerecherche.getText().toString());
				}
				else
					Toast.makeText(getBaseContext(),R.string.toast_rech_cours,Toast.LENGTH_SHORT).show();
					
			}
		};
		Button bouton = (Button) findViewById(R.id.bsearch);
		bouton.setOnClickListener(ButtonConnnect);
		
		final EditText villerecherche = (EditText) findViewById(R.id.fieldsearchville);
		villerecherche.setOnEditorActionListener(new OnEditorActionListener() {
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
		        	if(!rech){
						rech = true;
						act_button_recherche(villerecherche.getText().toString());
					}
					else
						Toast.makeText(getBaseContext(),R.string.toast_rech_cours,Toast.LENGTH_SHORT).show();
		        	return true;
		        }
		        return false;
		    }
		});
		
		this.listfav = (LinearLayout) findViewById(R.id.listfav);		
		
		this.fav = new Favoris();
		this.loadfavoris();
		int i;
		for(i=0;i<fav.getmaxfav();i++){
			listfav.addView(createNewLineFavoris(i));
		}
		
	}
	
	public TextView createNewLineFavoris(final int id) {
		final Favoris favo = this.fav;
	    final TextView textView = new TextView(this);
	    final LayoutParams lparams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	    textView.setLayoutParams(lparams);
	    textView.setText(this.fav.getFav(id));
	    if(id%2 == 0)
	    	textView.setBackgroundResource(R.color.Background_line);
	    else
	    	textView.setBackgroundResource(R.color.Background_line_2);
	    textView.setPadding(0, 15, 0, 15);
	    textView.setGravity(Gravity.CENTER);
	    textView.setTextSize(25);
	    textView.setTypeface(null, Typeface.BOLD);
	    
		textView.setOnTouchListener(new OnTouchListener() {           
			int i = 0;
			int fristx;
			boolean destroy = false;
			final int DIST = 300;
			final int DIST_red = 50;
			
		public boolean onTouch(View v, MotionEvent event){
        	    int action = MotionEventCompat.getActionMasked(event);
               switch(action) {
        	        case (MotionEvent.ACTION_DOWN) :
        	        	i = 0;
        	        	fristx = (int) event.getAxisValue(0);
        	        	textView.setBackgroundResource(R.color.andr_blue);
        	            return true;
        	        case (MotionEvent.ACTION_MOVE) :
        	        	i++;
        	        	int x1 = (int) (event.getAxisValue(0) - fristx);
        	        	int x2 = (int) (fristx - event.getAxisValue(0));
        	        	int x = 0;
        	        	if(x1 > x2)
        	        		x = x1;
        	        	else 
        	        		x = x2;
        	        	if(x > DIST_red)
        	        		textView.setBackgroundResource(R.color.andr_red);
        	        	if(!destroy && (x > DIST) ){
        	        		Log.i("log.appsmeteo.favoris","Suppresion favori : " + id);
        	        		favo.supp(id);
            	        	textView.setHeight(0);
            	        	destroy = true;
        	        	}        	        		
        	            return true;
        	        case (MotionEvent.ACTION_UP) :
        	        	if(i<10){
        	        		EditText villerecherche = (EditText) findViewById(R.id.fieldsearchville);
        	        		villerecherche.setText(fav.getFav(id));
        	        		Log.i("log.appsmeteo.favoris","Recherche : " + id);
        	        	}
        	        	if(!destroy){
        	        		if(id%2 == 0)
                		    	textView.setBackgroundResource(R.color.Background_line);
                		    else
                		    	textView.setBackgroundResource(R.color.Background_line_2);
        	        	}
        	        
        	        	i = 0;
        	            return true;
        	        default : 
        	            return false;
        	    } 
           }
		});
		
	    return textView;
	}

	public void savefavoris(){
		Log.d("log.appsmeteo.file","Save favoris");
		BufferedWriter writer = null;
        try {
           File dir = getDir("Files",MODE_PRIVATE);
           File newfile = new File(dir.getAbsolutePath() + File.separator + "favoris.txt");
           newfile.createNewFile();
           writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newfile)));
           writer.write(this.fav.toString());
        } catch (Exception e) {
               e.printStackTrace();
        } finally {
           if (writer != null) {
              try {
                 writer.close();
              } catch (IOException e) {
                 e.printStackTrace();
              }
           }
        }
	}
	
	public void loadfavoris(){
		 Log.d("log.appsmeteo.file","load favoris");
		 File dir = getDir("Files",MODE_PRIVATE);
         File newfile = new File(dir.getAbsolutePath() + File.separator +"favoris.txt");
         BufferedReader input = null;
         try {
           input = new BufferedReader(new InputStreamReader( new FileInputStream(newfile)));
           String line;
           while ((line = input.readLine()) != null)
        	   	this.fav.addline(line);
         } catch (Exception e) {
                e.printStackTrace();
         } finally {
	         if (input != null) {
	           try {
	            input.close();
	            } catch (IOException e) {
	               e.printStackTrace();
	            }
	         }
         }
	}
	
	public void addfavoris(City ville){
		this.fav.add(ville.getName(), ville.getPays());
	}

	
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.opt_addfav:
				this.savefavoris();
				return true;
			case R.id.action_settings:
				Intent intent_settings = new Intent(MainActivity.this, SettingsActivity.class);
				startActivity(intent_settings);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}	
	public boolean isNetworkAvailable() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if(networkInfo!= null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}	
	public boolean act_button_recherche(String P_ville) {
		if(!this.isNetworkAvailable()){
			fixErr(4);
			return false;
		}
		else{
			Resources resources = getResources();
			Toast.makeText(getBaseContext(),resources.getString(R.string.SeaEC)+" "+P_ville,Toast.LENGTH_SHORT).show();
		}
		DownloadWebPageTask task = new DownloadWebPageTask();
		P_ville = P_ville.trim();
		P_ville = P_ville.replaceAll(", ", ",");
		P_ville = P_ville.replaceAll(" ", "-");
		Openweathermaps owm = new Openweathermaps(P_ville);
		Log.d("log.appsmeteo.main.connect", owm.getURL());
		task.execute(owm.getURL());
		return true;
	}
	
	private class DownloadWebPageTask extends AsyncTask<String, Integer, String> {
		protected String doInBackground(String... urls) {
			String response = "";
			for (String url : urls) {
				DefaultHttpClient client = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);
				try {
					HttpResponse execute = client.execute(httpGet);
					Long size = execute.getEntity().getContentLength();
					progress.setMax(size.intValue());
					progress.setProgress(0);
					InputStream content = execute.getEntity().getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(content));
					String ligne;
					while ((ligne = reader.readLine()) != null) {
						response = response + ligne;
						publishProgress(ligne.length());
						//Log.i("log.appsmeteo.connect",ligne);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return response;
		}
		protected void onProgressUpdate(Integer... values) {
			progress.incrementProgressBy(values[0]);
		}
		protected void onPostExecute(String result) {
			//Log.d("log.appsmeteo.main.connect", "Connection Réussi");
			ParserXMLTask parser = new ParserXMLTask();
			parser.setville(new City());
			parser.settxtview(textView);
			parser.setclass(MainActivity);
			//result = result.replaceAll("^.*<", "<");
			Log.i("log.appsmeteo.parser",result);
			if(result == "")
				fixErr(3);
			else
				parser.execute(result);
		}
	}

	public void fixfinish(City Maville) {
		Intent intent_previ = new Intent(this, PrevjoursActivity.class);
		intent_previ.putExtra("Extra_city", Maville);
		startActivity(intent_previ);
		this.addfavoris(Maville);
		this.listfav.addView(createNewLineFavoris(this.fav.getmaxfav() -1));
		progress.setProgress(0);
		rech = false;
	}

	public void fixErr(int id) {
		switch(id){
			case 1 : // l'API n'a pas trouvé la ville
				Toast.makeText(getBaseContext(),R.string.toast_err_ville_inconnu,Toast.LENGTH_LONG).show();
				Log.w("log.appsmeteo.parser.valid","Ville non trouvé : " + id);
				break;
			case 2 ://probléme parsing XML => normalement résolu par la suppresion du buffer
				Toast.makeText(getBaseContext(),R.string.toast_err_parsing,Toast.LENGTH_LONG).show();
				Log.w("log.appsmeteo.parser.valid","Erreur parsing : " + id);
				break;
			case 3 ://fail de l'api !!!
				Toast.makeText(getBaseContext(),R.string.toast_err_api,Toast.LENGTH_LONG).show();
				Log.w("log.appsmeteo.parser.valid","Erreur API : " + id);
				break;
			case 4 ://Internet désactivé
				Toast.makeText(getBaseContext(),R.string.toast_err_reseau,Toast.LENGTH_LONG).show();
				Log.w("log.appsmeteo.internet","Erreur réseau internet : " + id);
				break;
			default://euh on n'arrive pas ici normalement ...
				Toast.makeText(getBaseContext(),R.string.toast_err_inco,Toast.LENGTH_LONG).show();
				Log.w("log.appsmeteo.parser.valid","Erreur : " + id);
				break;
		}
		rech = false;
		progress.setProgress(0);
	}
	

}
