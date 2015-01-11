package com.example.appsmeteo.bdd;

import android.os.Parcel;
import android.os.Parcelable;

/** Class PrevisionJours:
 * Package : bdd
 * Données, prévision d'une jour pour une ville
 * @author Charles Bongiorno
 */
public class PrevisionJours implements Parcelable {
	private String ville;
	private String pays;
	private String date;
	
	private String symbol = null;
	private String symbolVar = null;
	private int precipitation = 0;
	private String typeprecipitation = null;
	private String windDirection = null;
	private float windSpeed = 0;
	private int temperatureDay = 0;
	private int temperatureMin = 0;
	private int temperatureMax = 0;
	private int pressure = 0;
	private int humidity = 0;
	
	public PrevisionJours(String date,String ville,String pays){
		this.setDate(date);
		this.setVille(ville);
		this.setPays(pays);
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}

	public String getTypeprecipitation() {
		return typeprecipitation;
	}

	public void setTypeprecipitation(String typeprecipitation) {
		this.typeprecipitation = typeprecipitation;
	}

	public int getPrecipitation() {
		return precipitation;
	}

	public void setPrecipitation(int precipitation) {
		this.precipitation = precipitation;
	}

	public float getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(float windSpeed) {
		this.windSpeed = windSpeed;
	}

	public int getTemperatureMin() {
		return temperatureMin;
	}

	public void setTemperatureMin(int temperatureMin) {
		this.temperatureMin = temperatureMin;
	}

	public int getTemperatureMax() {
		return temperatureMax;
	}

	public void setTemperatureMax(int temperatureMax) {
		this.temperatureMax = temperatureMax;
	}

	public int getPressure() {
		return pressure;
	}

	public void setPressure(int pressure) {
		this.pressure = pressure;
	}

	public int getHumidity() {
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}
	
	public String getDate() {
		return date;
	}

	private void setDate(String date) {
		this.date = date;
	}
	
	public String toString(){
		String retour = null;
		retour = "Le "+this.getDate()+"\n";
		retour += "\t"+"Condition : " + this.getSymbol()+"\n";
		retour += "\t"+"Precipitation : " + this.getPrecipitation() + " de " + this.getTypeprecipitation() + "\n";
		retour += "\t"+"Vent : "+ this.getWindSpeed() +" km/h Direction : "+ this.getWindDirection() + "\n";
		retour += "\t"+"Pression : " + this.getPressure()+" hPa\n";
		retour += "\t"+"Humidity : " + this.getHumidity()+" %\n";
		
		return retour;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public String getSymbolVar() {
		return symbolVar;
	}

	public void setSymbolVar(String symbolVar) {
		this.symbolVar = symbolVar;
	}

	public int getTemperatureDay() {
		return temperatureDay;
	}

	public void setTemperatureDay(int temperatureDay) {
		this.temperatureDay = temperatureDay;
	}
	
	/*
	 * Parcelable
	 */
	
   public int describeContents() {
       return 0;
   }

   public void writeToParcel(Parcel out, int flags) {
       out.writeString(ville);
       out.writeString(pays);
       out.writeString(date);
       out.writeString(symbol);
       out.writeString(symbolVar);
       out.writeInt(precipitation);
       out.writeString(typeprecipitation);
       out.writeString(windDirection);
       out.writeFloat(windSpeed);
       out.writeInt(temperatureDay);
       out.writeInt(temperatureMin);
       out.writeInt(temperatureMax);
       out.writeInt(pressure);
       out.writeInt(humidity);
   }
   public static final Parcelable.Creator<PrevisionJours> CREATOR = new Parcelable.Creator<PrevisionJours>(){
	   @Override
	   public PrevisionJours createFromParcel(Parcel source)
	   {
		   return new PrevisionJours(source);
	   }

	   @Override
	   public PrevisionJours[] newArray(int size)
	   {
		   return new PrevisionJours[size];
	   }
   };

   public PrevisionJours(Parcel in) {
	   this.ville = in.readString();
	   this.pays = in.readString();
	   this.date = in.readString();
	   this.symbol = in.readString();
	   this.symbolVar = in.readString();
	   this.precipitation = in.readInt();
	   this.typeprecipitation = in.readString();
	   this.windDirection = in.readString();
	   this.windSpeed = in.readFloat();
	   this.temperatureDay = in.readInt();
	   this.temperatureMin = in.readInt();
	   this.temperatureMax = in.readInt();
	   this.pressure = in.readInt();
	   this.humidity = in.readInt();
   }

}
