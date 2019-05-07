package Modelo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class Pelicula {

	// ATRIBUTOS
	private String title;
	private HashMap<String, Integer> listaTags;

	// CONSTRUCTORA
	public Pelicula(String pTitle) {
		this.title = pTitle;
		this.listaTags = new HashMap<String, Integer>();
	}

	// METODOS

	public String obtTitle() {
		return this.title;
	}

	public boolean contieneTag(String pTag) {
		if (this.listaTags.containsKey(pTag)) {
			return true;
		} else {
			return false;
		}
	}

	public void anadirAparicionTag(String pTag) {
		if (contieneTag(pTag)) {
			int valorActual = this.listaTags.get(pTag);
			this.listaTags.put(pTag, valorActual + 1);
		} else {
			this.listaTags.put(pTag, 1);
		}
	}

	public int obtAparicionTag(String pTag) {
		return this.listaTags.get(pTag);
	}

	public int cantidadTags() {
		return listaTags.size();
	}

	// ITERADOR

	public Iterator<Entry<String, Integer>> getIterator() {
		return listaTags.entrySet().iterator();
	}

	// //METODOS Sirven para visualizar en pantalla TODOS los valores de pelis y
	// tags. UTILIZADO SOLO EN SPRINT 1
	public String visTags() {

		StringBuilder sb = new StringBuilder();
		for (Entry<String, Integer> entrada : listaTags.entrySet()) {
			sb.append(entrada.getKey());
			sb.append("\n");
		}
		return sb.toString();
	}

}
