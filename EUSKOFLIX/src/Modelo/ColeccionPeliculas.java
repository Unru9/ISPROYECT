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
				//System.out.println(title);
				Pelicula aux = new Pelicula(Integer.parseInt(part1), title);
				this.lista.put(Integer.parseInt(part1), aux);
				// System.out.println(Arrays.toString(campos));

				// Vuelvo a leer del fichero
				linea = bufferLectura.readLine();
			}
			// System.out.println(this.lista.size());
			// System.out.println(this.lista.values());
			// CIerro el buffer de lectura
			if (bufferLectura != null) {
				bufferLectura.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void cargarTags(String pPath) {
		// pPath="movie-tags.csv";

		try {

			BufferedReader bufferLectura = new BufferedReader(new FileReader(pPath));

			// Leo una línea del archivo
			String linea = bufferLectura.readLine();

			while (linea != null) {
				// Separa la línea leída con el separador definido previamente
				String[] campos = linea.split(";");

				String part1 = campos[0];
				String part2 = campos[1];
				//Pelicula aux;
				if (this.lista.containsKey(Integer.parseInt(part1))) {
					if(!this.lista.get(Integer.parseInt(part1)).containsObject(part2)){
						this.lista.get(Integer.parseInt(part1)).addTag(part2);
					}
				}
				// System.out.println(Arrays.toString(campos));

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
			// System.out.println(pel.getTitle());
			sb.append(pel.getTitle());
			sb.append("\n");
		}

		return sb.toString();
	}
	
	
	public String visTags(int pID) {
		
		ArrayList<String> aux =this.lista.get(pID).getTags();
		System.out.println(aux);
		StringBuilder sb = new StringBuilder();
		
		for (int i=0; i<aux.size(); i++) {
			sb.append(aux.get(i));
			sb.append("\n");
		}
		System.out.println(aux);
		return sb.toString();
	}
	
	public TableModel toTableModel(HashMap<Integer, Pelicula> aux) {
	    DefaultTableModel model = new DefaultTableModel(
	        new Object[] { "idMovie", "Title" }, 0
	    );
	    for (HashMap.Entry<Integer, Pelicula> entry : aux.entrySet()) {
	        model.addRow(new Object[] { entry.getKey(), entry.getValue().getTitle() });
	    }
	    return model;
	}
	
	/*public HashMap<Integer, Pelicula> getHas(){
		return this.lista;
	}*/
	
	public boolean containsKey(int piD){
		if(this.lista.containsKey(piD)){
			return true;
		}
		return false;
	}
	
}
	
