package Modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ColeccionPeliculas {
	// atributos
	private HashMap<Integer, Pelicula> lista;
	private static ColeccionPeliculas miColeccionPeliculas;

	// constructora
	private ColeccionPeliculas() {
		this.lista = new HashMap<Integer, Pelicula>();
	}

	// est�tico
	public static ColeccionPeliculas getColeccionPeliculas() {
		if (miColeccionPeliculas == null) {
			miColeccionPeliculas = new ColeccionPeliculas();

		}
		return miColeccionPeliculas;
	}

	// M�todos

	public void cargarPeliculas(String pPath) {
		// pPath= "movie-titles.csv";
		try {

			BufferedReader bufferLectura = new BufferedReader(new FileReader(pPath));

			// Leo una l�nea del archivo
			String linea = bufferLectura.readLine();

			while (linea != null) {
				// Separa la l�nea le�da con el separador definido previamente
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

	public void crearMatrizEtiquetaProductos(String pPath) {

		try {

			BufferedReader bufferLectura = new BufferedReader(new FileReader(pPath));

			// Leo una l�nea del archivo
			String linea = bufferLectura.readLine();

			while (linea != null) {
				// Separa la l�nea le�da con el separador definido previamente
				String[] campos = linea.split(";");

				String part1 = campos[0];
				String part2 = campos[1];
				if (this.lista.containsKey(Integer.parseInt(part1))) {
					Pelicula aux = this.lista.get(Integer.parseInt(part1));
					aux.sumAparicion(part2);
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

		ArrayList<String> aux = this.lista.get(pID).obtStringTags();
		System.out.println(aux);
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < aux.size(); i++) {
			sb.append(aux.get(i));
			sb.append("\n");
		}
		System.out.println(aux);
		return sb.toString();
	}

	public void borrarPeliculas(){
		lista.clear();
	}
	
	public boolean containsKey(int pID) {
		if (this.lista.containsKey(pID)) {
			return true;
		}
		return false;
	}
	
	/*public ArrayList<String> obtTagsPelicula(String pPelicula){
		Pelicula pel = buscarPelicula(pPelicula);
		if (!pel.equals(null)){
			return pel.obtTags();
		}
		return null;
	}*/

	private Pelicula buscarPelicula(String pPelicula) {
		for (Pelicula pel : lista.values()){
			if (pel.obtTitle() == pPelicula){
				return pel;
			}
		}
		return null;
	}
	
	public int numPeliculaConTag(String pTag){
		int res=0;
		Pelicula pel;
		for (HashMap.Entry<Integer, Pelicula> entry : lista.entrySet()) {
			pel= entry.getValue();
			if(pel.containsTags(pTag)){
				res=res+1;
			}
		}
		return res;
	}
	
	public double calcularTFIDF(int pIdPelicula, String pTag){
		Pelicula pel= this.lista.get(pIdPelicula);
		double res=0.0;
		int tf= pel.obtAparecionTag(pTag);
		int N= this.lista.size()+1;
		int NT=this.numPeliculaConTag(pTag);
		
		res= tf *Math.log(N/NT);
		return res;
		
	}
	
	

}
