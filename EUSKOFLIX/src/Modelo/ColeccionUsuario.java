package Modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

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

	public String visRatingUsuario(int pID) {

		Collection<Usuario> aux = this.lista.values();
		StringBuilder sb = new StringBuilder();
		sb.append("Valoraciones de la pelicula  con ID: " + pID);
		sb.append("\n");
		for (Usuario usu : aux) {
			if (usu.obtValoracion(pID) != null) {
				sb.append(usu.obtValoracion(pID));
				sb.append("\n");
			}
		}

		return sb.toString();
	}

	public boolean containsKey(int piD) {
		if (this.lista.containsKey(piD)) {
			return true;
		}
		return false;
	}
}
