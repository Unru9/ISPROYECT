package Modelo;

import java.util.ArrayList;

public class Pelicula {
	//atributos
	
	private int movieId;
	private String title;
	private ArrayList<String> tags;
	
	//Constructora
	public Pelicula(int pMovieId, String pTitle){
		this.movieId=pMovieId;
		this.title=pTitle;
		this.tags= new ArrayList<String>();
	}
	
	//metodo
	public void addTag(String pTag){
		this.tags.add(pTag);
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public int getMovieId(){
		return this.movieId;
	}
	
	public ArrayList<String> getTags(){
		return this.tags;
	}
	
	public boolean containsObject(String pObject){
		if (this.tags.contains(pObject)){
			return true;
		}
		return false;
	}
	
}
