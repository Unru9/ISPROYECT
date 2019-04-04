package Modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
	
	public ArrayList<Integer> obtPelisBienValoradas(){
		
		ArrayList<Integer> res= new ArrayList<Integer>();
		
		for (Entry<Integer, Usuario> entrada : lista.entrySet()) {
			int idUsuario = entrada.getKey();
			Usuario usuario = lista.get(idUsuario);
			HashMap<Integer, Double> listaRatingsUsuario = usuario.getRatings();
			//HashMap<String, Double> TFIDFTags = new HashMap<String, Double>();
			for (Entry<Integer, Double> entrada2 : listaRatingsUsuario.entrySet()) {
				//String tag = entrada2.getKey();
				double valoracion = entrada2.getValue();
				if(valoracion >=3.5){
					res.add((entrada2.getKey()));
				}
				// System.out.println(TFIDFTags.get(tag));
			}
			
		}
		System.out.println(res);
		System.out.println(res.size());
		return res;
		
	}
	
	/*public HashMap<Integer,HashMap<String,double>> crearModeloPersonas(){
		
		double resultadoFinal;
		ColeccionPeliculas cp= ColeccionPeliculas.getColeccionPeliculas();
		HashMap<Integer, HashMap<String, Double>> aux= cp.modeloProductos();
		
		for (Entry<Integer, Usuario> entrada : lista.entrySet()) {
			int idUsuario= entrada.getKey();
			Usuario usuario = lista.get(idUsuario);
			HashMap<Integer, Double> listaRatingsUsuario = usuario.getRatings();
			ArrayList<Integer> LisValoraciones = new ArrayList<Integer>();
			for (Entry<Integer, Double> entrada2 : listaRatingsUsuario.entrySet()) {
				double valoracion= entrada2.getValue();
				int idPelicula= entrada2.getKey();
				if(valoracion >=3.5){
					LisValoraciones.add(idPelicula);
				}
			}
			for(int i=0;i<LisValoraciones.size();i++){
				resultadoFinal=0.0;
				HashMap<String, Double> hm= aux.get(i);
				for (Entry<String, Double> entrada3 : hm.entrySet()) {
					String tag= entrada3.getKey();
					if(hm.containsKey(tag)){
						double tfidf= hm.get(tag);
					}
					
				}
				
			}
		}*/
}
