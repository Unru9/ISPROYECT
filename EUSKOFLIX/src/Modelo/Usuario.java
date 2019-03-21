package Modelo;

import java.util.HashMap;

public class Usuario {

	// atributos
	private HashMap<Integer, Double> ratings;

	// Contructora
	public Usuario() {
		this.ratings = new HashMap<Integer, Double>();
	}

	// Metodos

	public void addRatings(int pIdPelicula, double pCalificacion) {
		this.ratings.put(pIdPelicula, pCalificacion);
	}

	/*
	 * public void visRatingUsuario(){ Iterator<Integer> itr=
	 * ratings.keySet().iterator(); while(itr.hasNext()){ Integer key=
	 * itr.next(); System.out.println("Película : " + key + " --> Puntuación: "+
	 * ratings.get(key)); } }
	 */

	public Double obtValoracion(int pID) {

		return this.ratings.get(pID);
	}
}
