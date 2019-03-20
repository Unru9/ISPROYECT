package Modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ColeccionPeliculas {
	// atributos
	private HashMap<Integer, Pelicula> lista;
	private static ColeccionPeliculas miColeccionPeliculas;

	// constructora
	private ColeccionPeliculas() {
		this.lista = new HashMap<Integer, Pelicula>();
	}

	// estático
	public static ColeccionPeliculas getColeccionPeliculas() {
		if (miColeccionPeliculas == null) {
			miColeccionPeliculas = new ColeccionPeliculas();

		}
		return miColeccionPeliculas;
	}

	// Métodos

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
				Pelicula aux = new Pelicula(title);
				this.lista.put(Integer.parseInt(part1), aux);

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

	public void cargarTags(String pPath) {

		try {

			BufferedReader bufferLectura = new BufferedReader(new FileReader(pPath));

			// Leo una línea del archivo
			String linea = bufferLectura.readLine();

			while (linea != null) {
				// Separa la línea leída con el separador definido previamente
				String[] campos = linea.split(";");

				String part1 = campos[0];
				String part2 = campos[1];
				if (this.lista.containsKey(Integer.parseInt(part1))) {
					this.lista.get(Integer.parseInt(part1)).addTag(part2);
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

	public String visPelis() {
		Collection<Pelicula> aux = this.lista.values();
		StringBuilder sb = new StringBuilder();
		for (Pelicula pel : aux) {
			sb.append(pel.obtTitle());
			sb.append("\n");
		}

		return sb.toString();
	}

	public String visTags(int pID) {

		ArrayList<String> aux = this.lista.get(pID).obtTags();
		System.out.println(aux);
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < aux.size(); i++) {
			sb.append(aux.get(i));
			sb.append("\n");
		}
		System.out.println(aux);
		return sb.toString();
	}

	public boolean containsKey(int piD) {
		if (this.lista.containsKey(piD)) {
			return true;
		}
		return false;
	}

}
