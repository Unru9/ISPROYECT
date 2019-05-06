package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JOptionPane;

import Modelo.ColeccionPeliculas;
import Modelo.MatrizSimilitudesOrdenada;
import Modelo.MatrizValoraciones;
import Modelo.MedidasSimilitud;
import Modelo.ModeloProductos;
import Vista.VistaCargaDatos;

public class Controlador {

	// atributos
	private VistaCargaDatos miVista;

	public Controlador() throws Exception {
		miVista = new VistaCargaDatos();
		// listeners
		this.miVista.setTitlesListener(new Titles());
		this.miVista.setTagsListener(new Tags());
		this.miVista.setRatingsListener(new Ratings());
		this.miVista.MatrizSimilitudesListener(new MatrizSimilitudes());
		this.miVista.idoneidadListener(new Idoneidad());
		this.miVista.afinesListener(new afinPelProducto());
		
		ColeccionPeliculas cp = ColeccionPeliculas.getColeccionPeliculas();
		cp.cargarPeliculas("./resources/data/movie-titles.csv");
		
		ModeloProductos modeloProductos = ModeloProductos.getModeloProductos();
		MedidasSimilitud medidasSimilitud = MedidasSimilitud.getMedidasSimilitud();
		MatrizSimilitudesOrdenada matrizSimilitudesOrdenada = MatrizSimilitudesOrdenada.getMatrizSimilitudesOrdenada();
		cp.crearMatrizEtiquetaProductos("./resources/data/movie-tags.csv");
		
		MatrizValoraciones mv = MatrizValoraciones.getMatrizValoraciones();
		mv.cargarUsuarios("./resources/data/movie-ratings.csv");
		
		modeloProductos.crearModeloProducto();
		//medidasSimilitud.crearMatrizSimilitudes();
		//HashMap<Integer, ArrayList<HashMap<Integer, Double>>> a = medidasSimilitud.getMatrizSimilitudes();
		//System.out.println(a);
		//matrizSimilitudesOrdenada.GenerarMatrizSimilitudesOrdenada();
		
		System.out.println("FIN");
	}

	public void mostrarVentana() {
		this.miVista.setVisible(true);
	}

	class Titles implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ColeccionPeliculas cp = ColeccionPeliculas.getColeccionPeliculas();
			String aux = cp.visPelis();
			miVista.setTextoGeneral(aux);

		}
	}

	class Tags implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ColeccionPeliculas cp = ColeccionPeliculas.getColeccionPeliculas();
			String movieID = JOptionPane.showInputDialog("Introduce un movieID");
			if (movieID != null) {
				if (!cp.contieneIDPelicula(Integer.parseInt(movieID))) {
					JOptionPane.showMessageDialog(miVista, "El movieID no existe");
				} else {
					String aux = cp.visTags(Integer.parseInt(movieID));
					miVista.setTextoGeneral(aux);
				}
			}
		}
	}

	class Ratings implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			MatrizValoraciones mv = MatrizValoraciones.getMatrizValoraciones();
			ColeccionPeliculas cp= ColeccionPeliculas.getColeccionPeliculas();
			String movieID = JOptionPane.showInputDialog("Introduce un movieID");
			if (movieID != null) {
				if (!cp.contieneIDPelicula(Integer.parseInt(movieID))) {
					JOptionPane.showMessageDialog(miVista, "El movieID no existe");
				} else {
					String aux = mv.visRatingUsuario(Integer.parseInt(movieID));
					miVista.setTextoGeneral(aux);
				}
			}

		}
	}
	
	class MatrizSimilitudes implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ColeccionPeliculas cp = ColeccionPeliculas.getColeccionPeliculas();
			MatrizSimilitudesOrdenada msOrdenada = MatrizSimilitudesOrdenada.getMatrizSimilitudesOrdenada();
			String movieID = JOptionPane.showInputDialog("Introduce un movieID");
			if (movieID != null) {
				if (!cp.contieneIDPelicula(Integer.parseInt(movieID))) {
					JOptionPane.showMessageDialog(miVista, "El movieID no existe");
				} else {
					String aux = msOrdenada.modeloSimilitudProductoPelicula(Integer.parseInt(movieID));
					miVista.setTextoGeneral(aux);
				}
			}
		}
	}
	
	class Idoneidad implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ColeccionPeliculas cp = ColeccionPeliculas.getColeccionPeliculas();
			MatrizValoraciones mv = MatrizValoraciones.getMatrizValoraciones();
			MatrizSimilitudesOrdenada msOrdenada = MatrizSimilitudesOrdenada.getMatrizSimilitudesOrdenada();
			String movieID = JOptionPane.showInputDialog("Introduce un movieID");
			String usuarioID = JOptionPane.showInputDialog("Introduce un usuario");
			if (movieID != null && usuarioID !=null) {
				if (!cp.contieneIDPelicula(Integer.parseInt(movieID))) {
					JOptionPane.showMessageDialog(miVista, "El movieID no existe");
				}else if (!mv.contieneIdUsuario(Integer.parseInt(usuarioID))) {
					JOptionPane.showMessageDialog(miVista, "El usuarioID no existe");
				}else{
					double res= msOrdenada.gradoIdoneidad(Integer.parseInt(usuarioID),Integer.parseInt(movieID),20);
					String aux =Double.toString(res);
					StringBuilder sb = new StringBuilder();
					sb.append("============================================================ \n");
					sb.append("PREDICI�N BASADA EN PRODUCTOS \n");
					sb.append("============================================================ \n");
					sb.append("La valoraci�n predicha para el usuario "+ usuarioID + " para la pel�cula " + movieID + " ha sido de: "+ aux);
					miVista.setTextoGeneral(sb.toString());
				}
			}
		}
	}
	
	class afinPelProducto implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			MatrizSimilitudesOrdenada msOrdenada = MatrizSimilitudesOrdenada.getMatrizSimilitudesOrdenada();
			MatrizValoraciones mv = MatrizValoraciones.getMatrizValoraciones();
			String usuarioID = JOptionPane.showInputDialog("Introduce un usuario");
			if (usuarioID != null) {
				if (!mv.contieneIdUsuario(Integer.parseInt(usuarioID))) {
					JOptionPane.showMessageDialog(miVista, "El usuarioID no existe");
				} else {
					HashMap<String,Double> aux = msOrdenada.peliculasAfines(Integer.parseInt(usuarioID), 20);
					String res = msOrdenada.visualizarPeliculasAfines(Integer.parseInt(usuarioID), 20);
					miVista.setTextoGeneral(res);
				}
			}
		}
	}
}
