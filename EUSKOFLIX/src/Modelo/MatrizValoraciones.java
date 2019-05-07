package Modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class MatrizValoraciones {

	// atributos
	private static MatrizValoraciones miMatrizValoraciones;
	private HashMap<Integer, ValoracionUsuario> listaUsuarios;

	// Constructora
	private MatrizValoraciones() {
		this.listaUsuarios = new HashMap<Integer, ValoracionUsuario>();
	}

	// estático
	public static MatrizValoraciones getMatrizValoraciones() {
		if (miMatrizValoraciones == null) {
			miMatrizValoraciones = new MatrizValoraciones();

		}
		return miMatrizValoraciones;
	}

	// metodos manejo de la lista
	private void anadirUsuario(int pIdUsuario, ValoracionUsuario pNuevoUsuario) {
		listaUsuarios.put(pIdUsuario, pNuevoUsuario);
	}

	public void borrarUsuarios() {
		listaUsuarios.clear();
	}

	private boolean contieneUsuario(int pIdUsuario) {
		if (this.listaUsuarios.containsKey(pIdUsuario)) {
			return true;
		} else {
			return false;
		}
	}

	public ValoracionUsuario obtUsuario(int pUsuarioID) {
		return listaUsuarios.get(pUsuarioID);
	}

	public double obtValoracion(int pUsuarioID, int pPeliculaID) {
		ValoracionUsuario valoracionUsuario1 = obtUsuario(pUsuarioID);
		if (valoracionUsuario1 == null) {
			return -1.00;
		}
		return valoracionUsuario1.obtValoracionPelicula(pPeliculaID);
	}

	// METODOS PARA ITERAR
	public Iterator<Entry<Integer, ValoracionUsuario>> getIterador() {
		return listaUsuarios.entrySet().iterator();

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

				String stringIdUsuario = campos[0];
				String part2 = campos[1];
				String part3 = campos[2];

				int idUsuario = Integer.parseInt(stringIdUsuario);
				if (!contieneUsuario(idUsuario)) {
					ValoracionUsuario nuevoUsuario = new ValoracionUsuario();
					anadirUsuario(idUsuario, nuevoUsuario);
				}

				ValoracionUsuario valoracionUsuario1 = obtUsuario(idUsuario);
				valoracionUsuario1.anadirRating(Integer.parseInt(part2), Double.parseDouble(part3));

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
		for (Entry<Integer, ValoracionUsuario> e : this.listaUsuarios.entrySet()) {
			int IDUsuario = e.getKey();
			ValoracionUsuario UsuarioValorador = e.getValue();
			sb.append("Valoración del Usuario: " + IDUsuario);
			sb.append("\n");
			if (UsuarioValorador.containsPelicula(pIdPelicula)) {
				sb.append(UsuarioValorador.obtValoracionPelicula(pIdPelicula));
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

	public boolean contieneIdUsuario(int pIDusuario) {
		if (this.listaUsuarios.containsKey(pIDusuario)) {
			return true;
		}
		return false;
	}

}

// **********************************************************************************************************//
