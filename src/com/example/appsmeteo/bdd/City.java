package com.example.appsmeteo.bdd;

import android.os.Parcel;
import android.os.Parcelable;

/** Class City:
 * Package : bdd
 * Données sur la ville
 * @author Charles Bongiorno
 */

public class City implements Parcelable  {
	private String name;
	private String pays;
	private PrevisionJours previsionJ0;
	private PrevisionJours previsionJ1;
	private PrevisionJours previsionJ2;
	private PrevisionJours previsionJ3;
	private PrevisionJours previsionJ4;
	private PrevisionJours previsionJ5;
	private PrevisionJours previsionJ6;
	
	
	public City(){
	}
	
	public City(String name){
		this.setName(name);
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString(){
		String ret = "";
		ret = "Ville : "+this.name+" , "+this.pays+"\n";
		ret += this.previsionJ0;
		ret += this.previsionJ1;
		ret += this.previsionJ2;
		ret += this.previsionJ3;
		ret += this.previsionJ4;
		ret += this.previsionJ5;
		ret += this.previsionJ6;		
		return ret;
	}
	
	public PrevisionJours creeJoursPrevision(String date,int day){
		switch(day){
			case 0:
				this.previsionJ0 = new PrevisionJours(date,this.name,this.pays);
				return this.previsionJ0;
			case 1:
				this.previsionJ1 = new PrevisionJours(date,this.name,this.pays);
				return this.previsionJ1;
			case 2:
				this.previsionJ2 = new PrevisionJours(date,this.name,this.pays);
				return this.previsionJ2;
			case 3:
				this.previsionJ3 = new PrevisionJours(date,this.name,this.pays);
				return this.previsionJ3;
			case 4:
				this.previsionJ4 = new PrevisionJours(date,this.name,this.pays);
				return this.previsionJ4;
			case 5:
				this.previsionJ5 = new PrevisionJours(date,this.name,this.pays);
				return this.previsionJ5;
			case 6:
				this.previsionJ6 = new PrevisionJours(date,this.name,this.pays);
				return this.previsionJ6;
			default:
				this.previsionJ0 = new PrevisionJours(date,this.name,this.pays);
				return this.previsionJ0;
		}
	}
	
	public PrevisionJours getPrevision(int day){
		switch(day){
		case 0:
			return this.previsionJ0;
		case 1:
			return this.previsionJ1;
		case 2:
			return this.previsionJ2;
		case 3:
			return this.previsionJ3;
		case 4:
			return this.previsionJ4;
		case 5:
			return this.previsionJ5;
		case 6:
			return this.previsionJ6;
		default:
			return this.previsionJ0;
		}
	}

   public int describeContents() {
       return 0;
   }

   public void writeToParcel(Parcel out, int flags) {
       out.writeString(name);
       out.writeString(pays);
       out.writeParcelable(this.previsionJ0, flags);
       out.writeParcelable(this.previsionJ1, flags);
       out.writeParcelable(this.previsionJ2, flags);
       out.writeParcelable(this.previsionJ3, flags);
       out.writeParcelable(this.previsionJ4, flags);
       out.writeParcelable(this.previsionJ5, flags);
       out.writeParcelable(this.previsionJ6, flags);
   }
   
   public static final Parcelable.Creator<City> CREATOR = new Parcelable.Creator<City>(){
	   @Override
	   public City createFromParcel(Parcel source){
		   return new City(source);
	   }

	   @Override
	   public City[] newArray(int size){
		   return new City[size];
	   }
   };

   public City(Parcel in) {
	   this.name = in.readString();
	   this.pays = in.readString();
	   this.previsionJ0 = in.readParcelable(PrevisionJours.class.getClassLoader());
	   this.previsionJ1 = in.readParcelable(PrevisionJours.class.getClassLoader());
	   this.previsionJ2 = in.readParcelable(PrevisionJours.class.getClassLoader());
	   this.previsionJ3 = in.readParcelable(PrevisionJours.class.getClassLoader());
	   this.previsionJ4 = in.readParcelable(PrevisionJours.class.getClassLoader());
	   this.previsionJ5 = in.readParcelable(PrevisionJours.class.getClassLoader());
	   this.previsionJ6 = in.readParcelable(PrevisionJours.class.getClassLoader());
   }

}
