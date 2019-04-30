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
		if(this.ratings.get(pID)==null) {
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
	
	// METODOS NORMALIZACION
	public double calcularMediaValoraciones(){
		Iterator<Entry<Integer, Double>> itr = iterator();
		int i = 0;
		double total = 0;
		while (itr.hasNext()){
			i++;
			Entry<Integer, Double> valoracion = itr.next();
			Double valoracionValor = valoracion.getValue();
			total = total + valoracionValor;
		}
		
		return total/i;
	}
	
	public double calcularDesviacionTipica(double media){
		Iterator<Entry<Integer, Double>> itr = iterator();
		double total = 0;
		while (itr.hasNext()){
			Entry<Integer, Double> valoracion = itr.next();
			double valor = Math.abs(valoracion.getValue() - media);
			total = total + valor*valor;
		}
		return total;
	}
	
	public double calcularDesviacionTipica(){
		Iterator<Entry<Integer, Double>> itr = iterator();
		double total = 0;
		while (itr.hasNext()){
			Entry<Integer, Double> valoracion = itr.next();
			double valor = Math.abs(valoracion.getValue() - calcularMediaValoraciones());
			total = total + valor*valor;
		}
		return total;
	}
	
	public double calcularZscore(double media, double desv, double valor){
	
		return (valor - media) / desv;
	}
	
	public double calcularZscore(double valor){
		
		return (valor - calcularMediaValoraciones()) / calcularDesviacionTipica();
	}
}
