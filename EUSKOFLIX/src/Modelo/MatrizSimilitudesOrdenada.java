package Modelo;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class MatrizSimilitudesOrdenada {

	private HashMap<Integer, HashMap<Integer, Double>> matrizSimilitudesOrdenada;
	private static MatrizSimilitudesOrdenada mMatrizSimilitudesOrdenada;

	private MatrizSimilitudesOrdenada() {
		matrizSimilitudesOrdenada = new HashMap<Integer, HashMap<Integer, Double>>();
	}

	public static MatrizSimilitudesOrdenada getMatrizSimilitudesOrdenada() {
		if (mMatrizSimilitudesOrdenada == null)
			mMatrizSimilitudesOrdenada = new MatrizSimilitudesOrdenada();
		return mMatrizSimilitudesOrdenada;
	}

	public void GenerarMatrizSimilitudesOrdenada() throws Exception {
		MedidasSimilitud ms = MedidasSimilitud.getMedidasSimilitud();

		for (Entry<Integer, HashMap<Integer, Double>> entrada : ms.getEntrySet()) {
			int idPel1 = entrada.getKey();
			HashMap<Integer, Double> res = ms.obtSimilitudesPelicula(idPel1);
			HashMap<Integer, Double> sortedMapAsc = sortByComparator(res, false);
			matrizSimilitudesOrdenada.put(idPel1, sortedMapAsc);

		}

		/*
		 * StringBuilder sb = new StringBuilder(); for (Entry<Integer,
		 * HashMap<Integer, Double>> entrada :
		 * matrizSimilitudesOrdenada.entrySet()) { System.out.println(entrada);
		 * sb.append(entrada.toString() + "\n"); }
		 * 
		 * String output ="C:/Users/Unai/Desktop/matrizSimilitudesOrdenada.txt";
		 * writeFile(output,sb.toString());
		 */

	}

	public String modeloSimilitudProductoPelicula(int movieID) {
		StringBuilder sb = new StringBuilder();
		sb.append("============================================= \n");
		sb.append("ID PELÍCULA                COSENO \n");
		sb.append("============================================= \n");
		HashMap<Integer, Double> aux = matrizSimilitudesOrdenada.get(movieID);
		for (Entry<Integer, Double> entrada : aux.entrySet()) {
			int idPel1 = entrada.getKey();
			double cos = entrada.getValue();
			sb.append(idPel1 + " --------------> " + cos + "\n");
		}

		return sb.toString();
	}

	public HashMap<Integer, Double> sortByComparator(Map<Integer, Double> unsortMap, final boolean order) {

		List<Entry<Integer, Double>> list = new LinkedList<Entry<Integer, Double>>(unsortMap.entrySet());

		// Sorting the list based on values
		Collections.sort(list, new Comparator<Entry<Integer, Double>>() {
			public int compare(Entry<Integer, Double> o1, Entry<Integer, Double> o2) {
				if (order) {
					return o1.getValue().compareTo(o2.getValue());
				} else {
					return o2.getValue().compareTo(o1.getValue());

				}
			}
		});
		HashMap<Integer, Double> sortedMap = new LinkedHashMap<Integer, Double>();
		for (Entry<Integer, Double> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}

	public HashMap<String, Double> sortByComparator2(Map<String, Double> unsortMap, final boolean order) {

		List<Entry<String, Double>> list = new LinkedList<Entry<String, Double>>(unsortMap.entrySet());

		// Sorting the list based on values
		Collections.sort(list, new Comparator<Entry<String, Double>>() {
			public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
				if (order) {
					return o1.getValue().compareTo(o2.getValue());
				} else {
					return o2.getValue().compareTo(o1.getValue());

				}
			}
		});
		HashMap<String, Double> sortedMap = new LinkedHashMap<String, Double>();
		for (Entry<String, Double> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}

	public double gradoIdoneidad(int idUser, int idPel, int nProd) {
		HashMap<Integer, Double> similares = matrizSimilitudesOrdenada.get(idPel);
		double numerador = 0;
		double denominador = 0;
		MatrizValoraciones cu = MatrizValoraciones.getMatrizValoraciones();
		Set<Entry<Integer, Double>> entradas = similares.entrySet();
		Iterator<Entry<Integer, Double>> iteratorSimilares = entradas.iterator();
		for (int j = 0; j < nProd; j++) { // Suponemos que nProd <
											// entradas.size()
			Entry<Integer, Double> entrada = iteratorSimilares.next();
			double similitud = entrada.getValue();
			int idPel2 = entrada.getKey();
			numerador = numerador + (cu.obtValoracion(idUser, idPel2) * similitud);
			denominador = denominador + similitud;
		}

		return numerador / denominador;
	}

	public HashMap<String, Double> peliculasAfines(int idUser, int nProd) {
		ColeccionPeliculas cp = ColeccionPeliculas.getColeccionPeliculas();
		HashMap<String, Double> hm = new HashMap<String, Double>();
		double idoneidad = 0.0;
		for (Entry<Integer, Pelicula> entrada : cp.getEntrySet()) {
			Pelicula pel = entrada.getValue();
			int idPel = entrada.getKey();
			idoneidad = this.gradoIdoneidad(idUser, idPel, nProd);
			hm.put(pel.obtTitle(), idoneidad);
		}
		return (sortByComparator2(hm, false));
	}

	public String visualizarPeliculasAfinesProducto(int idUser, int nProd) {
		StringBuilder res = new StringBuilder();
		int i = 0;
		res.append("====================================================== \n");
		res.append("LAS PELÍCULAS MÁS AFINES AL USUARIO :" + idUser + "\n");
		res.append("====================================================== \n");
		HashMap<String, Double> afinpel = peliculasAfines(idUser, nProd);

		System.out.println();
		for (Entry<String, Double> entrada : afinpel.entrySet()) {
			if (i < 10) {
				String pelTitle = entrada.getKey();
				Double pelIdoneidad = entrada.getValue();
				res.append(pelTitle + " --> " + pelIdoneidad + "\n");

			}
			i = i + 1;
		}
		return res.toString();
	}
}
