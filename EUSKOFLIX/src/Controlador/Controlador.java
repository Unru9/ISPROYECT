package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JOptionPane;

import Modelo.ColeccionPeliculas;
import Modelo.MatrizSimilitudesOrdenada;
import Modelo.MatrizValoraciones;
import Modelo.MedidasSimilitud;
import Modelo.ModeloPersonas;
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
		this.miVista.idoneidadProductoListener(new IdoneidadProducto());
		this.miVista.idoneidadPersonaListener(new IdoneidadPersona());
		this.miVista.afinesListenerProducto(new afinPelProducto());
		this.miVista.afinesListenerPersona(new afinPelPersona());

		// CREACIÓN DE LA COLECCIÓN DE PELÍCULAS.
		ColeccionPeliculas cp = ColeccionPeliculas.getColeccionPeliculas();
		cp.cargarPeliculas("./resources/data/movie-titles.csv");

		MedidasSimilitud medidasSimilitud = MedidasSimilitud.getMedidasSimilitud();
		MatrizSimilitudesOrdenada matrizSimilitudesOrdenada = MatrizSimilitudesOrdenada.getMatrizSimilitudesOrdenada();
		cp.crearMatrizEtiquetaProductos("./resources/data/movie-tags.csv");

		// CREACIÓN DE LA MATRIZ DE VALORACIONES.
		MatrizValoraciones mv = MatrizValoraciones.getMatrizValoraciones();
		mv.cargarUsuarios("./resources/data/movie-ratings.csv");

		// CREACIÓN DEL MODELO PRODUCTO
		ModeloProductos modeloProductos = ModeloProductos.getModeloProductos();
		modeloProductos.crearModeloProducto();
		medidasSimilitud.crearMatrizSimilitudes();
		// HashMap<Integer, ArrayList<HashMap<Integer, Double>>> a =
		// medidasSimilitud.getMatrizSimilitudes();
		// System.out.println(a);
		matrizSimilitudesOrdenada.GenerarMatrizSimilitudesOrdenada();

		// CREACIÓN DEL MODELO PERSONA
		ModeloPersonas mp = ModeloPersonas.getModeloPersonas();
		mp.crearModeloPersonas();

		System.out.println("FIN DE LA EJECUCIÓN");
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
			ColeccionPeliculas cp = ColeccionPeliculas.getColeccionPeliculas();
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

	class IdoneidadProducto implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ColeccionPeliculas cp = ColeccionPeliculas.getColeccionPeliculas();
			MatrizValoraciones mv = MatrizValoraciones.getMatrizValoraciones();
			MatrizSimilitudesOrdenada msOrdenada = MatrizSimilitudesOrdenada.getMatrizSimilitudesOrdenada();
			String movieID = JOptionPane.showInputDialog("Introduce un movieID");
			String usuarioID = JOptionPane.showInputDialog("Introduce un usuario");
			if (movieID != null && usuarioID != null) {
				if (!cp.contieneIDPelicula(Integer.parseInt(movieID))) {
					JOptionPane.showMessageDialog(miVista, "El movieID no existe");
				} else if (!mv.contieneIdUsuario(Integer.parseInt(usuarioID))) {
					JOptionPane.showMessageDialog(miVista, "El usuarioID no existe");
				} else {
					double res = msOrdenada.gradoIdoneidad(Integer.parseInt(usuarioID), Integer.parseInt(movieID), 20);
					String aux = Double.toString(res);
					StringBuilder sb = new StringBuilder();
					sb.append("============================================================ \n");
					sb.append("PREDICIÓN BASADA EN PRODUCTOS \n");
					sb.append("============================================================ \n");
					sb.append("La valoración predicha para el usuario " + usuarioID + " para la película " + movieID
							+ " ha sido de: " + aux);
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
					HashMap<String, Double> aux = msOrdenada.peliculasAfines(Integer.parseInt(usuarioID), 20);
					String res = msOrdenada.visualizarPeliculasAfinesProducto(Integer.parseInt(usuarioID), 20);
					miVista.setTextoGeneral(res);
				}
			}
		}
	}

	class IdoneidadPersona implements ActionListener {
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

	class afinPelPersona implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ColeccionPeliculas cp = ColeccionPeliculas.getColeccionPeliculas();
			ModeloPersonas mp = ModeloPersonas.getModeloPersonas();
			String movieID = JOptionPane.showInputDialog("Introduce un movieID");
			String usuarioID = JOptionPane.showInputDialog("Introduce un usuario");
			if (movieID != null && usuarioID != null) {
				if (!cp.contieneIDPelicula(Integer.parseInt(movieID))) {
					JOptionPane.showMessageDialog(miVista, "El movieID no existe");
				} else if (!mp.contieneIdUsuario(Integer.parseInt(usuarioID))) {
					JOptionPane.showMessageDialog(miVista, "El usuarioID no existe");
				} else {
					double res = mp.gradoIdoneidad(Integer.parseInt(usuarioID), Integer.parseInt(movieID));
					String aux = Double.toString(res);
					StringBuilder sb = new StringBuilder();
					sb.append("============================================================ \n");
					sb.append("PREDICIÓN BASADA EN PERSONAS \n");
					sb.append("============================================================ \n");
					sb.append("La valoración predicha para el usuario " + usuarioID + " para la película " + movieID
							+ " ha sido de: " + aux);
					miVista.setTextoGeneral(sb.toString());
				}
			}
		}
	}
}
