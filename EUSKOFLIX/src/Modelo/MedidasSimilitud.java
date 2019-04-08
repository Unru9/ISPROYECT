package Modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import EstructuraDatos.MatrixHashMap;

public class MedidasSimilitud {
	
	//Atributos
	private static MedidasSimilitud mMedidasSimilitud;
	private  MatrixHashMap matrizModeloProductos;
	private MatrixHashMap matrizModeloPersonas;
	private HashMap<Integer, HashMap<Integer, Double>> matrizSimilitudes;
	
	//Atributos para la similitud
	
	//CONSTRUCTORA
	private MedidasSimilitud() {
		matrizModeloProductos = new MatrixHashMap(); 
		matrizModeloPersonas = new MatrixHashMap();
		matrizSimilitudes = new HashMap<Integer, HashMap<Integer, Double>>();
	}
	
	public static MedidasSimilitud getMedidasSimilitud() {
		if (mMedidasSimilitud==null)
			mMedidasSimilitud = new MedidasSimilitud();
		return mMedidasSimilitud;
	}
	
	//METODOS PARA MANEJAR LA MATRIZ SIMILITUD
	private double obtSimilitud(int pId1, int pId2) {
		HashMap<Integer, Double> similitudesConId1 = matrizSimilitudes.get(pId1);
		return similitudesConId1.get(pId2);
	}
	//METODOS
	public void cargarTagsDesdeArchivo(String pPath) {
		crearMatrizEtiquetaProductos(pPath);
	}
	
