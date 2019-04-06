package Modelo;

import java.io.ObjectInputStream.GetField;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class Usuario {

	//ATRIBUTOS
	private HashMap<Integer, Double> ratings;

	// Contructora
	public Usuario() {
		this.ratings = new HashMap<Integer, Double>();
	}

	// Metodos

	public void anadirRating(int pIdPelicula, double pCalificacion) {
		this.ratings.put(pIdPelicula, pCalificacion);
	}



	public Double obtValoracionPelicula(int pID) {
		return this.ratings.get(pID);
	}
	
	public boolean containsPelicula(int pIdPelicula) {
		if (this.ratings.containsKey(pIdPelicula)) {
			return true;
		}
		return false;
	}
	
	//METODOS PARA ITERAR
	private Iterator<Entry<Integer, Double>> iterator() {
		return ratings.entrySet().iterator();
	}
	
	public Iterator<Entry<Integer, Double>> getIterador() {
		return iterator();
	}
	
	
	/*
	 * public void visRatingUsuario(){ Iterator<Integer> itr=
	 * ratings.keySet().iterator(); while(itr.hasNext()){ Integer key=
	 * itr.next(); System.out.println("Película : " + key + " --> Puntuación: "+
	 * ratings.get(key)); } }
	 */
}
