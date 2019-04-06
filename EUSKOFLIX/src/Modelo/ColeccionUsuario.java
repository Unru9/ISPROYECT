package Modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class ColeccionUsuario {

	// atributos
	private static ColeccionUsuario miColeccionUsuario;
	private HashMap<Integer, Usuario> listaUsuarios;

	// Constructora
	private ColeccionUsuario() {
		this.listaUsuarios = new HashMap<Integer, Usuario>();
	}

	// estático
	public static ColeccionUsuario getColeccionUsuario() {
		if (miColeccionUsuario == null) {
			miColeccionUsuario = new ColeccionUsuario();

		}
		return miColeccionUsuario;
	}

	//metodos manejo de la lista
	private void anadirUsuario (int pIdUsuario, Usuario pNuevoUsuario) {
		listaUsuarios.put(pIdUsuario, pNuevoUsuario);
	}
	
	public void borrarUsuarios(){
		listaUsuarios.clear();
	}
	
	private boolean contieneUsuario (int pIdUsuario) {
		if (this.listaUsuarios.containsKey(pIdUsuario)) {
			return true;
		}else {
			return false;
		}
	}
	
	private Usuario obtUsuario (int pUsuarioID) {
		return listaUsuarios.get(pUsuarioID);
	}
	
	//METODOS PARA ITERAR
	
	private Iterator<Entry<Integer, Usuario>> iterador() {
		return listaUsuarios.entrySet().iterator();
	}
	
	public Iterator<Entry<Integer, Usuario>> getIterador() {
		return iterador();
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
					Usuario nuevoUsuario = new Usuario();
					anadirUsuario(idUsuario, nuevoUsuario);
				}

				Usuario usuario = obtUsuario(idUsuario);
				usuario.anadirRating(Integer.parseInt(part2), Double.parseDouble(part3));

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
		for (Entry<Integer, Usuario> e : this.listaUsuarios.entrySet()) {
			int IDUsuario = e.getKey();
			Usuario UsuarioValorador = e.getValue();
			sb.append("Valoración del Usuario: " + IDUsuario);
			sb.append("\n");
			if (UsuarioValorador.containsPelicula(pIdPelicula)) {
				sb.append(UsuarioValorador.obtValoracionPelicula(pIdPelicula));
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
}
	
//**********************************************************************************************************//
	
