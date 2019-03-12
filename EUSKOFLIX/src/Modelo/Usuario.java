package Modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Usuario {
	
	//atributos
	private HashMap<Integer,Double> ratings;
	
	//Contructora
	public Usuario (){
		this.ratings=new HashMap<Integer,Double>();
	}

	//Metodos
	
	public void addRatings(int pIdPelicula, double pCalificacion){
		this.ratings.put(pIdPelicula, pCalificacion);
	}
	
	public void visUsuario(){
		Iterator<Integer> itr= ratings.keySet().iterator();
		while(itr.hasNext()){
			Integer key= itr.next();
			System.out.println("Pel�cula : " + key + " --> Puntuaci�n: "+ ratings.get(key));
		}
	}
}
