package com.example.appsmeteo.web.api;

/** Class Openweathermaps:
 * Package : web.api
 * Date : 10/01/2014
 * Informations de connection de l'API Web
 * @author Charles Bongiorno
 */

public class Openweathermaps {
	protected String URL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";
	protected String KEY = "cd7f540525f7c1ec526fcf047410cf1a";
	protected String CITY;
	
	
	public Openweathermaps(String city){
		this.CITY = city;
	}
	
	public String getURL(){
		String urlfinal = this.URL+this.CITY+"&mode=xml&APPID="+this.KEY+"&units=metric&lang=fr&cnt=7";
		//String urlfinal = this.URL+this.CITY+"&mode=xml&units=metric&lang=fr&cnt=7";
		//String urlfinal = this.URL+this.CITY+"&mode=xml&APPID="+this.KEY+"&units=metric&lang=fr";
		return urlfinal;
	}
}
