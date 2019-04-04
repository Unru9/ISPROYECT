package Modelo;

import java.util.HashMap;

public class Usuario {

	// atributos
	private HashMap<Integer, Double> ratings;

	// Contructora
	public Usuario() {
		this.ratings = new HashMap<Integer, Double>();
		//a�adir todas las peliculas con valor 0
	}

	// Metodos

	public void addRatings(int pIdPelicula, double pCalificacion) {
		this.ratings.put(pIdPelicula, pCalificacion);
	}

	/*
	 * public void visRatingUsuario(){ Iterator<Integer> itr=
	 * ratings.keySet().iterator(); while(itr.hasNext()){ Integer key=
	 * itr.next(); System.out.println("Pel�cula : " + key + " --> Puntuaci�n: "+
	 * ratings.get(key)); } }
	 */

	public Double obtValoracion(int pID) {

		return this.ratings.get(pID);
	}
	
	public boolean containsPelicula(int pIdPelicula) {
		if (this.ratings.containsKey(pIdPelicula)) {
			return true;
		}
		return false;
	}
	
	public HashMap<Integer,Double> obtRatings(){
		return this.ratings;
	}
}