	public void crearMatrizEtiquetaProductos(String pPath) {
		
		try {
			ColeccionPeliculas cp = ColeccionPeliculas.getColeccionPeliculas();
			BufferedReader bufferLectura = new BufferedReader(new FileReader(pPath));

			// Leo una línea del archivo
			String linea = bufferLectura.readLine();

			while (linea != null) {
				// Separa la línea leída con el separador definido previamente
				String[] campos = linea.split(";");

				String pelicula = campos[0];
				String tag = campos[1];

				int idPelicula = Integer.parseInt(pelicula);
				if (cp.contieneIDPelicula(idPelicula)) {
					Pelicula aux = cp.buscarPelicula(idPelicula);
					aux.anadirAparicionTag(tag);

				}

				// Vuelvo a leer del fichero
				linea = bufferLectura.readLine();
			}

			// CIerro el buffer de lectura
			if (bufferLectura != null) {
				bufferLectura.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	


	public void crearMatrizSimilitudes(){
		
		HashMap<Integer,ArrayList<Double>> vectores = crearVectoresPorIdPel();
		
		for (Entry<Integer, ArrayList<Double>> entrada : vectores.entrySet()) {
			ArrayList<Double> v1 =  entrada.getValue();
			int idPel1 = entrada.getKey();
			
			for (Entry<Integer, ArrayList<Double>> entrada2 : vectores.entrySet()){
				ArrayList<Double> v2 = entrada2.getValue();
				int idPel2 = entrada2.getKey();
				
				double similitud = Math.round(compararVectores(v1, v2) * 1000.0)/ 1000.0;
				
				matrizSimilitudesAnadir(idPel1, idPel2, similitud);
			
			}
			ordenarHashMap(matrizSimilitudes.get(idPel1));
		}
		
		StringBuilder sb = new StringBuilder();
		for (Entry<Integer, HashMap<Integer, Double>> entrada : matrizSimilitudes.entrySet()) {
			System.out.println(entrada);
			sb.append(entrada.toString() + "\n");
		}
		
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter("C:/Users/ignac/Desktop/matrizSimilitudes.txt"));
			bw.write(sb.toString());
			bw.close();
		} catch (IOException e) {
			System.out.println("Error al escribir los resultados");
			e.printStackTrace();
		}
		
			
	}

	

	private void ordenarHashMap(HashMap<Integer, Double> hashMap) {
		// TODO Auto-generated method stub
		
	}

	private void matrizSimilitudesAnadir(int idPel1, int idPel2, double similitud) {
		if (matrizSimilitudes.containsKey(idPel1)){
			HashMap<Integer, Double> HM = matrizSimilitudes.get(idPel1);
			HM.put(idPel2, similitud);
			matrizSimilitudes.put(idPel1, HM);
		}else{
			HashMap<Integer, Double> HM = new HashMap<Integer, Double>();
			HM.put(idPel2, similitud);
			matrizSimilitudes.put(idPel1, HM);
		}
		
		
	}

	private HashMap<Integer,ArrayList<Double>> crearVectoresPorIdPel(){
		
		HashMap<Integer,ArrayList<Double>> vectores = new HashMap<Integer,ArrayList<Double>>();
		ColeccionUsuario coleccionUsuarios = ColeccionUsuario.getColeccionUsuario();
		
		Iterator<Entry<Integer, Usuario>> itrUsuarios = coleccionUsuarios.getIterador();
		while (itrUsuarios.hasNext()) {
			Entry<Integer, Usuario> entradaUsuario = itrUsuarios.next();
			Usuario usuario = entradaUsuario.getValue();
			
			Iterator<Entry<Integer, Double>> itrPeliculaValorada = usuario.getIterador();
			while (itrPeliculaValorada.hasNext()){
				Entry<Integer, Double> entradaValoracion = itrPeliculaValorada.next();
				Integer idPeliculaValorada = entradaValoracion.getKey();
				double valoracion = entradaValoracion.getValue();
				if (vectores.containsKey(idPeliculaValorada)){
					ArrayList<Double> val = vectores.get(idPeliculaValorada);
					val.add(valoracion);
					vectores.put(idPeliculaValorada, val);
				} else {
					ArrayList<Double> val = new ArrayList<Double>();
					val.add(valoracion);
					vectores.put(idPeliculaValorada, val);
				}
			}
		}
		
		return vectores;
	}

	public double compararVectores(ArrayList<Double> v1, ArrayList<Double> v2){
		int diferencia = Math.abs(v1.size() - v2.size());
		
		if (v1.size() > v2.size()){
			v2 = rellenarArray(v2, diferencia);
		}
		
		if (v2.size() > v1.size()){
			v1 = rellenarArray(v1, diferencia);
		}
		
		double coseno = cosenoVectores(v1, v2);
		
		return Math.abs(coseno);
	}

	private double cosenoVectores(ArrayList<Double> v1, ArrayList<Double> v2) {
		double numerador = multiplicarVectores(v1, v2);
		double denominador = calcularNorma(v1) * calcularNorma(v2);
		return numerador / denominador;
	}

	private double calcularNorma(ArrayList<Double> v1) {
		double result = 0;
		for (int i = 0; i < v1.size(); i++) {
			double cuadrado = v1.get(i) * v1.get(i);
			result = result + cuadrado;
		}
		result = Math.sqrt(result);
		return result;
	}

	private double multiplicarVectores(ArrayList<Double> v1, ArrayList<Double> v2) {
		double result = 0;
		for (int i = 0; i < v2.size(); i++) {
			double multiplicacion = v1.get(i) * v2.get(i);
			result = result + multiplicacion;
		}
		return result;
	}

	private ArrayList<Double> rellenarArray(ArrayList<Double> v2, int diferencia) {
		for (int i = 0; i < diferencia; i++) {
			v2.add(0.0);
		}
		return v2;
	}
	
	public ArrayList<Integer> crearListaPeliculasMasSimilaresA (int pIdPersona, int pIdPelicula, int pNumeroSimilitudes){
		ArrayList<Integer> lista = new ArrayList<Integer>();
		
		// CREAR LISTA DE PELICULAS MAS SIMILARES A PELICULA SELECCIONADA 
		
		//APLICAR FORMULA, PUNTO 2.2.2, Pag 6, Determinar el Grado de idoneidad, punto 2
		
		return lista;
	}
	
	public Double similitudEntrePeliculas(int pPelicula1, int pPelicula2) {
		return obtSimilitud(pPelicula1, pPelicula2);
	}
	
	public Double valoracionEstimada (int pIdPersona, int pIdPelicula){

		return null;
	}

//METODOS PARA VISUALIZAR RESULTADOS
		
	public void visualizarModeloProducto() {
		Double valorActual = matrizModeloProductos.recorrerMatriz();
		System.out.println();
		while (matrizModeloProductos.tieneSiguienteValor()) {
			valorActual = matrizModeloProductos.siguienteValor();
			System.out.println(matrizModeloProductos.getKey1Actual() + " " + matrizModeloProductos.getKey2Actual() + " " + valorActual);
		}
	}
	
	
		
	
	

}
	
	

