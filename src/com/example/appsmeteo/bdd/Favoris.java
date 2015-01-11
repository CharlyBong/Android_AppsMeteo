package com.example.appsmeteo.bdd;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

/** Class City:
 * Package : bdd
 * Données sur la ville
 * @author Charles Bongiorno
 */
public class Favoris{
	public int nb_fav;
	List<Ville> listville  = null;
	
	public Favoris(){
		this.nb_fav = 0;
		this.listville = new ArrayList<Ville>();  
	}
	
	public void add(String ville, String pays) {
		Ville city = new Ville(ville,pays); 
		this.listville.add( city );
		this.nb_fav++;
	}
	
	public String getVille(int nb){
		try{
			if(nb<this.nb_fav)
				return this.listville.get( nb ).getVille();
			return null;
		}
		catch (Exception e){
			return "";
		}
	}
	
	public String getPays(int nb){
		try{
			if(nb<this.nb_fav)
				return this.listville.get( nb ).getPays();
			return null;
		}
		catch (Exception e){
			return "";
		}
	}
	
	public String getFav(int nb){
		try{
			String ville = this.getVille(nb);
			String pays = this.getPays(nb);
			if(pays.equals("")) return ville;
			else return ville+", "+pays;
		}
		catch (Exception e){
			return "";
		}		
	}
	
	public int getmaxfav(){
		return this.nb_fav;
	}
	
	public String toString(){
		String retour = "";
		for(int i=0;i<this.nb_fav;i++){
			if(this.listville.get(i).existe())  retour += this.getVille(i)+","+this.getPays(i)+"\r\n";
		}
		return retour;
	}
	
	public Favoris(String input){
		this.listville = new ArrayList<Ville>();
		this.nb_fav = 0;
		String[] items = input.split("\n");
	    for (String item : items)
	    {
	    	this.addline(item);
	    }
	}
	
	public void addline(String item){
		String[] separated = item.split(",");
    	this.add(separated[0], separated[1]);
	}
	
	public void supp(int selectedIndex) {
		try{
			//this.listville.remove( this.listville.get( selectedIndex ) );
			//this.nb_fav--;
			this.listville.get(selectedIndex).supp();
		}
		catch (Exception e){
			Log.w("log.appsmeteo.favoris", "Impossibilité de suppression "+selectedIndex);
		}
	}
	
	
	
	
	public static class Ville{
		private String ville = null;
		private String pays = null;
		private boolean exis = true;
		
		public Ville(String ville,String pays){
			this.ville = ville;
			this.pays = pays;
			this.exis = true;
		}
		
		public String getVille(){
			return this.ville;
		}
		
		public String getPays(){
			return this.pays;
		}
		
		public void supp(){
			this.exis = false;
		}
		
		public boolean existe(){
			return this.exis;
		}
		
	}

}
