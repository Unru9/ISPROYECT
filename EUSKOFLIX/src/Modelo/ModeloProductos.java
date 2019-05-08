package Modelo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import EstructuraDatos.MatrixHashMap;

public class ModeloProductos {

	private  MatrixHashMap matrizModeloProductos;
	private static ModeloProductos mModeloProductos;
	
	private ModeloProductos() {
		matrizModeloProductos = new MatrixHashMap();
	}
	
	public static ModeloProductos getModeloProductos() {
		if (mModeloProductos==null)
			mModeloProductos = new ModeloProductos();
		return mModeloProductos;
	}
	
	
	private double calcularTFIDF(int pIdPelicula, String pTag) {
		ColeccionPeliculas coleccionPeliculas = ColeccionPeliculas.getColeccionPeliculas();
		Pelicula pel = coleccionPeliculas.buscarPelicula(pIdPelicula);
		double res = 0.0;
		int tf = pel.obtAparicionTag(pTag);
		int N = coleccionPeliculas.numeroPeliculas();
		int NT = coleccionPeliculas.numeroPeliculasConTag(pTag);

		res = tf * Math.log(N / NT);
		return res;

	}

	public void crearModeloProducto() {
		ColeccionPeliculas coleccionPeliculas = ColeccionPeliculas.getColeccionPeliculas();
		
		Iterator<Entry<Integer, Pelicula>> iteradorPeliculas = coleccionPeliculas.getIterator();
		while (iteradorPeliculas.hasNext()) {
			Entry<Integer, Pelicula> entradaPelicula = iteradorPeliculas.next();
			int idPelicula = entradaPelicula.getKey();
			Pelicula pelicula = entradaPelicula.getValue();
			
			Iterator<Entry<String, Integer>> iteradorTags = pelicula.getIterator(); 
			while (iteradorTags.hasNext()) {
				Entry<String, Integer> entradaTag = iteradorTags.next();
				String tag = entradaTag.getKey();
				double tFIDF = calcularTFIDF(idPelicula, tag);
				
				matrizModeloProductos.anadirDupla(idPelicula, tag, tFIDF);
			}
		}
	}
	
	public Iterator<Entry<Integer, HashMap<String, Double>>> getIterador() {
		return matrizModeloProductos.iterator();
	}

	public HashMap<String, Double> contenido(int pelicula) {
		return matrizModeloProductos.contenido(pelicula);
	}
		
	}

