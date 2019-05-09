package Modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class MedidasSimilitudProducto {

	// ATRIBUTOS
	private static MedidasSimilitudProducto mMedidasSimilitud;
	private HashMap<Integer, HashMap<Integer, Double>> matrizSimilitudes;

	// CONSTRUCTORA
	private MedidasSimilitudProducto() {
		matrizSimilitudes = new HashMap<Integer, HashMap<Integer, Double>>();
	}

	public static MedidasSimilitudProducto getMedidasSimilitudProducto() {
		if (mMedidasSimilitud == null)
			mMedidasSimilitud = new MedidasSimilitudProducto();
		return mMedidasSimilitud;
	}

	// MÉTODOS
	private double obtSimilitud(int pId1, int pId2) {
		HashMap<Integer, Double> similitudesConId1 = matrizSimilitudes.get(pId1);
		return similitudesConId1.get(pId2);
	}

	public Set<Entry<Integer, HashMap<Integer, Double>>> getEntrySet() {
		return matrizSimilitudes.entrySet();
	}

	public HashMap<Integer, Double> obtSimilitudesPelicula(int pIdPel) {
		return matrizSimilitudes.get(pIdPel);
	}
	// METODOS

	public void crearMatrizSimilitudes() {
		System.out.println("CREANDO MATRIZ SIMILITUDES --------->");

		ColeccionPeliculas cp = ColeccionPeliculas.getColeccionPeliculas();
		Iterator<Entry<Integer, Pelicula>> itrPel1 = cp.getIterator();
		while (itrPel1.hasNext()) {
			Entry<Integer, Pelicula> entradaPel1 = itrPel1.next();
			int idPel1 = entradaPel1.getKey();

			Iterator<Entry<Integer, Pelicula>> itrPel2 = cp.getIterator();
			while (itrPel2.hasNext()) {
				Entry<Integer, Pelicula> entradaPel2 = itrPel2.next();
				int idPel2 = entradaPel2.getKey();

				if (idPel1 != idPel2) {
					// System.out.println("Comparando " + idPel1 + " " +
					// idPel2);
					double similitud = compararPelis(idPel1, idPel2);

					matrizSimilitudesAnadir(idPel1, idPel2, similitud);
				}
			}
		}

		StringBuilder sb = new StringBuilder();
		for (Entry<Integer, HashMap<Integer, Double>> entrada : matrizSimilitudes.entrySet()) {
			// System.out.println(entrada);
			sb.append(entrada.toString() + "\n");
		}

	}

	private double compararPelis(int idPel1, int idPel2) {
		MatrizValoraciones mv = MatrizValoraciones.getMatrizValoraciones();
		Iterator<Entry<Integer, ValoracionUsuario>> itrUsuarios = mv.getIterador();
		ArrayList<Double> v1 = new ArrayList<Double>();
		ArrayList<Double> v2 = new ArrayList<Double>();

		while (itrUsuarios.hasNext()) {
			Entry<Integer, ValoracionUsuario> entradaUsuario = itrUsuarios.next();
			ValoracionUsuario valoracionUsuario = entradaUsuario.getValue();

			Iterator<Entry<Integer, Double>> itrPeliculaValorada = valoracionUsuario.getIterador();
			while (itrPeliculaValorada.hasNext()) {
				Entry<Integer, Double> entradaValoracion = itrPeliculaValorada.next();
				Integer idPeliculaValorada = entradaValoracion.getKey();

				if ((idPeliculaValorada == idPel1) && (valoracionUsuario.obtValoracionPelicula(idPel2) == 0.0)) {
					v1.add(entradaValoracion.getValue());
					v2.add(valoracionUsuario.obtValoracionPelicula(idPel2));
				}

				if (idPeliculaValorada == idPel2) {
					v2.add(entradaValoracion.getValue());
					v1.add(valoracionUsuario.obtValoracionPelicula(idPel1));
				}
			}
		}
		double indice = Math.abs(cosenoVectores(v1, v2));
		// System.out.println(v1);
		// System.out.println(v2);
		// System.out.println("Valor: " + indice);

		return indice;
	}

	private void matrizSimilitudesAnadir(int idPel1, int idPel2, double similitud) {
		if (matrizSimilitudes.containsKey(idPel1)) {
			HashMap<Integer, Double> HM = matrizSimilitudes.get(idPel1);
			HM.put(idPel2, similitud);
			matrizSimilitudes.put(idPel1, HM);
		} else {
			HashMap<Integer, Double> HM = new HashMap<Integer, Double>();
			HM.put(idPel2, similitud);
			matrizSimilitudes.put(idPel1, HM);
		}

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

	public double cosenoVectores(ArrayList<Double> v1, ArrayList<Double> v2) {
		double numerador = multiplicarVectores(v1, v2);
		double denominador = calcularNorma(v1) * calcularNorma(v2);
		return Math.round((numerador / denominador) * 1000.0) / 1000.0;
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

	public ArrayList<Double> rellenarArray(ArrayList<Double> v2, int diferencia) {
		for (int i = 0; i < diferencia; i++) {
			v2.add(0.0);
		}
		return v2;
	}

	public Double similitudEntrePeliculas(int pPelicula1, int pPelicula2) {
		return obtSimilitud(pPelicula1, pPelicula2);
	}

	public Double valoracionEstimada(int pIdPersona, int pIdPelicula) {

		return null;

	}

}
