package Modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

public class ColeccionPeliculas {
	// ATRIBUTOS
	private HashMap<Integer, Pelicula> listaPeliculas;
	private static ColeccionPeliculas miColeccionPeliculas;

	// CONSTRUCTORA
	private ColeccionPeliculas() {
		this.listaPeliculas = new HashMap<Integer, Pelicula>();
	}

	public static ColeccionPeliculas getColeccionPeliculas() {
		if (miColeccionPeliculas == null) {
			miColeccionPeliculas = new ColeccionPeliculas();

		}
		return miColeccionPeliculas;
	}

	// MÉTODOS

	private void anadirPelicula(int pIdPelicula, Pelicula pPelicula) {
		this.listaPeliculas.put(pIdPelicula, pPelicula);
	}

	private Pelicula buscarPelicula(String pPelicula) {
		for (Pelicula pel : listaPeliculas.values()) {
			if (pel.obtTitle() == pPelicula) {
				return pel;
			}
		}
		return null;
	}

	public Pelicula buscarPelicula(int pIdPelicula) {
		return listaPeliculas.get(pIdPelicula);
	}

	public void borrarPeliculas() {
		listaPeliculas.clear();
	}

	public boolean contieneIDPelicula(int pID) {
		if (this.listaPeliculas.containsKey(pID)) {
			return true;
		}
		return false;
	}

	public int numeroPeliculasConTag(String pTag) {
		int res = 0;
		Pelicula pel;
		for (HashMap.Entry<Integer, Pelicula> entry : listaPeliculas.entrySet()) {
			pel = entry.getValue();
			if (pel.contieneTag(pTag)) {
				res = res + 1;
			}
		}
		return res;
	}

	public int numeroPeliculas() {
		return this.listaPeliculas.size();
	}

	public void cargarPeliculas(String pPath) {
		// pPath= "movie-titles.csv";
		try {

			BufferedReader bufferLectura = new BufferedReader(new FileReader(pPath));

			// Leo una línea del archivo
			String linea = bufferLectura.readLine();

			while (linea != null) {
				// Separa la línea leída con el separador definido previamente
				String[] campos = linea.split(";");

				String part1 = campos[0];
				String part2 = campos[1];
				String title = part2.substring(1, part2.length() - 1);
				Pelicula pelicula = new Pelicula(title);
				int idPelicula = Integer.parseInt(part1);
				anadirPelicula(idPelicula, pelicula);

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

	public void cargarTagsDesdeArchivo(String pPath) {
		crearMatrizEtiquetaProductos(pPath);
	}

	public void crearMatrizEtiquetaProductos(String pPath) {

		try {
			System.out.println("CREANDO MATRIZ ETIQUETA PRODUCTOS --------->");
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

	// ITERADORES
	private Iterator<Entry<Integer, Pelicula>> iterator() {
		return listaPeliculas.entrySet().iterator();
	}

	public Iterator<Entry<Integer, Pelicula>> getIterator() {
		return iterator();
	}

	public Set<Entry<Integer, Pelicula>> getEntrySet() {
		return listaPeliculas.entrySet();
	}

	// METODOS Sirven para visualizar en pantalla TODOS los valores de pelis y
	// tags. UTILIZADO SOLO EN SPRINT 1
	public String visPelis() {
		Collection<Pelicula> aux = this.listaPeliculas.values();
		StringBuilder sb = new StringBuilder();
		for (Pelicula pel : aux) {
			sb.append(pel.obtTitle());
			sb.append("\n");
		}
		return sb.toString();
	}

	public String visTags(int pID) {
		Pelicula pelicula = buscarPelicula(pID);
		return pelicula.visTags();
	}
}