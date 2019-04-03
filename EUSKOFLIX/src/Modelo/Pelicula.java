package Modelo;

import java.util.ArrayList;
import java.util.HashMap;

public class Pelicula {
	// atributos

	private String title;
	private HashMap<String,Integer> tags;

	// Constructora
	public Pelicula(String pTitle) {
		this.title = pTitle;
		this.tags = new HashMap<String,Integer>();
	}

	// metodo

	public String obtTitle() {
		return this.title;
	}

	public boolean containsObject(Object pAux){
		if(this.tags.containsKey(pAux)){
			return true;
		}
	return false;
	}
	
	public void sumAparicion(String pTag){
		if(this.tags.containsKey(pTag)){
			int valorActual = this.tags.get(pTag);
			this.tags.put(pTag, valorActual + 1);
		}else{
			this.tags.put(pTag, 1);
		}
	}
}
