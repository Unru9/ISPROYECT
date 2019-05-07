package Modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import EstructuraDatos.MatrixHashMap;

public class ModeloPersonas {

	private MatrixHashMap modeloPersonas;
	private static ModeloPersonas mModeloPersonas;

	private ModeloPersonas() {
		modeloPersonas = new MatrixHashMap();
	}

	public static ModeloPersonas getModeloPersonas() {
		if (mModeloPersonas == null) {
			mModeloPersonas = new ModeloPersonas();
		}
		return mModeloPersonas;
	}

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
		// recorrer matrix hashmap
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
		System.out.println(res);
		return res;
	}

	public void crearModeloPersonas() {
		MatrizValoraciones mv = MatrizValoraciones.getMatrizValoraciones();
		;
		Iterator<Entry<Integer, ValoracionUsuario>> itrUs = mv.getIterador();
		while (itrUs.hasNext()) {
			Entry<Integer, ValoracionUsuario> entradaUs = itrUs.next();
			int idUsu = entradaUs.getKey();
			ArrayList<Integer> a = pelBienValoradas(idUsu);
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
}
