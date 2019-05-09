package Modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import EstructuraDatos.MatrixHashMap;

public class ModeloPersonas {

	// ATRIBUTOS
	private MatrixHashMap modeloPersonas;
	private static ModeloPersonas mModeloPersonas;

	// CONSTRUCTORA
	private ModeloPersonas() {
		modeloPersonas = new MatrixHashMap();
	}

	public static ModeloPersonas getModeloPersonas() {
		if (mModeloPersonas == null) {
			mModeloPersonas = new ModeloPersonas();
		}
		return mModeloPersonas;
	}

	// MÉTODOS
	private ArrayList<Integer> pelBienValoradas(int idUsuario) {
		MatrizValoraciones mv = MatrizValoraciones.getMatrizValoraciones();
		ArrayList<Integer> res = new ArrayList<Integer>();
		ValoracionUsuario vu = mv.obtUsuario(idUsuario);
		Iterator<Entry<Integer, Double>> itr = vu.getIterador();
		while (itr.hasNext()) {
			Entry<Integer, Double> entrada = itr.next();
			double valoracion = entrada.getValue();
			int idPel = entrada.getKey();
			if (valoracion >= 3.5) {
				res.add(idPel);
			}
		}
		return res;
	}

	public HashMap<String, Double> tfidfsMejorValoradas(ArrayList<Integer> aux) {
		HashMap<String, Double> res = new HashMap<String, Double>();
		ModeloProductos mp = ModeloProductos.getModeloProductos();
		// RECORRER MATRIX
		Iterator<Entry<Integer, HashMap<String, Double>>> iterador1 = mp.getIterador();
		while (iterador1.hasNext()) {
			Entry<Integer, HashMap<String, Double>> a = iterador1.next();
			Integer idPel = a.getKey();
			HashMap<String, Double> b = a.getValue();
			if (aux.contains(idPel)) {
				Iterator<Entry<String, Double>> iterador2 = b.entrySet().iterator();
				while (iterador2.hasNext()) {
					Entry<String, Double> entrada = iterador2.next();
					String tag = entrada.getKey();
					Double tfidf = entrada.getValue();
					if (res.containsKey(tag)) {
						double valor = res.get(tag) + tfidf;
						res.put(tag, valor);
					} else {
						res.put(tag, tfidf);
					}
				}
			}
		}
		// System.out.println("tfidfs mejor valoradas: " + res);
		return res;
	}

	public void crearModeloPersonas() {
		System.out.println("CREANDO MODELO PERSONAS --------->");
		MatrizValoraciones mv = MatrizValoraciones.getMatrizValoraciones();
		Iterator<Entry<Integer, ValoracionUsuario>> itrUs = mv.getIterador();
		while (itrUs.hasNext()) {
			Entry<Integer, ValoracionUsuario> entradaUs = itrUs.next();
			int idUsu = entradaUs.getKey();
			ArrayList<Integer> a = pelBienValoradas(idUsu);
			// System.out.println("pelBienValoradas: " + a);
			modeloPersonas.anadir(idUsu, tfidfsMejorValoradas(a));
		}
	}

	public String visualizarPeliculasAfinesPersona(int idUser, int nProd) {
		StringBuilder res = new StringBuilder();
		int i = 0;
		res.append("====================================================== \n");
		res.append("LAS PELÍCULAS MÁS AFINES AL USUARIO :" + idUser + "\n");
		res.append("====================================================== \n");
		System.out.println();

		if (modeloPersonas.tieneValores(idUser)) {
			HashMap<String, Double> b = this.modeloPersonas.obtHash(idUser);
			Iterator<Entry<String, Double>> iterador2 = b.entrySet().iterator();
			while (iterador2.hasNext()) {
				Entry<String, Double> entrada = iterador2.next();
				if (i < 10) {
					String pelTitle = entrada.getKey();
					Double pelIdoneidad = entrada.getValue();
					res.append(pelTitle + " --> " + pelIdoneidad + "\n");

				}
				i = i + 1;
			}
		}
		return res.toString();
	}

	public boolean contieneIdUsuario(int pIDusuario) {
		if (this.modeloPersonas.tieneValores(pIDusuario)) {
			return true;
		}
		return false;
	}

	public double gradoIdoneidad(int Usuario, int Pelicula) {
		HashMap<String, Double> vectorRawU = modeloPersonas.contenido(Usuario);
		ArrayList<Double> vectorU = transform(vectorRawU.values());

		ModeloProductos mp = ModeloProductos.getModeloProductos();
		HashMap<String, Double> vectorRawP = mp.contenido(Pelicula);
		ArrayList<Double> vectorP = transform(vectorRawP.values());
		ArrayList<Double> vectorUu = unitario(vectorU);
		ArrayList<Double> vectorPu = unitario(vectorP);
		// System.out.println(vectorUu);
		// System.out.println("-----------------");
		// System.out.println(vectorPu);
		return Math.abs(cosenoVectores(vectorUu, vectorPu));
	}

	private ArrayList<Double> unitario(ArrayList<Double> vector) {
		double modulo = modulo(vector);
		for (int i = 0; i < vector.size(); i++) {
			double nuevo = vector.get(i) / modulo;
			vector.set(i, nuevo);
		}
		return vector;
	}

	private double modulo(ArrayList<Double> vector) {
		double sumatorio = 0.0;
		for (int i = 0; i < vector.size(); i++) {
			double valor = vector.get(i);
			sumatorio = sumatorio + (valor * valor);
		}
		return Math.sqrt(sumatorio);
	}

	private ArrayList<Double> transform(Collection<Double> values) {
		ArrayList<Double> a = new ArrayList<Double>();
		Iterator<Double> itr = values.iterator();
		while (itr.hasNext()) {
			a.add(itr.next());
		}
		return a;
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

}
