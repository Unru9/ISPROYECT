package Modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
		sb.append("Valoraciones de la pelicula  con ID: " + pIdPelicula +"\n");
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
			}else {
				sb.append("El usuario todavia no ha valorado la pelicula " + pIdPelicula +".");
				sb.append("\n");
				sb.append("\n");
			}
		}

		return sb.toString();
	}
	
	public void borrarUsuarios(){
		lista.clear();
	}

	public boolean containsKey(int piD) {
		if (this.lista.containsKey(piD)) {
			return true;
		}
		return false;
	}
}
