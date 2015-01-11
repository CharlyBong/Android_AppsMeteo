package com.example.appsmeteo.controller;

import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.example.appsmeteo.Myactivity;
import com.example.appsmeteo.bdd.City;

public class ParserXMLTask extends AsyncTask<String, Integer, City>{
	private City maville;
	private TextView textView;
	private Myactivity main;
	
	public void setville(City maville){
		this.maville = maville;
	}
	
	public void settxtview(TextView textView){
		this.textView = textView;
	}
	
	public void setclass(Myactivity main){
		this.main = main;
	}
	
	
	protected City doInBackground(String... params) {
		Xpp3 parser = new Xpp3();
		for (String param : params){
			try {
				parser.parserxlm(param,this.maville);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return this.maville;
	}
	
	protected void onPostExecute(City maville) {
		//Log.d("log.appsmeteo.parser",maville.toString());
		//textView.setText(maville.toString());
		int iderror = this.valid(maville);
		if(iderror==0)
			this.main.fixfinish(maville);
		else this.main.fixErr(iderror);
	}
	
	protected int valid(City maville) {
		if(maville.getName()==null) return 1;
		int i;
		for(i=0;i<7;i++)
			if(maville.getPrevision(i) == null) return 2;
		return 0;
	}
	
	
	
	
	public class Xpp3 {
		int etatname = 0;
		int etatcountry = 0;
		int currentday = -1;
		
		public void parserxlm(String input,City maville) throws Exception {
			XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();

			xpp.setInput( new StringReader(input) );
			int eventType= xpp.getEventType();
			while(eventType!= XmlPullParser.END_DOCUMENT) {
				if(eventType == XmlPullParser.START_TAG) {
	                processStartElement(xpp);
	            } else if(eventType == XmlPullParser.END_TAG) {
	                processEndElement(xpp);
	            } else if(eventType == XmlPullParser.TEXT) {
	                processText(xpp);
	            }
				eventType= xpp.next();
			}
		}
		
		public void processStartElement (XmlPullParser xpp)
	    {
	        String name = xpp.getName();
	        if ("name".equals (name)) {
	        	this.etatname = 1;
	        } else if ("country".equals (name)) {
	        	this.etatcountry = 1;
	        } else if ("time".equals (name)) {
	        	this.currentday++;
	        	//Log.d("log.appsmeteo.parser","new day"+this.currentday);
	        	maville.creeJoursPrevision(xpp.getAttributeValue(null,"day"), this.currentday);
	        } else if ("symbol".equals (name)) {
	        	maville.getPrevision(this.currentday).setSymbol(xpp.getAttributeValue(null,"name"));
	        	maville.getPrevision(this.currentday).setSymbolVar(xpp.getAttributeValue(null,"var"));
	        } else if ("precipitation".equals (name)) {
	        	String att = xpp.getAttributeValue(null, "value");
	        	if(att != null)
	        		maville.getPrevision(this.currentday).setPrecipitation(Math.round(Float.parseFloat(att)));
	        	String att2 = xpp.getAttributeValue(null, "type");
	        	if(att2 != null)
	        		maville.getPrevision(this.currentday).setTypeprecipitation(att2); 
	        } else if ("windDirection".equals (name)) {
	        	maville.getPrevision(this.currentday).setWindDirection(xpp.getAttributeValue(null, "code")); 
	        } else if ("windSpeed".equals (name)) {
	        	maville.getPrevision(this.currentday).setWindSpeed(Float.parseFloat(xpp.getAttributeValue(null, "mps"))); 
	        } else if ("temperature".equals (name)) {
	        	maville.getPrevision(this.currentday).setTemperatureDay(Math.round(Float.parseFloat(xpp.getAttributeValue(null, "day"))));
	        	maville.getPrevision(this.currentday).setTemperatureMin(Math.round(Float.parseFloat(xpp.getAttributeValue(null, "min"))));
	        	maville.getPrevision(this.currentday).setTemperatureMax(Math.round(Float.parseFloat(xpp.getAttributeValue(null, "max"))));
	        } else if ("pressure".equals (name)) {
	        	maville.getPrevision(this.currentday).setPressure(Math.round(Float.parseFloat(xpp.getAttributeValue(null, "value"))));
	        } else if ("humidity".equals (name)) {
	        	maville.getPrevision(this.currentday).setHumidity(Math.round(Float.parseFloat(xpp.getAttributeValue(null, "value"))));
	        }
	    }


	    public void processEndElement (XmlPullParser xpp)
	    {
	    	String name = xpp.getName();
	        if ("name".equals (name)) {
	        	this.etatname = 0;
	        } else if ("name".equals (name)) {
	        	this.etatcountry = 0;
	        }
	    }

		private void processText(XmlPullParser xpp) {
			String text = xpp.getText();
			if (this.etatname == 1){
				maville.setName(text);
				this.etatname = 0;
			} else if (this.etatcountry == 1) {
				maville.setPays(text);
				this.etatcountry = 0;
	        }
			
		}


	}

}
