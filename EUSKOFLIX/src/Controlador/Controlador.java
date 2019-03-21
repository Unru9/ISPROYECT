package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import Modelo.ColeccionPeliculas;
import Modelo.ColeccionUsuario;
import Vista.VistaCargaDatos;

public class Controlador {

	// atributos
	private VistaCargaDatos miVista;

	public Controlador() {
		miVista = new VistaCargaDatos();
		// listeners
		this.miVista.setTitlesListener(new Titles());
		this.miVista.setTagsListener(new Tags());
		this.miVista.setRatingsListener(new Ratings());
		ColeccionPeliculas cp = ColeccionPeliculas.getColeccionPeliculas();
		cp.cargarPeliculas("./resources/data/movie-titles.csv");
		cp.cargarTags("./resources/data/movie-tags.csv");
		ColeccionUsuario cu = ColeccionUsuario.getColeccionUsuario();
		cu.cargarUsuarios("./resources/data/movie-ratings.csv");
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
				if (!cp.containsKey(Integer.parseInt(movieID))) {
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
			ColeccionUsuario cu = ColeccionUsuario.getColeccionUsuario();
			ColeccionPeliculas cp= ColeccionPeliculas.getColeccionPeliculas();
			String movieID = JOptionPane.showInputDialog("Introduce un movieID");
			if (movieID != null) {
				if (!cp.containsKey(Integer.parseInt(movieID))) {
					JOptionPane.showMessageDialog(miVista, "El movieID no existe");
				} else {
					String aux = cu.visRatingUsuario(Integer.parseInt(movieID));
					miVista.setTextoGeneral(aux);
				}
			}

		}
	}

}
