package Modelo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class ValoracionUsuario {

	// ATRIBUTOS
	private HashMap<Integer, Double> ratings;

	// CONSTRUCTORA
	public ValoracionUsuario() {
		this.ratings = new HashMap<Integer, Double>();
	}

	// MÉTODOS

	public void anadirRating(int pIdPelicula, double pCalificacion) {
		this.ratings.put(pIdPelicula, pCalificacion);
	}

	public Double obtValoracionPelicula(int pID) {
		if (this.ratings.get(pID) == null) {
			return 0.0;
		}
		return this.ratings.get(pID);
	}

	public boolean containsPelicula(int pIdPelicula) {
		if (this.ratings.containsKey(pIdPelicula)) {
			return true;
		}
		return false;
	}

	// METODOS PARA ITERAR
	public Iterator<Entry<Integer, Double>> getIterador() {
		return ratings.entrySet().iterator();
	}
}
