package Modelo;

import java.io.BufferedReader;
import java.io.FileReader;
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
import java.util.SortedMap;
import java.util.TreeMap;


public class ColeccionUsuario {

	// atributos
	private static ColeccionUsuario miColeccionUsuario;
	private HashMap<Integer, Usuario> lista;

	// Constructora
	private ColeccionUsuario() {
		this.lista = new HashMap<Integer, Usuario>();
	}

	// estático
	public static ColeccionUsuario getColeccionUsuario() {
		if (miColeccionUsuario == null) {
			miColeccionUsuario = new ColeccionUsuario();

		}
		return miColeccionUsuario;
	}

	// métodos

	public void cargarUsuarios(String pPath) {
		try {

			BufferedReader bufferLectura = new BufferedReader(new FileReader(pPath));

			// Leo una línea del archivo
			String linea = bufferLectura.readLine();

			while (linea != null) {
				// Separa la línea leída con el separador definido previamente
				String[] campos = linea.split(",");

				String part1 = campos[0];
				String part2 = campos[1];
				String part3 = campos[2];

				int idUsuario = Integer.parseInt(part1);
				if (!this.lista.containsKey(idUsuario)) {
					Usuario aux = new Usuario();
					this.lista.put(idUsuario, aux);
				}

				Usuario aux = this.lista.get(idUsuario);
				aux.addRatings(Integer.parseInt(part2), Double.parseDouble(part3));

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

	public String visRatingUsuario(int pIdPelicula) {

		StringBuilder sb = new StringBuilder();
		sb.append("=====================================================\n");
		sb.append("Valoraciones de la pelicula  con ID: " + pIdPelicula + "\n");
		sb.append("=====================================================\n");
		sb.append("\n");
		sb.append("\n");
		for (Entry<Integer, Usuario> e : this.lista.entrySet()) {
			int IDUsuario = e.getKey();
			Usuario UsuarioValorador = e.getValue();
			sb.append("Valoración del Usuario: " + IDUsuario);
			sb.append("\n");
			if (UsuarioValorador.containsPelicula(pIdPelicula)) {
				sb.append(UsuarioValorador.obtValoracion(pIdPelicula));
				sb.append("\n");
				sb.append("\n");
			} else {
				sb.append("El usuario todavia no ha valorado la pelicula " + pIdPelicula + ".");
				sb.append("\n");
				sb.append("\n");
			}
		}

		return sb.toString();
	}

	public void borrarUsuarios() {
		lista.clear();
	}

	public boolean containsKey(int piD) {
		if (this.lista.containsKey(piD)) {
			return true;
		}
		return false;
	}

	/*
	 * public HashMap<Integer,HashMap<String,double>> crearModeloPersonas(){
	 * 
	 * double resultadoFinal; ColeccionPeliculas cp=
	 * ColeccionPeliculas.getColeccionPeliculas(); HashMap<Integer,
	 * HashMap<String, Double>> aux= cp.modeloProductos();
	 * 
	 * for (Entry<Integer, Usuario> entrada : lista.entrySet()) { int idUsuario=
	 * entrada.getKey(); Usuario usuario = lista.get(idUsuario);
	 * HashMap<Integer, Double> listaRatingsUsuario = usuario.getRatings();
	 * ArrayList<Integer> LisValoraciones = new ArrayList<Integer>(); for
	 * (Entry<Integer, Double> entrada2 : listaRatingsUsuario.entrySet()) {
	 * double valoracion= entrada2.getValue(); int idPelicula=
	 * entrada2.getKey(); if(valoracion >=3.5){ LisValoraciones.add(idPelicula);
	 * } } for(int i=0;i<LisValoraciones.size();i++){ resultadoFinal=0.0;
	 * HashMap<String, Double> hm= aux.get(i); for (Entry<String, Double>
	 * entrada3 : hm.entrySet()) { String tag= entrada3.getKey();
	 * if(hm.containsKey(tag)){ double tfidf= hm.get(tag); }
	 * 
	 * }
	 * 
	 * } }
	 */

	public HashMap<Integer, HashMap<Integer, Double>> MatrizSimilitudes() {

		HashMap<Integer, ArrayList<Double>> vectores = crearVectoresPorIdPel();

		HashMap<Integer, HashMap<Integer, Double>> modeloP = new HashMap<Integer, HashMap<Integer, Double>>();

		for (Entry<Integer, ArrayList<Double>> entrada : vectores.entrySet()) {
			ArrayList<Double> v1 = entrada.getValue();
			int idPel1 = entrada.getKey();
			for (Entry<Integer, ArrayList<Double>> entrada2 : vectores.entrySet()) {
				ArrayList<Double> v2 = entrada2.getValue();
				int idPel2 = entrada2.getKey();
				HashMap<Integer, Double> a = new HashMap<Integer, Double>();

				a.put(idPel2, compararVectores(v1, v2));
				modeloP.put(idPel1, a);

			}
		}

		return modeloP;
	}

	public HashMap<Integer, ArrayList<Double>> crearVectoresPorIdPel() {

		HashMap<Integer, ArrayList<Double>> vectores = new HashMap<Integer, ArrayList<Double>>();

		for (Entry<Integer, Usuario> entrada : lista.entrySet()) {
			HashMap<Integer, Double> ratings = entrada.getValue().obtRatings();
			for (Entry<Integer, Double> entrada2 : ratings.entrySet()) {

				int idPel = entrada2.getKey();
				double valoracion = entrada2.getValue();

				if (vectores.containsKey(idPel)) {
					ArrayList<Double> val = vectores.get(idPel);
					val.add(valoracion);
					vectores.put(idPel, val);
				} else {
					ArrayList<Double> val = new ArrayList<Double>();
					val.add(valoracion);
					vectores.put(idPel, val);
				}
			}
		}
		return vectores;
	}

	public double compararVectores(ArrayList<Double> v1, ArrayList<Double> v2) {
		int diferencia = Math.abs(v1.size() - v2.size());

		if (v1.size() > v2.size()) {
			v2 = rellenarArray(v2, diferencia);
		}

		if (v2.size() > v1.size()) {
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
	
	public void visualizar(HashMap<Integer, HashMap<Integer, Double>>a){
		for (Entry<Integer, HashMap<Integer, Double>> entrada : a.entrySet()) {
			int idPel1 = entrada.getKey();
			HashMap<Integer, Double> sm= a.get(idPel1);
			for (Entry<Integer, Double> entrada2 : sm.entrySet()) {
				int pel = entrada2.getKey();
				double cos = entrada2.getValue();
				System.out.println("El producto " + idPel1 + " sobre la : " + pel +" tiene un coseno de " + cos);
			}
			
			
		}
		
	}
	
	public HashMap<Integer, HashMap<Integer, Double>> MatrizSimilitudesOrdenada(){
		HashMap<Integer, HashMap<Integer, Double>> ms = MatrizSimilitudes();
		for (Entry<Integer, HashMap<Integer, Double>> entrada : ms.entrySet()){
			int idPel1 = entrada.getKey();
			HashMap<Integer, Double> res = ms.get(idPel1);
			HashMap<Integer, Double> sortedMapAsc = sortByComparator(res, false);
			ms.put(idPel1, sortedMapAsc);
	
		}
		this.visualizar(ms);
		return ms;
	}
	
    public HashMap<Integer, Double> sortByComparator(Map<Integer, Double> unsortMap, final boolean order)
    {

        List<Entry<Integer, Double>> list = new LinkedList<Entry<Integer, Double>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<Integer, Double>>()
        {
            public int compare(Entry<Integer, Double> o1,
                    Entry<Integer, Double> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });
        HashMap<Integer, Double> sortedMap = new LinkedHashMap<Integer, Double>();
        for (Entry<Integer, Double> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
}
