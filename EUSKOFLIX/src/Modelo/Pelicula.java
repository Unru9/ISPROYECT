package Modelo;

import java.util.ArrayList;

public class Pelicula {
	// atributos

	private String title;
	private ArrayList<String> tags;

	// Constructora
	public Pelicula(String pTitle) {
		this.title = pTitle;
		this.tags = new ArrayList<String>();
	}

	// metodo
	public void addTag(String pTag) {
		this.tags.add(pTag);
	}

	public String obtTitle() {
		return this.title;
	}

	public ArrayList<String> obtTags() {
		return this.tags;
	}

}
